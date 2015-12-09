package dijons.classifier.core;

import dijons.classifier.core.data.Document;

import java.util.List;
import java.util.Set;

/**
 * Created by jensj.r on 12/8/2015.
 *
 */
public class Trainer {

    Classifier classifier;

    public Trainer(Classifier classifier) {
        this.classifier = classifier;
    }

    public void train(Set<AIClass> set, Document document) {
        List<String> class1 = document.getClass1List();
        List<String> class2 = document.getClass2List();
        int noOfDocs = class1.size() + class2.size();
        int n1 = class1.size();
        int n2 = class2.size();
    }


}
