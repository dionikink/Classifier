package dijons.classifier.core.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jensj.r on 12/7/2015.
 *
 */
public class KnowledgeBase {

    public static int numberOfDocuments = 0;
    public static int numberOfCategories = 0;

    private Map<String, Double> prior = new HashMap<String, Double>();
    private Map<String, Map<String, Double>> condProb = new HashMap<String, Map<String, Double>>();

    public Map<String, Double> getPrior() {
        return prior;
    }

    public void setPrior(Map<String, Double> prior) {
        this.prior = prior;
    }

    public Map<String, Map<String, Double>> getCondProb() {
        return condProb;
    }

    public void setCondProb(Map<String, Map<String, Double>> condProb) {
        this.condProb = condProb;
    }
}
