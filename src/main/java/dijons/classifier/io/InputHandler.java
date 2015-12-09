package dijons.classifier.io;

import dijons.classifier.core.data.Document;

import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by jensj.r on 12/7/2015.
 *
 */
public class InputHandler {

    public Document tokenizer(String data) {
        String s;
        HashMap<String, Integer> bagOfWords = new HashMap<String, Integer>();

        String string = data.toLowerCase();
        string = string.replaceAll("[^a-z \\s]", "");
        StringTokenizer stringTokenizer = new StringTokenizer(string);
        while (stringTokenizer.hasMoreElements()) {
            s = stringTokenizer.nextToken();
            if (bagOfWords.containsKey(s)) {
                bagOfWords.replace(s, bagOfWords.get(s) + 1);
            } else {
                bagOfWords.put(s, 1);
            }
        }

        return new Document(bagOfWords);
    }
}
