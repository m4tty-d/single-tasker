package controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.Task;
import models.TaskState;
import models.TaskStateKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PomodoroController implements Initializable {
    @FXML
    private VBox root;

    @FXML
    private HBox btnHolder;

    @FXML
    private Label taskState;

    @FXML
    private Label taskName;

    private Task task;

    private StringProperty timerText;
    private Timeline timeline;

    private Logger logger = LoggerFactory.getLogger(PomodoroController.class);

    public PomodoroController() {
        timerText = new SimpleStringProperty();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setTask(Task task) {
        this.task = task;
        taskName.setText(task.getName());
        setTimerText(task.getCurrentState().getRemainingSeconds());
        // setTaskStateText();
        logger.info("Task set in Pomodoro");
    }

    public String getTimerText() {
        return timerText.get();
    }

    public StringProperty timerTextProperty() {
        return timerText;
    }

    public void setTimerText(String timerText) {
        this.timerText.set(timerText);
    }

    public void setTimerText(int timeInSeconds) {
        int minutes = timeInSeconds / 60;
        int seconds = timeInSeconds % 60;
        setTimerText(String.format("%02d:%02d", minutes, seconds));
    }

    public void handleStart(ActionEvent event) {
        playTimer();
        logger.info("Pomodoro timer started");
    }

    public void handlePause(ActionEvent event) {
        pauseTimer();
        logger.info("Pomodoro timer paused");
    }

    public void handleStop(ActionEvent event) {
        logger.info("Pomodoro timer stopped");

        if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
            // TODO Ask user whether she wants to stop the activity
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pomodoroRate.fxml"));

            Stage stage = new Stage();

            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                logger.error("Error while loading pomodoroRate.fxml, here's some further info: {}", e.getMessage());
                return;
            }

            logger.info("PomodoroRate created");

            PomodoroRateController controller = loader.getController();
            controller.setTask(task);

            root.getScene().getWindow().hide();
            logger.info("Pomodoro hidden");

            stage.show();
            logger.info("PomodoroRate showed");
        }
    }

    private void prepareTimer() {
        resetTimer();
        timeline = new Timeline();

        setTimerText(task.getCurrentState().getRemainingSeconds());
        timeline.setCycleCount(task.getCurrentState().getRemainingSeconds());

        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            task.getCurrentState().tickRemainingSeconds();
            setTimerText(task.getCurrentState().getRemainingSeconds());
        }));

        timeline.setOnFinished(event -> {
            if (task.getCurrentState().getKind() == TaskStateKind.FOCUS) {
                task.incrementPomodoroCount();

                if (task.getPomodoroCount() % 4 == 0) {
                    task.setCurrentState(new TaskState(TaskStateKind.LONG_BREAK));
                    // setTaskStateText();
                    prepareTimer();
                } else {
                    task.setCurrentState(new TaskState(TaskStateKind.SHORT_BREAK));
                    // setTaskStateText();
                    prepareTimer();
                }
            } else {
                task.setCurrentState(new TaskState((TaskStateKind.FOCUS)));
                // setTaskStateText();
                prepareTimer();
            }
        });
    }

    private void playTimer() {
        prepareTimer();
        timeline.play();
        showPauseBtn();
    }

    private void pauseTimer() {
        if (timeline != null) {
            timeline.pause();
            showStartBtn();
        }
    }

    private void resetTimer() {
        if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
            timeline.stop();
        }
        showStartBtn();
    }

    /*
    private void setTaskStateText() {
        taskState.setText(task.getCurrentState().getKind().toString());
    }*/

    private void showStartBtn() {
        btnHolder.getStyleClass().remove("playing");
    }

    private void showPauseBtn() {
        btnHolder.getStyleClass().add("playing");
    }
}