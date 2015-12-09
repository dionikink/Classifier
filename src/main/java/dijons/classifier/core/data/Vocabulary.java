package dijons.classifier.core.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dion on 9-12-15.
 */
public class Vocabulary {

    private Map<String, Map<String, Integer>> vocabulary;
    private Map<String, Integer> docsInClass;

    public Vocabulary(File file) {
        try {
            this.vocabulary = DataUtils.extractVocabulary(file);
        } catch (IOException e) {
            System.err.println("Cannot extract vocabulary from file.");
            System.exit(1);
        }
    }

    public List<String> getClasses() {
        List<String> classes = new ArrayList<String>();

        for(Map.Entry<String, Map<String, Integer>> entry : vocabulary.entrySet()) {
            if (!classes.contains(entry.getKey())) {
                classes.add(entry.getKey());
            }
        }

        return classes;
    }

    public Map<String, Integer> countWordsInClass() {
        Map<String, Integer> wordsInClass = new HashMap<String, Integer>();

        for(String classEntry : vocabulary.keySet()) {
            int size = 0;

            for(String wordEntry : vocabulary.get(classEntry).keySet()) {
                size = size + vocabulary.get(classEntry).get(wordEntry);
            }

            wordsInClass.put(classEntry, size);
        }

        return wordsInClass;
    }

    public int countDocsInClass(String classEntry) {
        //TODO: method schrijven die aantal docs in een class teruggeeft
        return 0;
    }
}
