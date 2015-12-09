package dijons.classifier.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by jensj.r on 12/7/2015.
 *
 */
public class FileInput {

    ZipFile class1;
    ZipFile class2;

    HashMap<String, Integer> map1;
    HashMap<String, Integer> map2;

    public FileInput(String address1, String address2) throws IOException {
        try {
            class1 = new ZipFile(address1);
            class2 = new ZipFile(address2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tokenizeData();
    }

    public void tokenizeData() throws IOException {
        Enumeration<? extends ZipEntry> entriesClass1 = class1.entries();
        Enumeration<? extends ZipEntry> entriesClass2 = class2.entries();
        map1 = getBagOfWordsMap(entriesClass1, true);
        map2 = getBagOfWordsMap(entriesClass2, false);
    }

    public HashMap<String, Integer> getBagOfWordsMap(Enumeration<? extends ZipEntry> entries, boolean isClass1) throws IOException {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        String s;
        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            BufferedReader bufferedReader;
            if (isClass1) {
                bufferedReader = new BufferedReader(new InputStreamReader(class1.getInputStream(entry)));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(class2.getInputStream(entry)));
            }
            String string;
            while((string = bufferedReader.readLine()) != null) {
                string = string.toLowerCase();
                string = string.replaceAll("[^a-z \\s]", "");
                StringTokenizer stringTokenizer = new StringTokenizer(string);
                while(stringTokenizer.hasMoreElements()) {
                    s = stringTokenizer.nextToken();
                    if (result.containsKey(s)) {
                        result.replace(s, result.get(s) + 1);
                    } else {
                        result.put(s, 1);
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        FileInput fileInput = null;
        try {
            fileInput = new FileInput("C:\\Users\\jensj.r\\Downloads\\F.zip", "C:\\Users\\jensj.r\\Downloads\\M.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileInput != null) {
            System.out.println("Class 1:");
            for (String s : fileInput.getMap1().keySet()) {
                System.out.println(s + "\t\t" + fileInput.getMap1().get(s));
            }
            System.out.println("Class 2:");
            for (String s : fileInput.getMap2().keySet()) {
                System.out.println(s + "\t\t" + fileInput.getMap2().get(s));
            }
        }
    }

    public HashMap<String, Integer> getMap1() {
        return map1;
    }
    public HashMap<String, Integer> getMap2() {
        return map2;
    }
}
