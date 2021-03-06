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
import java.io.IOException;
import java.util.List;

/**
 * Created by dion on 9-12-15.
 */
public class ClassifyMultipleController {

    @FXML
    public Parent classifyMultiple;
    public Button btnClassify;
    public TextField txtSelected;

    private File selectedFile;

    // Extracts the files to classify from the .zip file (input) and then classifies them all
    // Also shows a feedback window (interactive) for every classified file
    public void btnClassifyClicked() {
        Classifier c = Classifier.getInstance();
        List<Document> documents = null;

        try {
            documents = DataUtils.extractDocuments(selectedFile);
        } catch (IOException e) {
            System.err.println("Error extracting documents from file");
            btnCancelClicked();
        }

        for(Document document : documents) {
            String result = c.apply(document);

            // Opens a new feedback window for the file that was just classified
            InteractiveStage interactiveStage = new InteractiveStage(true);
            interactiveStage.start(result, document);
        }

        btnCancelClicked();
    }

    // Opens a FileChooser to allow the user to select a file (.zip with multiple .txt files) to classify
    public void btnBrowseClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select files to classify");
        Stage stage = (Stage) classifyMultiple.getScene().getWindow();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ZIP archives (*.zip)", "*.zip");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedFile = file;
            txtSelected.setText(file.getPath());
            btnClassify.setDisable(false);
        }
    }

    // Closes the current window
    public void btnCancelClicked() {
        cancel();
    }

    public void cancel() {
        Stage stage = (Stage) classifyMultiple.getScene().getWindow();
        stage.close();
    }
}
