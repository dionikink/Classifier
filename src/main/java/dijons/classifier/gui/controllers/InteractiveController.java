package dijons.classifier.gui.controllers;

import dijons.classifier.core.Classifier;
import dijons.classifier.core.data.Document;
import dijons.classifier.core.data.Vocabulary;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    // The InteractiveController receives the result of the classification as a parameter to show the user the result
    public InteractiveController(String result, Document document, boolean multiple) {
        this.result = result;
        this.document = document;
        this.multiple = multiple;
    }

    // Sets the text that displays the result of the classification
    public void initialize() {
        txtInput.setText(document.getName() + " was classified as " + result);
        List<String> classes = Vocabulary.getInstance().getClasses();

        bxCorrectClass.setTooltip(new Tooltip("Select the correct class"));
        bxCorrectClass.setItems(FXCollections.observableArrayList(classes));
    }

    // Enables the 'OK' button when the user selects an item from the ChoiceBox
    public void bxCorrectClassChanged() {
        btnOK.setDisable(false);
    }

    // Trains the classifier with the file that was just classified and the user's feedback
    public void btnOKClicked() {
        String result = bxCorrectClass.getValue();

        Classifier.getInstance().trainSingleDocument(document, result);

        cancel();

        if (!multiple) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("You are amazing");
            alert.setHeaderText("Thank you for your feedback!");
            alert.setContentText("The classifier will use this information to improve its classifications.");

            alert.showAndWait();
        }
    }

    // Closes the current window
    public void btnCancelClicked() {
        cancel();
    }

    public void cancel() {
        Stage stage = (Stage) interactive.getScene().getWindow();
        stage.close();
    }
}
