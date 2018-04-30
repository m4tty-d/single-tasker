package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.Task;
import models.TaskState;
import models.TaskStateKind;
import java.net.URL;
import java.util.ResourceBundle;

public class PomodoroRateController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private TextField difficulty;

    private Task task;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void handleFinish(ActionEvent event) {
        task.setDifficultyLevel(Integer.parseInt(difficulty.getText()));
        task.setCurrentState(new TaskState(TaskStateKind.FINISHED));
        root.getScene().getWindow().hide();
    }
}
