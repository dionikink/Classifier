package dijons.classifier.gui.stages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClassifySingleStage {

    @FXML
    Parent main;

    // Opens a window that allows the user to classify a single file
    public void start() {
        try {
            main = FXMLLoader.load(getClass().getResource("/gui/classifySingle.fxml"));
        } catch (IOException e) {
            System.err.println("Error loading classifySingle.fxml");
        }

        Stage stage = new Stage();
        stage.setTitle("Classify single file");
        stage.setScene(new Scene(main, 428, 111));
        stage.setResizable(false);
        stage.show();
    }

}
