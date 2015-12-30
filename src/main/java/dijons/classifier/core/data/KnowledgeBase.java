package dijons.classifier.core.data;

import java.util.List;
import java.util.Map;

/**
 * Created by jensj.r on 12/7/2015.
 *
 */
public class KnowledgeBase {

    public static int numberOfDocuments = 0;

    public static DataSet data;

    private Map<String, Double> prior;
    private Map<String, Map<String, Double>> condProb;
    private List<String> classes;

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

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }
}
