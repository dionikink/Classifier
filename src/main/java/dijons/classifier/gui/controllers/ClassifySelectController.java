package dijons.classifier.gui.controllers;

import dijons.classifier.core.Classifier;
import dijons.classifier.core.data.DataUtils;
import dijons.classifier.core.data.Document;
import dijons.classifier.gui.stages.ClassifyMultipleStage;
import dijons.classifier.gui.stages.ClassifySingleStage;
import dijons.classifier.gui.stages.InteractiveStage;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by dion on 9-12-15.
 */
public class ClassifySelectController {

    @FXML
    public Parent classifySelect;
    public Button btnSingle;
    public Button btnMultiple;

    public void btnSingleClicked() {
        ClassifySingleStage classifySingleStage = new ClassifySingleStage();
        classifySingleStage.start();
        cancel();
    }

    public void btnMultipleClicked() {
        ClassifyMultipleStage classifyMultipleStage = new ClassifyMultipleStage();
        classifyMultipleStage.start();
        cancel();
    }

    public void cancel() {
        Stage stage = (Stage) classifySelect.getScene().getWindow();
        stage.close();
    }

}
