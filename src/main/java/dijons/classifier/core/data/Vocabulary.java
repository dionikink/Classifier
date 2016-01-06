package dijons.classifier.core.data;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by dion on 9-12-15.
 */
public class Vocabulary {

    private static final Vocabulary instance = new Vocabulary();
    /*
    This is a list with stop words
     */
    private List<String> stopwords = fillList();
    /*
    This is a map with the categories as the key and a map with words and the number of occurrences of that specific word in the specified category
     */
    private Map<String, Map<String, Integer>> vocabulary;
    /*
    This double is the total number of unique words in the entire vocabulary
     */
    private double uniqueWordCount;
    /*
    The documentFrequencies map contains the categories as the key and a map with words and the number of documents in that class they occur in as a value
     */
    private Map<String, Map<String, Double>> documentFrequencies;

    public Map<String, Map<String, Double>> getDocumentFrequencies() {return documentFrequencies; }

    public void setDocumentFrequencies(Map<String, Map<String, Double>> documentFrequencies) {this.documentFrequencies = documentFrequencies; }

    /*
    Returns a list of all the known categories
     */
    public List<String> getClasses() {
        List<String> classes = new ArrayList<String>();

        for (Map.Entry<String, Map<String, Integer>> entry : vocabulary.entrySet()) {
            if (!classes.contains(entry.getKey())) {
                classes.add(entry.getKey());
            }
        }

        return classes;
    }

    /*
    This method counts all the unique words in the entire vocabulary
     */
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

    /*
    This method adds one document to the vocabulary
     */
    public void addDocument(Document document, String className) {
        for (String word : document.getBagOfWords().keySet()) {
            if (vocabulary.get(className).containsKey(word)) {
                vocabulary.get(className).replace(word, vocabulary.get(className).get(word) + document.getBagOfWords().get(word));
            } else {
                vocabulary.get(className).put(word, document.getBagOfWords().get(word));
            }
        }
    }

    /*
    This method adds a complete zip file to the vocabulary
     */
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

    /*
    This method is used to fill the array list with stop words
     */
    private List<String> fillList() {
        String[] stopWords = {"a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "arent", "as", "at", "b", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "c", "cant", "cannot",
                "could", "couldnt", "d", "did", "didnt", "do", "does", "doesnt", "doing", "dont", "down", "during", "e", "each", "f", "few", "for", "from", "further", "h", "had", "hadnt", "has", "hasnt", "have", "havent", "having",
                "he", "hed", "hell", "hes", "her", "here", "heres", "hers", "herself", "him", "himself", "his", "how", "hows", "i", "id", "ill", "im", "ive", "if", "in", "into", "is", "isnt", "it", "its", "its",
                "itself", "j", "k", "l", "ll", "lets", "m", "me", "more", "most", "mustnt", "my", "myself", "n", "no", "nor", "not", "o", "of", "off", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out",
                "over", "own", "p", "q", "r", "s", "same", "shant", "she", "shed", "shell", "shes", "should", "shouldnt", "so", "some", "such", "t", "than", "that", "thats", "the", "their", "theirs", "them", "themselves", "then",
                "there", "theres", "these", "they", "theyd", "theyll", "theyre", "theyve", "this", "those", "through", "to", "too", "u", "under", "until", "up", "v", "ve", "very", "w", "was", "wasnt", "we", "wed", "well", "were",
                "weve", "were", "werent", "what", "whats", "when", "whens", "where", "wheres", "which", "while", "who", "whos", "whom", "why", "whys", "with", "wont", "would", "wouldnt", "you", "youd", "youll", "youre", "youve",
                "your", "yours", "yourself", "yourselves", "y", "z"};
        return Arrays.asList(stopWords);
    }

    public List<String> getStopwords() {
        return stopwords;
    }

    public double getUniqueWordCount() {
        return uniqueWordCount;
    }

    public Map<String, Map<String, Integer>> getVocabulary() {
        return this.vocabulary;
    }

    public static Vocabulary getInstance() {
        return instance;
    }
}
