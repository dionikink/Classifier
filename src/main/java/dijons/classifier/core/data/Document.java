package dijons.classifier.core.data;

import java.util.Map;

/**
 * Created by jensj.r on 12/9/2015.
 *
 */
public class Document {

    private Map<String, Integer> bagOfWords;
    private String name;

    public Document(Map<String, Integer> bagOfWords) {
        this.bagOfWords = bagOfWords;
    }

    public Map<String, Integer> getBagOfWords() {
        return bagOfWords;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
