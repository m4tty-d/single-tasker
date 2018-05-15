package singletasker.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import org.apache.maven.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import singletasker.dao.ConfigDAOImpl;
import singletasker.dao.ConfigEntity;
import singletasker.models.TaskStateKind;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SettingsController implements Initializable {

    @FXML
    private ComboBox<String> pomodoroTimeBox;

    @FXML
    private ComboBox<String> shortBreakBox;

    @FXML
    private ComboBox<String> longBreakBox;

    private ConfigDAOImpl configDAO = ConfigDAOImpl.getInstance();

    private TaskList taskList = TaskList.getInstance();

    private Logger logger = LoggerFactory.getLogger(SettingsController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Integer> times = Arrays.asList(5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60);
        List<String> timeString = times.stream().map(time -> time + " minutes").collect(Collectors.toList());

        pomodoroTimeBox.getItems().addAll(timeString);
        pomodoroTimeBox.getSelectionModel().select(times.indexOf(TaskStateKind.FOCUS.getDurationInMinutes()));

        shortBreakBox.getItems().addAll(timeString);
        shortBreakBox.getSelectionModel().select(times.indexOf(TaskStateKind.SHORT_BREAK.getDurationInMinutes()));

        longBreakBox.getItems().addAll(timeString);
        longBreakBox.getSelectionModel().select(times.indexOf(TaskStateKind.LONG_BREAK.getDurationInMinutes()));

        logger.info("Default settings set");
    }

    public void handleSaveBtnClick(ActionEvent event) {
        int pomodoroTime = getNumberFromString(pomodoroTimeBox.getValue());
        int shortBreak = getNumberFromString(shortBreakBox.getValue());
        int longBreak = getNumberFromString(longBreakBox.getValue());

        TaskStateKind.FOCUS.setDurationInMinutes(pomodoroTime);
        configDAO.save(new ConfigEntity("pomodoroTime", String.valueOf(pomodoroTime)));
        TaskStateKind.SHORT_BREAK.setDurationInMinutes(shortBreak);
        configDAO.save(new ConfigEntity("shortBreakTime", String.valueOf(shortBreak)));
        TaskStateKind.LONG_BREAK.setDurationInMinutes(longBreak);
        configDAO.save(new ConfigEntity("longBreakTime", String.valueOf(longBreak)));

        logger.info("Settings saved");

        updateTaskDurationsInTaskList();
        logger.info("Task times in TaskList updated");
    }

    private void updateTaskDurationsInTaskList() {
        taskList.getTasks().stream().forEach(task -> task.getCurrentState().setRemainingSeconds(task.getCurrentState().getKind().getDurationInSeconds()));
    }

    private Integer getNumberFromString(String str) {
        return Integer.parseInt(str.replaceAll("\\D+",""));
    }
}
