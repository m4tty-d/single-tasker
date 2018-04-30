package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Task;
import models.TaskList;
import utils.TaskCellFactory;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private TextField newTaskField;

    @FXML
    private ListView<Task> taskListView;

    private TaskList taskList = TaskList.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskListView.setItems(taskList.getTasks());

        taskListView.setEditable(true);
        taskListView.setCellFactory(new TaskCellFactory());
    }

    @FXML
    void handleEnterOnNewTaskField(ActionEvent event) {
        if (newTaskFieldNotEmpty()) {
            taskList.addTask(new Task(newTaskField.getText()));
            emptyNewTaskField();
        }
    }

    private boolean newTaskFieldNotEmpty() {
        return !newTaskField.getText().equals("");
    }

    private void emptyNewTaskField() {
        newTaskField.setText("");
    }
}
