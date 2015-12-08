package dijons.classifier.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by jensj.r on 12/7/2015.
 *
 */
public class FileInput {

    ZipFile class1;
    ZipFile class2;

    public HashMap<String, List<String>> getMap() {
        return map;
    }

    public void setMap(HashMap<String, List<String>> map) {
        this.map = map;
    }

    HashMap<String, List<String>> map;

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
        map = new HashMap<String, List<String>>();
        map = getClassContent(map, entriesClass1, "Class 1");
//        map = getClassContent(map, entriesClass2, "Class 2");
    }

    /*
    Werkt niet echt lekker
     */
    public HashMap<String, List<String>> getClassContent(HashMap<String, List<String>> givenMap, Enumeration<? extends  ZipEntry> entries, String className) throws IOException {
        List<String> zipEntry = new ArrayList<String>();
        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            InputStream inputStream = class1.getInputStream(entry);
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
            String string;
            while((string = bufferedReader.readLine()) != null) {
                string = string.trim();
                System.out.println(string);
            }
        }
        givenMap.put(className, zipEntry);
        return givenMap;
    }

    public static void main(String[] args) {
        FileInput fileInput = null;
        try {
            fileInput = new FileInput("C:\\Users\\jensj.r\\Downloads\\F.zip", "C:\\Users\\jensj.r\\Downloads\\M.zip");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (fileInput != null) {
            for (String s : fileInput.getMap().keySet()) {
                System.out.println(fileInput.getMap().get(s));
            }
        }
    }
}
