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

    public String apply(File file) {
        String resultClass = null;
        Map<String, Double> prior = knowledgeBase.getPrior();
        Map<String, Map<String, Double>> condProb = knowledgeBase.getCondProb();
        List<String> classes = knowledgeBase.getClasses();

        Document document = DataUtils.extractDocument(file);
        Map<String, Integer> tokens = document.getBagOfWords();

        Map<String, Double> result = new HashMap<String, Double>();

        for(String classEntry : classes) {
            double score = prior.get(classEntry);

            for(String token : tokens.keySet()) {
                if (condProb.get(classEntry).containsKey(token)) {
                    score +=  Math.pow(condProb.get(classEntry).get(token), tokens.get(token));
                } else {
                    Map<String, Integer> wordsInClass = DataUtils.countWordsInClass();
                    double classSize = wordsInClass.get(classEntry);
                    double newScoreForToken = 1/(classSize + Vocabulary.getInstance().getUniqueWordCount());
                    condProb.get(classEntry).put(token, newScoreForToken);
                    score += Math.pow(condProb.get(classEntry).get(token), tokens.get(token));
                }
            }
            result.put(classEntry, score);
        }

        for(String className : result.keySet()) {
            if (resultClass == null) {
                resultClass = className;
            } else if (result.get(className) >= result.get(resultClass)) {
                resultClass = className;
            }
        }

        return resultClass;
    }

    public void train(File file) {
        Vocabulary v = Vocabulary.getInstance();
        v.addFile(file);
        Map<String, Map<String, Integer>> vocabulary = v.getVocabulary();

        int numberOfDocuments = KnowledgeBase.numberOfDocuments;
        Map<String, Double> prior = new HashMap<String, Double>();
        Map<String, Map<String, Double>> condProb = new HashMap<String, Map<String, Double>>();

        List<String> classes = v.getClasses();
        Map<String, Integer> docsInClass = DataUtils.countDocsInClass(file);
        Map<String, Integer> wordsInClass = DataUtils.countWordsInClass();

        for(String classEntry : classes) {
            int docsInThisClass = docsInClass.get(classEntry);
            prior.put(classEntry, log2((double)docsInThisClass/(double)numberOfDocuments));
            Map<String, Double> condProbInClass = new HashMap<String, Double>();

            for(String word : vocabulary.get(classEntry).keySet()) {
                double wordOccurrencesInClass = (double) vocabulary.get(classEntry).get(word) + 1;
                double wordsInThisClass = (double) wordsInClass.get(classEntry) + v.getUniqueWordCount();

                condProbInClass.put(word, log2(wordOccurrencesInClass/wordsInThisClass));
            }

            condProb.put(classEntry, condProbInClass);
        }

        knowledgeBase.setPrior(prior);
        knowledgeBase.setCondProb(condProb);
        knowledgeBase.setClasses(classes);
    }

    public void test(File file, File output) {
        List<Document> documents = null;
        Map<String, Double> prior = knowledgeBase.getPrior();
        Map<String, Map<String, Double>> condProb = knowledgeBase.getCondProb();
        List<String> classes = knowledgeBase.getClasses();

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

        for(Document document : documents) {
            String resultClass = null;

            Map<String, Integer> tokens = document.getBagOfWords();
            Map<String, Double> result = new HashMap<String, Double>();

            for(String classEntry : classes) {
                double score = prior.get(classEntry);

                for(String token : tokens.keySet()) {
                    if (condProb.get(classEntry).containsKey(token)) {
                        score += condProb.get(classEntry).get(token);
                    }
                }

                result.put(classEntry, score);
            }

            for(String className : result.keySet()) {
                if (resultClass == null) {
                    resultClass = className;
                } else if (result.get(className) < result.get(resultClass)) {
                    resultClass = className;
                }
            }

            pw.println(document.getName() + ": " + resultClass);
        }

        pw.flush();
        pw.close();
    }

    public static Classifier getInstance() {
        return instance;
    }

    public double log2(double x) {
        return Math.log(x)/Math.log(2.0d);
    }
}
