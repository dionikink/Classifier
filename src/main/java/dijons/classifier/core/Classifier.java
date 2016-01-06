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

    // Contains the knowledge base of the classifier, where all the information is stored
    private KnowledgeBase knowledgeBase;

    // Creates a new Classifier instance, that can be accessed from anywhere
    private final static Classifier instance = new Classifier();

    public Classifier() {
        this.knowledgeBase = new KnowledgeBase();
    }

    // Classifies a document
    public String apply(Document document) {
        String resultClass = null;

        // Loads the prior probabilities from the knowledge base
        Map<String, Double> prior = knowledgeBase.getPrior();

        // Loads the conditional probabilities from the knowledge base
        Map<String, Map<String, Double>> condProb = knowledgeBase.getCondProb();

        // Loads all classes from the vocabulary
        List<String> classes = Vocabulary.getInstance().getClasses();

        // Loads all tokens (words) from the document
        Map<String, Integer> tokens = document.getBagOfWords();

        Map<String, Double> result = new HashMap<String, Double>();

        // Counts the number of unique words in the vocabulary
        double uniqueWordCount = Vocabulary.getInstance().getUniqueWordCount();

        // Loads the number of words in each class
        Map<String, Integer> wordsInClass = DataUtils.countWordsInClass();

        for(String classEntry : classes) {
            // The probability of the document belonging to the current classEntry
            double score = prior.get(classEntry);

            double classSize = wordsInClass.get(classEntry);
            double scoreForUnknownToken = log2(1/(classSize + uniqueWordCount));

            // Adds up the conditional probabilities for all tokens in the document
            for(String token : tokens.keySet()) {
                if (condProb.get(classEntry).containsKey(token)) {
                    score += (condProb.get(classEntry).get(token) * tokens.get(token));
                } else {
                    condProb.get(classEntry).put(token, scoreForUnknownToken);
                    score += (condProb.get(classEntry).get(token) * tokens.get(token));
                }
            }

            System.out.println("Score for " + document.getName() + "|" + classEntry + ": " + score);
            result.put(classEntry, score);
        }

        // Determines the result
        for(String className : result.keySet()) {
            if (resultClass == null) {
                resultClass = className;
            } else if (result.get(className) > result.get(resultClass)) {
                resultClass = className;
            }
        }

        return resultClass;
    }

    // Trains the classifier with a training set
    public void train(File file) {
        Vocabulary v = Vocabulary.getInstance();

        // Adds the training set to the vocabulary
        v.addFile(file);

        // Loads the vocabulary
        Map<String, Map<String, Integer>> vocabulary = v.getVocabulary();

        int numberOfDocuments = KnowledgeBase.numberOfDocuments;
        Map<String, Double> prior = new HashMap<String, Double>();
        Map<String, Map<String, Double>> condProb = new HashMap<String, Map<String, Double>>();

        // Loads the classes from the vocabulary
        List<String> classes = v.getClasses();

        // Loads the number of documents in the classes
        Map<String, Integer> docsInClass = DataUtils.countDocumentsInClass(file);

        // Loads the number of words in the classes
        Map<String, Integer> wordsInClass = DataUtils.countWordsInClass();

        // Updates the unique word count of the vocabulary
        v.countUniqueWords();

        Map<String,Map<String,Double>> documentFrequencies = v.getDocumentFrequencies();

        for(String classEntry : classes) {
            int docsInThisClass = docsInClass.get(classEntry);

            // Calculates the prior probability for the current classEntry
            prior.put(classEntry, log2((double)docsInThisClass/(double)numberOfDocuments));

            Map<String, Double> condProbInClass = new HashMap<String, Double>();

            // Loads the information about the document frequencies
            Map<String, Double> documentFrequency = documentFrequencies.get(classEntry);

            // Counts the total number of words in this class + the number of unique words in the vocabulary
            double wordsInThisClass = (double) wordsInClass.get(classEntry) + v.getUniqueWordCount();

            for(String word : vocabulary.get(classEntry).keySet()) {

                // If a token appears in more than 95% or less than 0.05% of the documents, the token is omitted
                if ((documentFrequency.get(word)/(double)numberOfDocuments) < 0.95 && (documentFrequency.get(word)/(double)numberOfDocuments) > 0.0005) {
                    double wordOccurrencesInClass = (double) vocabulary.get(classEntry).get(word) + 1;
                    condProbInClass.put(word, log2(wordOccurrencesInClass / wordsInThisClass));
                }
            }

            condProb.put(classEntry, condProbInClass);
        }

        // Saves all calculated probabilities in the knowledge base
        knowledgeBase.setPrior(prior);
        knowledgeBase.setCondProb(condProb);
        knowledgeBase.setTotalDocsInClasses(docsInClass);
        knowledgeBase.setTotalWordsInClasses(wordsInClass);
    }

    // Trains the classifier with a single document instead of a training set
    public void trainSingleDocument(Document document, String className) {
        Vocabulary v = Vocabulary.getInstance();

        // Adds the new document to the vocabulary
        v.addDocument(document, className);

        // Updates the unique word count of the vocabulary
        v.countUniqueWords();

        // Loads all known data from the knowledge base
        Map<String, Map<String, Double>> condProb = knowledgeBase.getCondProb();
        Map<String, Integer> docsInClass = knowledgeBase.getTotalDocsInClasses();
        Map<String, Integer> wordsInClass = knowledgeBase.getTotalWordsInClasses();
        Map<String, Double> prior = knowledgeBase.getPrior();
        Map<String, Map<String, Integer>> vocabulary = v.getVocabulary();

        int numberOfDocuments = KnowledgeBase.numberOfDocuments + 1;

        if (condProb.containsKey(className)) {
            for(String classEntry : condProb.keySet()) {
                Map<String, Double> condProbInClass = condProb.get(classEntry);
                int docsInThisClass = docsInClass.get(classEntry);

                if (classEntry.equals(className)) {
                    docsInThisClass += 1;
                }

                prior.replace(classEntry, log2((double)docsInThisClass/(double)numberOfDocuments));
                double wordsInThisClass = (double) wordsInClass.get(classEntry) + v.getUniqueWordCount();
                for (String word : vocabulary.get(classEntry).keySet()) {
                    double wordOccurrencesInClass = (double) vocabulary.get(classEntry).get(word) + 1;
                    condProbInClass.replace(word, log2(wordOccurrencesInClass/wordsInThisClass));
                }

                //  Replaces old stored values
                condProb.replace(classEntry, condProbInClass);
                if (classEntry.equals(className)) {
                    docsInClass.replace(classEntry, docsInClass.get(classEntry) + 1);
                }

                KnowledgeBase.numberOfDocuments = numberOfDocuments;
                knowledgeBase.setPrior(prior);
                knowledgeBase.setCondProb(condProb);
                knowledgeBase.setTotalDocsInClasses(docsInClass);
                knowledgeBase.setTotalWordsInClasses(wordsInClass);
            }
        } else {
            System.err.println("Conditional Probability table does not contain class " + className);
        }
    }

    // Tests the classifier with a test set
    public void test(File file, File output) {
        List<Document> documents = null;

        // Loads the documents from the .zip file
        try {
            documents = DataUtils.extractDocuments(file);
        } catch (IOException e) {
            System.err.println("Could not extract documents from this file.");
        }

        PrintWriter pw = null;

        // Creates the output file
        try {
            pw = new PrintWriter(output + "/result.txt");
        } catch (IOException e) {
            System.err.println("Could not write output file.");
        }

        // Classifies all documents and saves the results
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

    // Converts the input to a logarithm with base 2
    public double log2(double x) {
        return Math.log(x)/Math.log(2.0d);
    }
}
