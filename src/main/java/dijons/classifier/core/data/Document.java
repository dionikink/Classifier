package dijons.classifier.core.data;

import java.util.List;

/**
 * Created by jensj.r on 12/9/2015.
 *
 */
public class Document {

    public List<String> getClass1List() {
        return class1List;
    }

    public void setClass1List(List<String> class1List) {
        this.class1List = class1List;
    }

    public List<String> getClass2List() {
        return class2List;
    }

    public void setClass2List(List<String> class2List) {
        this.class2List = class2List;
    }

    List<String> class1List;
    List<String> class2List;
    /*
    Goede manier van bag of words opslaan;
     */
}
