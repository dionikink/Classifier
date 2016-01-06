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

    // Opens a FileChooser to allow the user to select a file to test the classifier on
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

    // Opens a DirectoryChooser to allow the user to select where he wants to classifier to save its output
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

    // Tests the classifier using the given files
    public void btnTestClicked() {
        Classifier c = Classifier.getInstance();
        c.test(selectedFile, selectedOutput);
        Stage stage = (Stage) test.getScene().getWindow();
        stage.close();
    }

    // Enables the 'Test' button if both the source as well as the output location have been selected
    public void update() {
        if (sourceSelected && outputSelected) {
            btnTest.setDisable(false);
        } else {
            btnTest.setDisable(true);
        }
    }

    // Closes the current window
    public void btnCancelClicked() {
        cancel();
    }

    public void cancel() {
        Stage stage = (Stage) test.getScene().getWindow();
        stage.close();
    }


}
