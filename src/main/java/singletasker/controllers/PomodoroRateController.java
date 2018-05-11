package singletasker.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import singletasker.models.Task;
import singletasker.models.TaskStateKind;
import singletasker.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import singletasker.utils.DifficultyLevelRangeException;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the pomodoroRate view.
 */
public class PomodoroRateController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private Slider difficultySlider;

    @FXML
    private Label difficultyLabel;

    private Task task;

    private User user = User.getInstance();

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
        try {
            task.setDifficultyLevel((int) difficultySlider.getValue());
        } catch (DifficultyLevelRangeException e) {
            logger.error(e.getMessage());
        }
        task.getCurrentState().setKind(TaskStateKind.FINISHED);
        user.addToTotalPoints(task.getPoints());
        user.incrementCompletedTasks();

        root.getScene().getWindow().hide();
        logger.info("Pomodoro rating finished");
    }
}
