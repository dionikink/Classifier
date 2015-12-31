package dijons.classifier.gui.stages;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ClassifySelectStage {

    @FXML
    Parent main;

    public void start() {
        try {
            main = FXMLLoader.load(getClass().getResource("/gui/classifySelect.fxml"));
        } catch (IOException e) {
            System.err.println("Error loading classifySelect.fxml");
        }

        Stage stage = new Stage();
        stage.setTitle("Select method");
        stage.setScene(new Scene(main, 198, 76));
        stage.setResizable(false);
        stage.show();
    }

}
