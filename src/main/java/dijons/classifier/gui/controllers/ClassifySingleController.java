package dijons.classifier.gui.controllers;

import dijons.classifier.core.Classifier;
import dijons.classifier.core.data.DataUtils;
import dijons.classifier.core.data.Document;
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
public class ClassifySingleController {

    @FXML
    public Parent classifySingle;
    public Button btnClassify;
    public TextField txtSelected;

    public File selectedFile;

    public void btnClassifyClicked() {
        Classifier c = Classifier.getInstance();
        Document document = DataUtils.extractDocument(selectedFile);
        String result = c.apply(document);

        InteractiveStage interactiveStage = new InteractiveStage();
        interactiveStage.start(result, document);

        Stage stage = (Stage) classifySingle.getScene().getWindow();
        stage.close();
    }

    public void btnBrowseClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file to classify");
        Stage stage = (Stage) classifySingle.getScene().getWindow();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedFile = file;
            txtSelected.setText(file.getPath());
            btnClassify.setDisable(false);
        }
    }

    public void btnCancelClicked() {
        Stage stage = (Stage) classifySingle.getScene().getWindow();
        stage.close();
    }
}
