package dijons.classifier.gui.stages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TrainStage {

    @FXML
    Parent main;

    // Opens a window that allows the user to train the classifier
    public void start() {
        try {
            main = FXMLLoader.load(getClass().getResource("/gui/train.fxml"));
        } catch (IOException e) {
            System.err.println("Error loading train.fxml");
        }

        Stage stage = new Stage();
        stage.setTitle("Train classifier");
        stage.setScene(new Scene(main, 428, 111));
        stage.setResizable(false);
        stage.show();
    }
}
