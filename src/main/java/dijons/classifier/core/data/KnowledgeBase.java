package dijons.classifier.core.data;

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
    private Map<String, Integer> totalDocsInClasses;
    private Map<String, Integer> totalWordsInClasses;

    public Map<String, Integer> getTotalWordsInClasses() {return totalWordsInClasses; }

    public void setTotalWordsInClasses(Map<String, Integer> totalWordsInClasses) {this.totalWordsInClasses = totalWordsInClasses; }

    public Map<String, Integer> getTotalDocsInClasses() {return totalDocsInClasses; }

    public void setTotalDocsInClasses(Map<String, Integer> totalDocsInClasses) {this.totalDocsInClasses = totalDocsInClasses; }

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
