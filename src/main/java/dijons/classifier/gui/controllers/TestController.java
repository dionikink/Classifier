package dijons.classifier.gui.controllers;

import dijons.classifier.core.Classifier;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
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
    public TextField txtOutput;
    public Button btnTest;

    private File selectedFile;
    private File selectedOutput;
    private boolean sourceSelected = false;
    private boolean outputSelected = false;

    public void btnBrowseSourceClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select test set");
        Stage stage = (Stage) test.getScene().getWindow();

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ZIP archives (*.zip)", "*.zip");
        fileChooser.getExtensionFilters().add(extFilter);

        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            selectedFile = file;
            txtSelected.setText(file.getPath());
            sourceSelected = true;
        } else {
            sourceSelected = false;
        }

        update();
    }

    public void btnBrowseOutputClicked() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select output location");
        Stage stage = (Stage) test.getScene().getWindow();

        File file = directoryChooser.showDialog(stage);
        if (file != null) {
            selectedOutput = file;
            txtOutput.setText(file.getAbsolutePath());
            outputSelected = true;
        } else {
            outputSelected = false;
        }

        update();
    }

    public void btnTestClicked() {
        Classifier c = Classifier.getInstance();
        c.test(selectedFile, selectedOutput);
        Stage stage = (Stage) test.getScene().getWindow();
        stage.close();
    }

    public void btnCancelClicked() {
        Stage stage = (Stage) test.getScene().getWindow();
        stage.close();
    }

    public void update() {
        if (sourceSelected && outputSelected) {
            btnTest.setDisable(false);
        } else {
            btnTest.setDisable(true);
        }
    }
}
