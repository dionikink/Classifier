package dijons.classifier.core.data;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by dion on 9-12-15.
 */
public class Vocabulary {

    private static final Vocabulary instance = new Vocabulary();

    private List<String> stopwords = new ArrayList<String>();

    private Map<String, Map<String, Integer>> vocabulary;

    public List<String> getClasses() {
        List<String> classes = new ArrayList<String>();

        for (Map.Entry<String, Map<String, Integer>> entry : vocabulary.entrySet()) {
            if (!classes.contains(entry.getKey())) {
                classes.add(entry.getKey());
            }
        }

        return classes;
    }


    public List<String> getStopwords() {
        return stopwords;
    }

    public double getUniqueWordCount() {
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

        return result;
    }

    public Map<String, Map<String, Integer>> getVocabulary() {
        return this.vocabulary;
    }

    public void addFile(File file) {
        try {
            this.vocabulary = DataUtils.extractVocabulary(file);
        } catch (IOException e) {
            System.err.println("Cannot extract vocabulary from file.");
            System.exit(1);
        }
    }

    private List<String> fillList() {
        List<String> result = new ArrayList<String>();

        return result;
    }

    public static Vocabulary getInstance() {
        return instance;
    }
}
