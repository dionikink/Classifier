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
public class TestController {

    @FXML
    public Parent test;
    public TextField txtSelected;
    public Button btnTest;

    private File selectedFile;

    public void btnBrowseClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select test file");
        Stage stage = (Stage) test.getScene().getWindow();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ZIP archives (*.zip)", "*.zip");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedFile = file;
            txtSelected.setText(file.getPath());
            btnTest.setDisable(false);
        }
    }

    public void btnTestClicked() {

    }

    public void btnCancelClicked() {
        Stage stage = (Stage) test.getScene().getWindow();
        stage.close();
    }
}
