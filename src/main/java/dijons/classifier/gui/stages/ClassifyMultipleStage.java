package dijons.classifier.gui.stages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClassifyMultipleStage {

    @FXML
    Parent main;

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
    }

}
