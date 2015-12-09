package dijons.classifier.gui.controllers;

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

    public void btnTrainClicked() {

    }

    public void btnCancelClicked() {
        Stage stage = (Stage) train.getScene().getWindow();
        stage.close();
    }
}