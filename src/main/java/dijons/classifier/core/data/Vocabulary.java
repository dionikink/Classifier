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

    private Map<String, Map<String, Integer>> vocabulary;
    private static Map<String, Integer> docsInClass;

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

    public Map<String, Map<String, Integer>> getVocabulary() {
        return this.vocabulary;
    }



}
