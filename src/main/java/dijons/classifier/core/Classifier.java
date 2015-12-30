package dijons.classifier.core;

import dijons.classifier.core.data.DataUtils;
import dijons.classifier.core.data.Document;
import dijons.classifier.core.data.KnowledgeBase;
import dijons.classifier.core.data.Vocabulary;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jensj.r on 12/7/2015.
 *
 */
public class Classifier {

    private KnowledgeBase knowledgeBase;
    private final static Classifier instance = new Classifier();


    public Classifier() {
        this.knowledgeBase = new KnowledgeBase();
    }

    public String apply(Document document) {
        String resultClass = null;
        Map<String, Double> prior = knowledgeBase.getPrior();
        Map<String, Map<String, Double>> condProb = knowledgeBase.getCondProb();
        List<String> classes = knowledgeBase.getClasses();
        Map<String, Integer> tokens = document.getBagOfWords();
        Map<String, Double> result = new HashMap<String, Double>();
        double uniqueWordCount = Vocabulary.getInstance().getUniqueWordCount();
        Map<String, Integer> wordsInClass = DataUtils.countWordsInClass();

        for(String classEntry : classes) {
            double score = prior.get(classEntry);
            double classSize = wordsInClass.get(classEntry);
            double scoreForUnknownToken = 1/(classSize + uniqueWordCount);

            for(String token : tokens.keySet()) {
                if (condProb.get(classEntry).containsKey(token)) {
                    score += multiply(condProb.get(classEntry).get(token), tokens.get(token));
                } else {
                    condProb.get(classEntry).put(token, scoreForUnknownToken);
                    score += multiply(condProb.get(classEntry).get(token), tokens.get(token));
                }
            }
            System.out.println("Score for " + document.getName() + "|" + classEntry + ": " + score);
            result.put(classEntry, score);
        }

        for(String className : result.keySet()) {
            if (resultClass == null) {
                resultClass = className;
            } else if (result.get(className) > result.get(resultClass)) {
                resultClass = className;
            }
        }

        return resultClass;
    }

    public void train(File file) {
        Vocabulary v = Vocabulary.getInstance();
        v.addFile(file);
        Map<String, Map<String, Integer>> vocabulary = v.getMap();

        int numberOfDocuments = KnowledgeBase.numberOfDocuments;
        Map<String, Double> prior = new HashMap<String, Double>();
        Map<String, Map<String, Double>> condProb = new HashMap<String, Map<String, Double>>();

        List<String> classes = v.getClasses();
        Map<String, Integer> docsInClass = DataUtils.countDocumentsInClass(file);
        Map<String, Integer> wordsInClass = DataUtils.countWordsInClass();

        for(String classEntry : classes) {
            int docsInThisClass = docsInClass.get(classEntry);
            prior.put(classEntry, log2((double)docsInThisClass/(double)numberOfDocuments));
            Map<String, Double> condProbInClass = new HashMap<String, Double>();
            double wordsInThisClass = (double) wordsInClass.get(classEntry) + v.getUniqueWordCount();

            for(String word : vocabulary.get(classEntry).keySet()) {
                double wordOccurrencesInClass = (double) vocabulary.get(classEntry).get(word) + 1;
                condProbInClass.put(word, log2(wordOccurrencesInClass/wordsInThisClass));
            }

            condProb.put(classEntry, condProbInClass);
        }

        knowledgeBase.setPrior(prior);
        knowledgeBase.setCondProb(condProb);
        knowledgeBase.setClasses(classes);
        knowledgeBase.setTotalDocsInClasses(docsInClass);
        knowledgeBase.setTotalWordsInClasses(wordsInClass);
        Vocabulary.getInstance().countUniqueWords();
    }

    public void trainSingleDocument(Document document, String className) {
        Map<String, Map<String, Double>> condProb = knowledgeBase.getCondProb();
        if (condProb.containsKey(className)) {
            // Load known values of the Knowledge Base
            Vocabulary v = Vocabulary.getInstance();
            Map<String, Map<String, Integer>> vocabulary = v.getMap();
            Map<String, Double> prior = knowledgeBase.getPrior();
            Map<String, Double> condProbInClass = condProb.get(className);
            Map<String, Integer> docsInClass = knowledgeBase.getTotalDocsInClasses();
            Map<String, Integer> wordsInClass = knowledgeBase.getTotalWordsInClasses();

            int numberOfDocuments = KnowledgeBase.numberOfDocuments + 1;
            int docsInThisClass = docsInClass.get(className) + 1;
            prior.replace(className, log2((double)docsInThisClass/(double)numberOfDocuments));
            double wordsInThisClass = (double) wordsInClass.get(className) + v.getUniqueWordCount();
            Map<String, Integer> bagOfWords = document.getBagOfWords();

            for (String word : bagOfWords.keySet()) {
                if (vocabulary.get(className).containsKey(word)) {
                    double wordOccurrencesInClass = (double) vocabulary.get(className).get(word) + bagOfWords.get(word);
                    condProbInClass.replace(word, log2(wordOccurrencesInClass/wordsInThisClass));
                    vocabulary.get(className).replace(word, vocabulary.get(className).get(word) + bagOfWords.get(word));
                } else {
                    vocabulary.get(className).put(word, bagOfWords.get(word));
                    condProbInClass.put(word, log2(1d/wordsInThisClass));
                }
            }
            //  Replace old stored values
            condProb.replace(className, condProbInClass);
            docsInClass.replace(className, docsInClass.get(className) + 1);
            KnowledgeBase.numberOfDocuments = numberOfDocuments;
            knowledgeBase.setPrior(prior);
            knowledgeBase.setCondProb(condProb);
            knowledgeBase.setTotalDocsInClasses(docsInClass);
            knowledgeBase.setTotalWordsInClasses(wordsInClass);
            Vocabulary.getInstance().countUniqueWords();
        } else {
            System.err.println("Conditional Probability table does not contain class " + className);
        }
    }

    public void test(File file, File output) {
        List<Document> documents = null;

        try {
            documents = DataUtils.extractDocuments(file);
        } catch (IOException e) {
            System.err.println("Could not extract documents from this file.");
        }

        PrintWriter pw = null;

        try {
            pw = new PrintWriter(output + "/result.txt");
        } catch (IOException e) {
            System.err.println("Could not write output file.");
        }

        if (documents != null && pw != null) {
            for (Document document : documents) {
                String resultClass = apply(document);
                System.out.println(document.getName() + ": " + resultClass);
                pw.println(document.getName() + ": " + resultClass);
            }
        }

        if (pw != null) {
            pw.flush();
            pw.close();
        }
    }

    public static Classifier getInstance() {
        return instance;
    }

    public double log2(double x) {
        return Math.log(x)/Math.log(2.0d);
    }

    public double multiply(double x, int multiplier) {
        double result = 0;

        for(int i = 0; i < multiplier; i++) {
            result += x;
        }

        return result;
    }
}
