package dijons.classifier.gui;

import dijons.classifier.gui.controllers.InteractiveController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class InteractiveStage {

    @FXML
    Parent main;

    public void start(String result, File file) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/interactive.fxml"));
        InteractiveController interactiveController = new InteractiveController(result, file);
        loader.setController(interactiveController);

        try {
            main = loader.load();
        } catch (IOException e) {
            System.err.println("Error loading root");
        }

        Stage stage = new Stage();
        stage.setTitle("Give feedback");
        stage.setScene(new Scene(main, 360, 139));
        stage.setResizable(false);
        stage.show();
    }

}
