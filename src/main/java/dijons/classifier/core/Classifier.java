package dijons.classifier.core;

import dijons.classifier.core.data.DataUtils;
import dijons.classifier.core.data.KnowledgeBase;
import dijons.classifier.core.data.Vocabulary;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jensj.r on 12/7/2015.
 *
 */
public class Classifier {

    private KnowledgeBase knowledgeBase;

    public void apply(File file) {

    }

    public void train(File file) {
        Vocabulary v = new Vocabulary(file);
        int numberOfDocuments = KnowledgeBase.numberOfDocuments;
        Map<String, Double> prior = new HashMap<String, Double>();
        Map<String, Map<String, Double>> condProb = new HashMap<String, Map<String, Double>>();

        List<String> classes = v.getClasses();
        Map<String, Integer> docsInClass = DataUtils.countDocsInClass(file);

        for(String classEntry : classes) {
            int docsInThisClass = docsInClass.get(classEntry);
            prior.put(classEntry, Math.log((double)(docsInThisClass/numberOfDocuments)));

        }
    }
}
