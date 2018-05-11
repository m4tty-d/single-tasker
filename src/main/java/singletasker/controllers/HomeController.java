package singletasker.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import singletasker.models.Task;
import singletasker.models.TaskList;
import singletasker.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import singletasker.utils.TaskCellFactory;
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

    private TaskList taskList = TaskList.getInstance();

    private User user = User.getInstance();

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskListView.setItems(taskList.getTasks());
        logger.info("Setting up tasks");

        pointsLabel.textProperty().bind(user.totalPointsProperty().asString().concat(" points"));
        completedTasksLabel.textProperty().bind(user.completedTasksProperty().asString().concat(" completed tasks"));
        rankLabel.textProperty().bind(user.userRankProperty());

        taskListView.setEditable(true);
        taskListView.setCellFactory(new TaskCellFactory());
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
