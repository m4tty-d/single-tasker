package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import models.Task;
import models.TaskState;
import models.TaskStateKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class PomodoroRateController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Slider difficultySlider;

    @FXML
    private Label difficultyLabel;

    private Task task;

    private Logger logger = LoggerFactory.getLogger(PomodoroController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        difficultySlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            difficultySlider.setValue(newVal.intValue());
            difficultyLabel.setText(String.valueOf(newVal.intValue()));
        });
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void handleFinish(ActionEvent event) {
        task.setDifficultyLevel((int) difficultySlider.getValue());
        task.setCurrentState(new TaskState(TaskStateKind.FINISHED));
        root.getScene().getWindow().hide();
        logger.info("Pomodoro rating finished");
    }
}
