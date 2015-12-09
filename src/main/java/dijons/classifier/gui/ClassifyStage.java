package dijons.classifier.gui;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClassifyStage {

    @FXML
    Parent main;

    public void start() {
        try {
            main = FXMLLoader.load(getClass().getResource("/classify.fxml"));
        } catch (IOException e) {
            System.err.println("Error loading classify.fxml");
        }

        Stage stage = new Stage();
        stage.setTitle("Classify data");
        stage.setScene(new Scene(main, 428, 167));
        stage.setResizable(false);
        stage.show();
    }

}