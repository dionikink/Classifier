package dijons.classifier.gui.controllers;

import dijons.classifier.gui.ClassifyStage;
import dijons.classifier.gui.TestStage;
import dijons.classifier.gui.TrainStage;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;

/**
 * Created by jensj.r on 12/7/2015.
 *
 */
public class MainController {

    @FXML
    Parent main;

    public void btnTrainClicked() {
        TrainStage trainStage = new TrainStage();
        trainStage.start();
    }

    public void btnClassifyClicked() {
        ClassifyStage classifyStage = new ClassifyStage();
        classifyStage.start();
    }

    public void btnTestClicked() {
        TestStage testStage = new TestStage();
        testStage.start();
    }
}
