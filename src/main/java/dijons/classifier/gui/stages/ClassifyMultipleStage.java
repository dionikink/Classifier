package dijons.classifier.gui.stages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class ClassifyMultipleStage {

    @FXML
    Parent main;

    // Opens a window that allows the user to classify multiple files at once
    // Also shows the user a warning that he shouldn't classify too many files at once
    public void start() {
        try {
            main = FXMLLoader.load(getClass().getResource("/gui/classifyMultiple.fxml"));
        } catch (IOException e) {
            System.err.println("Error loading classifyMultiple.fxml");
        }

        Stage stage = new Stage();
        stage.setTitle("Classify multiple files");
        stage.setScene(new Scene(main, 428, 111));
        stage.setResizable(false);
        stage.show();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Important!");
        alert.setHeaderText("Do not classify more than 10 files at once!");
        alert.setContentText("An unresolved bug in the program will create feedback windows for all classified files simultaneously. If you try to classify 200 files, it will open 200 windows.");

        alert.showAndWait();
    }

}
