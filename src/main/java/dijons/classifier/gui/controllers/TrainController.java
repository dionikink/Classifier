package dijons.classifier.gui.controllers;

import dijons.classifier.core.Classifier;
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
public class TrainController {

    @FXML
    public Parent train;
    public TextField txtSelected;
    public Button btnTrain;

    private File selectedFile;

    // Opens a FileChooser to allow the user to select a .zip file that will train the classifier
    public void btnBrowseClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select training file");
        Stage stage = (Stage) train.getScene().getWindow();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ZIP archives (*.zip)", "*.zip");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedFile = file;
            txtSelected.setText(file.getPath());
            btnTrain.setDisable(false);
        }
    }

    // Trains the classifier using the training set provided by the user
    public void btnTrainClicked() {
        Classifier c = Classifier.getInstance();
        c.train(selectedFile);
        MainController.setTrained(true);
        btnCancelClicked();
    }

    // Closes the current window
    public void btnCancelClicked() {
        cancel();
    }

    public void cancel() {
        Stage stage = (Stage) train.getScene().getWindow();
        stage.close();
    }
}
