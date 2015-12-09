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

    public ArrayList<Document> getDocumentListForTraining(File file) throws IOException{
        ArrayList<Document> result = new ArrayList<Document>();
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        String className = null;
        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
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
                result.add(document);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        InputHandler inputHandler = new InputHandler();
        File file = new File("C:\\Users\\jensj.r\\Downloads\\blogs.zip");
        try {
            for ( Document d : inputHandler.getDocumentListForTraining(file)) {
                System.out.println(d.getCategory() + ": ");
                for (String s : d.getBagOfWords().keySet()) {
                    System.out.println(s + "\t\t\t" + d.getBagOfWords().get(s));
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
