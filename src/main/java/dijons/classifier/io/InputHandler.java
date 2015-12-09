package dijons.classifier.io;

import dijons.classifier.core.data.Document;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by jensj.r on 12/7/2015.
 *
 */
public class InputHandler {

    public ArrayList<Document> getDocumentListForClassifying(File file) throws IOException{
        ArrayList<Document> result = new ArrayList<Document>();
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
            String string = "";
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                string = string + s;
            }
            if (!string.equals("")) {
                Document document = tokenizer(string);
                result.add(document);
            }
        }
        return result;
    }

    public HashMap<String, HashMap<String, Integer>> getDocumentMapForTraining(File file) throws IOException{
        HashMap<String,HashMap<String,Integer>> result = new HashMap<String, HashMap<String, Integer>>();
        HashMap<String, Integer> bagOfWords = new HashMap<String, Integer>();
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

    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        File file = new File("C:\\Users\\jensj.r\\Downloads\\blogs.zip");
        try {
            HashMap<String, HashMap<String, Integer>> hoi = inputHandler.getDocumentMapForTraining(file);
            for ( String s : hoi.keySet()) {
                System.out.println(s + ": ");
                for (String s1 : hoi.keySet()) {
                    System.out.println(s1 + ":\t" + hoi.get(s1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document tokenizer(String data) {
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
