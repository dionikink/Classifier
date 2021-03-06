package dijons.classifier.gui.stages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TestStage {

    @FXML
    Parent main;

    // Opens a new window that allows the user to test the classifier
    public void start() {
        try {
            main = FXMLLoader.load(getClass().getResource("/gui/test.fxml"));
        } catch (IOException e) {
            System.err.println("Error loading test.fxml");
        }

        Stage stage = new Stage();
        stage.setTitle("Test classifier");
        stage.setScene(new Scene(main, 428, 167));
        stage.setResizable(false);
        stage.show();
    }
}
