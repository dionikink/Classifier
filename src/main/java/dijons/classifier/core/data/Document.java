package dijons.classifier.core.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jensj.r on 12/9/2015.
 *
 */
public class Document {

    private Map<String, Integer> bagOfWords;
    private String category;

    public Document() {
        this.bagOfWords = new HashMap<String, Integer>();
    }

    public Document(Map<String, Integer> bagOfWords) {
        this.bagOfWords = bagOfWords;
    }
}
