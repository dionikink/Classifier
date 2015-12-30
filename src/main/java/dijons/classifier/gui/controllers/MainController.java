package dijons.classifier.gui.controllers;

import dijons.classifier.gui.stages.ClassifyStage;
import dijons.classifier.gui.stages.TestStage;
import dijons.classifier.gui.stages.TrainStage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 * Created by jensj.r on 12/7/2015.
 *
 */
public class MainController {

    private static boolean trained = false;

    @FXML
    public Button btnTrain;

    public void btnTrainClicked() {
        if (trained) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops...");
            alert.setHeaderText("The classifier is already trained!");
            alert.setContentText("Use the 'Classify' option to start classifying new files. You can provide feedback to the classifier in the form of a correction.");
            alert.showAndWait();
        } else {
            TrainStage trainStage = new TrainStage();
            trainStage.start();
        }
    }

    public void btnClassifyClicked() {
        if (!trained) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops...");
            alert.setHeaderText("The classifier is not trained yet");
            alert.setContentText("In order to classify a new file, the classifier has to be trained first. Use the 'Train' option to do so.");
            alert.showAndWait();
        } else {
            ClassifyStage classifyStage = new ClassifyStage();
            classifyStage.start();
        }
    }

    public void btnTestClicked() {
        if (!trained) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Oops...");
            alert.setHeaderText("The classifier is  not trained yet");
            alert.setContentText("In order to test the classifier, it has to be trained first. Use the 'Train' option to do so.");
            alert.showAndWait();
        } else {
            ClassifyStage classifyStage = new ClassifyStage();
            classifyStage.start();
        }
    }

    public static void setTrained(boolean trained) {
        MainController.trained = trained;
    }
}
