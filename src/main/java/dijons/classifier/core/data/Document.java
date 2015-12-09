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
    private String name;

    public Document() {
        this.bagOfWords = new HashMap<String, Integer>();
    }

    public Document(Map<String, Integer> bagOfWords) {
        this.bagOfWords = bagOfWords;
    }

    public Map<String, Integer> getBagOfWords() {
        return bagOfWords;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
