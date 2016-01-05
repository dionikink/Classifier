package dijons.classifier.core.data;

import javax.print.Doc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by dion on 9-12-15.
 */
public class Vocabulary {

    private static final Vocabulary instance = new Vocabulary();
    private static final String[] stopWords = {"a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren\'t", "as", "at",
            "b", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "c", "can\'t", "cannot", "could", "couldn\'t", "d", "did", "didn\'t", "do", "does", "doesn\'t", "doing", "don\'t", "down", "during", "e",
            "each", "f", "few", "for", "from", "further", "g", "h", "had", "hadn\'t", "has", "hasn\'t", "have", "haven\'t", "having", "he", "he\'d", "he\'ll", "he\'s", "her", "here", "here\'s", "hers", "herself", "him", "himself", "his",
            "how", "how\'s", "i", "i\'d", "i\'ll", "i\'m", "i\'ve", "if", "in", "into", "is", "isn\'t", "it", "it\'s", "its", "itself", "j", "l", "let\'s", "ll", "m", "me", "more", "most", "mustn\'t", "my", "myself", "n", "no", "nor",
            "not", "o", "of", "off", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "p", "q", "r", "re", "s", "same", "shan\'t", "she", "she\'d", "she\'ll", "she\'s", "should", "shouldn\'t",
            "so", "some", "such", "t", "than", "that", "that\'s", "the", "their", "theirs", "them", "themselves", "then", "there", "there\'s", "these", "they", "they\'d", "they\'ll", "they\'re", "they\'ve", "this", "those", "through",
            "to", "too", "u", "under", "until", "up", "v", "ve", "very", "w", "was", "wasn\'t", "we", "we\'d", "we\'ll", "we\'re", "we\'ve", "were", "weren\'t", "what", "what\'s", "when", "when\'s", "where", "where\'s", "which",
            "while", "who", "who\'s", "whom", "why", "why\'s", "with", "won\'t", "would", "wouldn\'t", "x", "y", "you", "you\'d", "you\'ll", "you\'re", "you\'ve", "your", "yours", "yourself", "yourselves", "z"};
    private List<String> stopwords = fillList();
    private Map<String, Map<String, Integer>> vocabulary;
    private double uniqueWordCount;
    private Map<String, Map<String, Double>> documentFrequencies;

    public Map<String, Map<String, Double>> getDocumentFrequencies() {return documentFrequencies; }

    public void setDocumentFrequencies(Map<String, Map<String,Double>> documentFrequencies) {this.documentFrequencies = documentFrequencies; }

    public List<String> getClasses() {
        List<String> classes = new ArrayList<String>();

        for (Map.Entry<String, Map<String, Integer>> entry : vocabulary.entrySet()) {
            if (!classes.contains(entry.getKey())) {
                classes.add(entry.getKey());
            }
        }

        return classes;
    }

    public void countUniqueWords() {
        double result = 0;
        List<String> countedWords = new ArrayList<String>();

        for(String classEntry : vocabulary.keySet()) {
            for(String word : vocabulary.get(classEntry).keySet()) {
                if(!countedWords.contains(word)) {
                    result++;
                    countedWords.add(word);
                }
            }
        }
        uniqueWordCount = result;
    }

    public void addDocument(Document document, String className) {
        for (String word : document.getBagOfWords().keySet()) {
            if (vocabulary.get(className).containsKey(word)) {
                vocabulary.get(className).replace(word, vocabulary.get(className).get(word) + document.getBagOfWords().get(word));
            } else {
                vocabulary.get(className).put(word, document.getBagOfWords().get(word));
            }
        }
    }

    public void addFile(File file) {
        try {
            if (this.vocabulary == null) {
                this.vocabulary = new HashMap<String, Map<String, Integer>>();
            }

            this.vocabulary.putAll(DataUtils.extractVocabulary(file));
        } catch (IOException e) {
            System.err.println("Cannot extract vocabulary from file.");
            System.exit(1);
        }
    }

    private List<String> fillList() {
        List<String> result = new ArrayList<String>();
        result = Arrays.asList(stopWords);
        return result;
    }

    public List<String> getStopwords() {
        return stopwords;
    }

    public double getUniqueWordCount() {
        return uniqueWordCount;
    }

    public Map<String, Map<String, Integer>> getMap() {
        return this.vocabulary;
    }

    public static Vocabulary getInstance() {
        return instance;
    }
}
