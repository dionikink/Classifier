package dijons.classifier.gui.controllers;

import dijons.classifier.gui.stages.ClassifyMultipleStage;
import dijons.classifier.gui.stages.ClassifySingleStage;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Created by dion on 9-12-15.
 */
public class ClassifySelectController {

    @FXML
    public Parent classifySelect;
    public Button btnSingle;
    public Button btnMultiple;

    // Opens a window to classify a single .txt file
    public void btnSingleClicked() {
        ClassifySingleStage classifySingleStage = new ClassifySingleStage();
        classifySingleStage.start();
        cancel();
    }

    // Open a window to classify multiple .txt files (bundled in a .zip)
    public void btnMultipleClicked() {
        ClassifyMultipleStage classifyMultipleStage = new ClassifyMultipleStage();
        classifyMultipleStage.start();
        cancel();
    }

    // Closes the current window
    public void cancel() {
        Stage stage = (Stage) classifySelect.getScene().getWindow();
        stage.close();
    }

}
