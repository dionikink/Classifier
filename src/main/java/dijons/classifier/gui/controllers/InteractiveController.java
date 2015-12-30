package dijons.classifier.gui.controllers;

import dijons.classifier.core.data.DataUtils;
import dijons.classifier.core.data.Document;
import dijons.classifier.core.data.Vocabulary;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
    public Label lblResult;
    public ChoiceBox<String> bxCorrectClass;

    private String result;
    private File file;

    public InteractiveController(String result, File file ) {
        this.result = result;
        this.file = file;
    }

    public void initialize() {
        lblResult.setText(result);
        List<String> classes = Vocabulary.getInstance().getClasses();

        bxCorrectClass.setTooltip(new Tooltip("Select the correct class"));
        bxCorrectClass.setItems(FXCollections.observableArrayList(classes));
    }

    public void bxCorrectClassChanged() {
        btnOK.setDisable(false);
    }

    public void btnOKClicked() {
        Document document = DataUtils.extractDocument(file);

    }

    public void btnCancelClicked() {
        Stage stage = (Stage) interactive.getScene().getWindow();
        stage.close();
    }
}
