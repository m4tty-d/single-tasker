package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Task;
import models.TaskList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.TaskCellFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private TextField newTaskField;

    @FXML
    private ListView<Task> taskListView;

    private TaskList taskList = TaskList.getInstance();

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskListView.setItems(taskList.getTasks());
        logger.info("Setting up tasks");

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
