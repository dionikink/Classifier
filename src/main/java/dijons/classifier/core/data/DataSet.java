package dijons.classifier.core.data;

import java.util.HashMap;
import java.util.Map;
import java.util.jar.Pack200;

/**
 * Created by Dion on 29-12-2015.
 */
public class DataSet {

    private Map<String, Map<String,Integer>> noOfOccurrences;
    private Map<String,Integer> totalWordCount;

    public DataSet() {
        this.noOfOccurrences = new HashMap<String, Map<String, Integer>>();
        this.totalWordCount = new HashMap<String, Integer>();
    }

    public Map<String, Map<String, Integer>> getNoOfOccurrences() {
        return noOfOccurrences;
    }

    public Map<String, Integer> getTotalWordCount() {
        return totalWordCount;
    }

    public void setNoOfOccurrences(Map<String, Map<String, Integer>> noOfOccurrences) {
        this.noOfOccurrences = noOfOccurrences;
    }

    public void setTotalWordCount(Map<String, Integer> totalWordCount) {
        this.totalWordCount = totalWordCount;
    }
}
