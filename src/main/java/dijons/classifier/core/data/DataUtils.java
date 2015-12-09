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
 */
public class DataUtils {

    public static Map<String, Map<String, Integer>> extractVocabulary(File file) throws IOException{
        Map<String, Map<String,Integer>> result = new HashMap<String, Map<String, Integer>>();
        Map<String, Integer> bagOfWords = new HashMap<String, Integer>();
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        String className = null;
        while (entries.hasMoreElements()) {
            boolean newEntry = true;
            ZipEntry entry = entries.nextElement();
            System.out.println(entry.toString());
            if (entry.isDirectory()) {
                if (className != null) {
                    result.put(className, bagOfWords);
                    bagOfWords = new HashMap<String, Integer>();
                }
                className = entry.getName();
            } else {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
                String string = "";
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    string = string + s;
                }
                string = string.toLowerCase();
                string = string.replaceAll("[^a-z \\s]", "");
                String[] array = string.split("\\s");
                for (int i = 0; i < array.length; i++) {
                    if (!bagOfWords.keySet().contains(array[i])) {
                        bagOfWords.put(array[i], 1);
                    } else if (newEntry) {
                        bagOfWords.replace(array[i], bagOfWords.get(array[i]) + 1);
                        newEntry = false;
                    }
                }
            }
        }
        if (className != null) {
            result.put(className, bagOfWords);
        }
        return result;
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


}
