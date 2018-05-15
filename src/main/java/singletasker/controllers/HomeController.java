package singletasker.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import singletasker.dao.ConfigDAOImpl;
import singletasker.dao.ConfigEntity;
import singletasker.models.Task;
import singletasker.models.TaskStateKind;
import singletasker.models.User;
import singletasker.utils.TaskCellFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the home view.
 */
public class HomeController implements Initializable {

    @FXML
    private TextField newTaskField;

    @FXML
    private ListView<Task> taskListView;

    @FXML
    private Label pointsLabel;

    @FXML
    private Label completedTasksLabel;

    @FXML
    private Label rankLabel;

    @FXML
    private ImageView settingsIcon;

    private User user = User.getInstance();

    private TaskList taskList = TaskList.getInstance();

    private ConfigDAOImpl configDAO = ConfigDAOImpl.getInstance();

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpSettings();
        logger.info("Settings set up");

        taskListView.setItems(taskList.getTasks());
        logger.info("Tasks set up");

        setUpUserConfigs();
        logger.info("Configs set up");

        setUpBindings();

        settingsIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Stage settings = createSettingsStage();
            settings.show();
            event.consume();
        });

        taskListView.setEditable(true);
        taskListView.setCellFactory(new TaskCellFactory());
    }

    private void setUpBindings() {
        pointsLabel.textProperty().bind(user.totalPointsProperty().asString().concat(" points"));
        completedTasksLabel.textProperty().bind(user.completedTasksProperty().asString().concat(" completed tasks"));
        rankLabel.textProperty().bind(user.userRankProperty());
    }

    private void setUpUserConfigs() {
        ConfigEntity userPoints = configDAO.findByKey("userTotalPoints");
        user.setTotalPoints(userPoints != null ? Integer.parseInt(userPoints.getConfigValue()) : 0);
        user.setUserRankByPoint();

        ConfigEntity userCompletedTasks = configDAO.findByKey("userCompletedTasks");
        user.setCompletedTasks(userCompletedTasks != null ? Integer.parseInt(userCompletedTasks.getConfigValue()) : 0);
    }

    private void setUpSettings() {
        ConfigEntity pomodoroTime = configDAO.findByKey("pomodoroTime");
        TaskStateKind.FOCUS.setDurationInMinutes(pomodoroTime != null ? Integer.parseInt(pomodoroTime.getConfigValue()) : 25);
        ConfigEntity shortBreakTime = configDAO.findByKey("shortBreakTime");
        TaskStateKind.SHORT_BREAK.setDurationInMinutes(shortBreakTime != null ? Integer.parseInt(shortBreakTime.getConfigValue()) : 5);
        ConfigEntity longBreakTime = configDAO.findByKey("longBreakTime");
        TaskStateKind.LONG_BREAK.setDurationInMinutes(longBreakTime != null ? Integer.parseInt(longBreakTime.getConfigValue()) : 15);
    }

    private Stage createSettingsStage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/settings.fxml"));

        Stage stage = new Stage();

        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            logger.error("Error while loading settings.fxml, here's some further info: {}", e.getMessage());
        }
        stage.setResizable(false);
        logger.info("Settings created");

        return stage;
    }

    @FXML
    void handleEnterOnNewTaskField(ActionEvent event) {
        if (newTaskFieldNotEmpty()) {
            String text = newTaskField.getText();
            taskList.addTask(new Task(text));
            logger.info("New task handled with name: " + text);
            emptyNewTaskField();
        }
    }

    private boolean newTaskFieldNotEmpty() {
        return !newTaskField.getText().equals("");
    }

    private void emptyNewTaskField() {
        newTaskField.setText("");
        logger.info("New task field emptied");
    }
}
