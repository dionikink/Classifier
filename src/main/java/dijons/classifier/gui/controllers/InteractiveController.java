package dijons.classifier.gui.controllers;

import dijons.classifier.core.Classifier;
import dijons.classifier.core.data.DataUtils;
import dijons.classifier.core.data.Document;
import dijons.classifier.core.data.Vocabulary;
import dijons.classifier.gui.stages.ClassifyMultipleStage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

/**
 * Created by dion on 9-12-15.
 */
public class InteractiveController {

    @FXML
    public Parent interactive;
    public Button btnOK;
    public Text txtInput;
    public ChoiceBox<String> bxCorrectClass;

    private String result;
    private Document document;
    private boolean multiple;

    public InteractiveController(String result, Document document, boolean multiple) {
        this.result = result;
        this.document = document;
        this.multiple = multiple;
    }

    public void initialize() {
        txtInput.setText(document.getName() + " was classified as " + result);
        List<String> classes = Vocabulary.getInstance().getClasses();

        bxCorrectClass.setTooltip(new Tooltip("Select the correct class"));
        bxCorrectClass.setItems(FXCollections.observableArrayList(classes));
    }

    public void bxCorrectClassChanged() {
        btnOK.setDisable(false);
    }

    public synchronized void btnOKClicked() {
        String result = bxCorrectClass.getValue();

        Classifier.getInstance().trainSingleDocument(document, result);

        btnCancelClicked();

        if (!multiple) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You are amazing");
            alert.setHeaderText("Thank you for your feedback!");
            alert.setContentText("The classifier will use this information to improve its classifications.");

            alert.showAndWait();
        }
    }

    public void btnCancelClicked() {
        Stage stage = (Stage) interactive.getScene().getWindow();
        stage.close();
    }
}
