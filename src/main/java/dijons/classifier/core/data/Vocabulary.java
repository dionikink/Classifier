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

    public static void countDocsInClass(ZipFile zipFile) {
        docsInClass = new HashMap<String, Integer>();
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        String className = null;
        int count = 0;
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                if (className != null) {
                    docsInClass.put(className, count);
                }
                className = entry.getName();
                count = 0;
            } else {
                count++;
            }
        }
        if (className != null) {
            docsInClass.put(className, count);
        }
        for (String s : docsInClass.keySet()) {
            System.out.println(s + docsInClass.get(s));
        }
    }
}
