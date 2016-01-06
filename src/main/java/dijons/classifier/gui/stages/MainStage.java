package dijons.classifier.gui.stages;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainStage extends Application {

    // Opens the main menu
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/gui/main.fxml"));
        primaryStage.setScene(new Scene(root, 217, 76));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    // Starts the program
    public static void main(String[] args) {
        launch(args);
    }
}
