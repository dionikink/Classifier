package dijons.classifier.core.data;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by dion on 9-12-15.
 *
 */
public class DataUtils {

    public static Map<String, Map<String, Integer>> extractVocabulary(File file) throws IOException{
        KnowledgeBase.data = new DataSet();
        Map<String, Map<String,Integer>> result = new HashMap<String, Map<String, Integer>>();
        ZipFile zipFile = new ZipFile(file);
        Enumeration<? extends ZipEntry> entries = zipFile.entries();
        count(entries);
        entries = zipFile.entries();
        String categoryName = null;
        Map<String, Integer> bagOfWords = new HashMap<String, Integer>();
        Map<String, Map<String, Double>> documentFrequencies = new HashMap<String, Map<String, Double>>();
        Map<String, Double> documentFrequency = new HashMap<String, Double>();

        while(entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.isDirectory()) {
                if (categoryName != null) {
                    result.put(categoryName, bagOfWords);
                    documentFrequencies.put(categoryName, documentFrequency);
                }
                bagOfWords = new HashMap<String, Integer>();
                documentFrequency = new HashMap<String, Double>();
                categoryName = entry.getName().replaceAll("[^a-z A-Z]", "");
            } else {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)));
                documentFrequency = fillBagOfWords(bufferedReader, categoryName, bagOfWords, documentFrequency);
                bufferedReader.close();
            }
        }
        if (categoryName != null) {
            result.put(categoryName, bagOfWords);
            documentFrequencies.put(categoryName, documentFrequency);
        }
        Vocabulary.getInstance().setDocumentFrequencies(documentFrequencies);
        return result;
    }

    public static ArrayList<Document> extractDocuments(File file) throws IOException {
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
                document.setName(entry.getName());
                result.add(document);
            }
        }
        return result;
    }

    public static Document extractDocument(File file) {
        Document result = null;
        String data = "";
        String newLine;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while((newLine = br.readLine()) != null) {
                data += newLine;
            }

            result = DataUtils.tokenizer(data);

        } catch (IOException e) {
            System.err.println("Cannot open file " + file.getName());
        }

        result.setName(file.getName());
        return result;
    }

    public static void countTotalWords(String[] wordArray, String className) {
        Map<String, Map<String, Integer>> noOfOccurrences = KnowledgeBase.data.getNoOfOccurrences();
        Map<String, Integer> totalWordCount;
        if (!noOfOccurrences.containsKey(className)) {
            totalWordCount = new HashMap<String, Integer>();
        } else {
            totalWordCount = KnowledgeBase.data.getTotalWordCount();
        }

        for (int i = 0; i < wordArray.length; i++) {
            if (totalWordCount.containsKey(wordArray[i])) {
                totalWordCount.replace(wordArray[i], totalWordCount.get(wordArray[i]) + 1);
            } else {
                totalWordCount.put(wordArray[i], 1);
            }
        }

        if (noOfOccurrences.containsKey(className)) {
            noOfOccurrences.replace(className, totalWordCount);
        } else {
            noOfOccurrences.put(className, totalWordCount);
        }

        KnowledgeBase.data.setNoOfOccurrences(noOfOccurrences);
        KnowledgeBase.data.setTotalWordCount(totalWordCount);
    }

    public static Map<String, Double> fillBagOfWords(BufferedReader bufferedReader, String className, Map<String, Integer> bagOfWords, Map<String,Double> documentFrequency) throws IOException {
        List<String> stopwords = Vocabulary.getInstance().getStopwords();
        List<String> doneWords = new ArrayList<String>();
        String string = "";
        String s;
        while((s = bufferedReader.readLine()) != null) {
            s = s.toLowerCase();
            s = s.replaceAll("[^a-z \\s]", "");
            string = string + s;
        }
        String[] wordArray = string.split("\\s");
        countTotalWords(wordArray, className);
        for (int i = 0; i < wordArray.length; i++) {
            if (!stopwords.contains(wordArray[i])) {
                if (bagOfWords.containsKey(wordArray[i])) {
                    bagOfWords.replace(wordArray[i], bagOfWords.get(wordArray[i]) + 1);
                } else {
                    bagOfWords.put(wordArray[i], 1);
                }
                if (!doneWords.contains(wordArray[i])) {
                    if (documentFrequency.containsKey(wordArray[i])) {
                        documentFrequency.replace(wordArray[i], documentFrequency.get(wordArray[i]) + 1);
                    } else {
                        documentFrequency.put(wordArray[i], 1d);
                    }
                    doneWords.add(wordArray[i]);
                }
            }
        }
        return documentFrequency;
    }

    public static void count(Enumeration<? extends ZipEntry> entries) {
        int totalNoOfDocs = 0;

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (!entry.isDirectory()) {
                totalNoOfDocs++;
            }
        }

        KnowledgeBase.numberOfDocuments = totalNoOfDocs;
    }

    public static Document tokenizer(String data) {
        HashMap<String, Integer> bagOfWords = new HashMap<String, Integer>();
        List<String> stopwords = Vocabulary.getInstance().getStopwords();
        String string = data.toLowerCase();
        string = string.replaceAll("[^a-z \\s]", "");
        StringTokenizer stringTokenizer = new StringTokenizer(string);

        while (stringTokenizer.hasMoreElements()) {
            string = stringTokenizer.nextToken();
            if (!stopwords.contains(string)) {
                if (bagOfWords.containsKey(string)) {
                    bagOfWords.replace(string, bagOfWords.get(string) + 1);
                } else {
                    bagOfWords.put(string, 1);
                }
            }
        }

        return new Document(bagOfWords);
    }

    public static Map<String, Integer> countDocumentsInClass(File file) {
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

    public static Map<String, Integer> countWordsInClass() {
        Map<String, Map<String,Integer>> vocabulary = KnowledgeBase.data.getNoOfOccurrences();
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
}
