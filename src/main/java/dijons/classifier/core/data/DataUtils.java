package dijons.classifier.core.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by dion on 9-12-15.
 *
 */
public class DataUtils {

    private static HashMap<String, Integer> bagOfWords;

    public static Map<String, Map<String, Integer>> extractVocabulary(File file) throws IOException{

        Map<String, Map<String,Integer>> result = new HashMap<String, Map<String, Integer>>();
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        count(entries);
        entries = zipFile.entries();
        String categoryName = null;

        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                if (categoryName != null) {
                    result.put(categoryName, bagOfWords);
                }
                bagOfWords = new HashMap<String, Integer>();
                categoryName = entry.getName().replaceAll("[^a-z A-Z]", "");
            } else {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
                List<String> uniqueWords = getUniqueWords(bufferedReader);
                addUniqueWordsToBag(uniqueWords);
            }
        }
        if (categoryName != null && bagOfWords != null) {
            result.put(categoryName, bagOfWords);
        }
        return result;
    }

    public static void addUniqueWordsToBag(List<String> uniqueWords) {
        for (String word : uniqueWords) {
            if (bagOfWords.containsKey(word)) {
                bagOfWords.replace(word, bagOfWords.get(word) + 1);
            } else {
                bagOfWords.put(word, 1);
            }
        }
    }

    public static List<String> getUniqueWords(BufferedReader bufferedReader) throws IOException {
        List<String> result = new ArrayList<String>();
        String string = "";
        String s;
        while((s = bufferedReader.readLine()) != null) {
            s = s.toLowerCase();
            s = s.replaceAll("[^a-z \\s]", "");
            string = string + s;
        }
        String[] wordArray = string.split("\\s");
        for (int i = 0; i < wordArray.length; i++) {
            if(!result.contains(wordArray[i])) {
                result.add(wordArray[i]);
            }
        }
        return result;
    }

    public static void count(Enumeration<? extends ZipEntry> entries) {
        int totalNoOfDocs = 0;
        int totalNoOfCategories = 0;

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                totalNoOfCategories++;
            } else {
                totalNoOfDocs++;
            }
        }
        KnowledgeBase.numberOfCategories = totalNoOfCategories;
        KnowledgeBase.numberOfDocuments = totalNoOfDocs;
    }

    public static Document tokenizer(String data) {
        HashMap<String, Integer> bagOfWords = new HashMap<String, Integer>();

        String string = data.toLowerCase();
        string = string.replaceAll("[^a-z \\s]", "");
        StringTokenizer stringTokenizer = new StringTokenizer(string);
        while (stringTokenizer.hasMoreElements()) {
            string = stringTokenizer.nextToken();
            if (bagOfWords.containsKey(string)) {
                bagOfWords.replace(string, bagOfWords.get(string) + 1);
            } else {
                bagOfWords.put(string, 1);
            }
        }
        return new Document(bagOfWords);
    }

    public static Map<String, Integer> countDocsInClass(File file) {
        ZipFile zipFile = null;
        Map<String, Integer> docsInClass = new HashMap<String, Integer>();

        try {
            zipFile = new ZipFile(file);
        } catch (IOException e) {
            System.err.println("Cannot create zip file.");
        }

        if (zipFile != null) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            String className = null;
            int count = 0;
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    if (className != null) {
                        docsInClass.put(className, count);
                    }
                    className = entry.getName().replaceAll("[^a-z A-Z]", "");
                    count = 0;
                } else {
                    count++;
                }
            }
            if (className != null) {
                docsInClass.put(className, count);
            }
        }

        return docsInClass;
    }

    public static Map<String, Integer> countWordsInClass(Vocabulary v) {
        Map<String, Map<String, Integer>> vocabulary = v.getVocabulary();

        Map<String, Integer> wordsInClass = new HashMap<String, Integer>();

        for(String classEntry : vocabulary.keySet()) {
            int size = 0;

            for(String wordEntry : vocabulary.get(classEntry).keySet()) {
                size = size + vocabulary.get(classEntry).get(wordEntry) + 1;
            }

            wordsInClass.put(classEntry, size);
        }
        return wordsInClass;
    }
}
