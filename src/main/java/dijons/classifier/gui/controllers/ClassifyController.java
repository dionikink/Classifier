package dijons.classifier.gui.controllers;

import dijons.classifier.core.Classifier;
import dijons.classifier.core.data.DataUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by dion on 9-12-15.
 */
public class ClassifyController {

    @FXML
    public Parent classify;
    public Button btnClassify;
    public TextField txtSelected;

    public File selectedFile;

    public void btnClassifyClicked() {
        Classifier c = Classifier.getInstance();
        String result = c.apply(DataUtils.extractDocument(selectedFile));

        System.out.println("Result: " + result);

        Stage stage = (Stage) classify.getScene().getWindow();
        stage.close();
    }

    public void btnBrowseClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file to classify");
        Stage stage = (Stage) classify.getScene().getWindow();

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
        Stage stage = (Stage) classify.getScene().getWindow();
        stage.close();
    }
}
