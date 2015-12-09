package dijons.classifier.core;

import dijons.classifier.core.data.Document;
import dijons.classifier.core.data.KnowledgeBase;
import dijons.classifier.core.data.Vocabulary;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jensj.r on 12/7/2015.
 *
 */
public class Classifier {

    private KnowledgeBase knowledgeBase;

    public void apply(ArrayList<Document> arrayList) {
        for (Document document : arrayList) {
            //Classify die mofo
        }
    }

    public void train(List<String> classes, File file) {
        Vocabulary v = new Vocabulary(file);
        int numberOfDocuments = 0;
    }
}
