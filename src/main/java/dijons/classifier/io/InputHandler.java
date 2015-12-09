package dijons.classifier.io;

import dijons.classifier.core.data.DataUtils;
import dijons.classifier.core.data.Document;

import java.io.*;
import java.util.*;
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
                Document document = DataUtils.tokenizer(string);
                result.add(document);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\jensj.r\\Downloads\\hoi.zip");
        try {
            Map<String, Map<String, Integer>> hoi = DataUtils.extractVocabulary(file);
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
}
