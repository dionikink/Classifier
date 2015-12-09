package dijons.classifier.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TestStage {

    @FXML
    Parent main;

    public void start() {
        try {
            main = FXMLLoader.load(getClass().getResource("/test.fxml"));
        } catch (IOException e) {
            System.err.println("Error loading test.fxml");
        }

        Stage stage = new Stage();
        stage.setTitle("Test classifier");
        stage.setScene(new Scene(main, 428, 111));
        stage.setResizable(false);
        stage.show();
    }
}
