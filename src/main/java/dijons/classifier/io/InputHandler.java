package dijons.classifier.io;

import dijons.classifier.core.data.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public HashMap<String, ArrayList<Document>> getDocumentListForTraining(File file) throws IOException{
        HashMap<String,ArrayList<Document>> result = new HashMap<String, ArrayList<Document>>();
        ArrayList<Document> arrayList = new ArrayList<Document>();
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        String className = null;
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                if (className != null) {
                    result.put(className, arrayList);
                    arrayList = new ArrayList<Document>();
                }
                className = entry.getName();
            } else {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
                String string = "";
                String s;
                while ((s = bufferedReader.readLine()) != null) {
                    string = string + s;
                }
                Document document = tokenizer(string);
                document.setCategory(className);
                arrayList.add(document);
            }
        }
        result.put(className, arrayList);
        return result;
    }

    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        File file = new File("C:\\Users\\jensj.r\\Downloads\\blogs.zip");
        try {
            HashMap<String, ArrayList<Document>> hoi = inputHandler.getDocumentListForTraining(file);
            for ( String s : hoi.keySet()) {
                System.out.println(s + ": ");
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
