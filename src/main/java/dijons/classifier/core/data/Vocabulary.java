package dijons.classifier.core.data;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by dion on 9-12-15.
 */
public class Vocabulary {

    private Map<String, Map<String, Integer>> vocabulary;

    public Vocabulary(File file) {
        try {
            this.vocabulary = DataUtils.extractVocabulary(file);
        } catch (IOException e) {
            System.err.println("Cannot extract vocabulary from file.");
            System.exit(1);
        }
    }
}
