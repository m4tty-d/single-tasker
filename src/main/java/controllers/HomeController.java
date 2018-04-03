package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.Callback;
import models.Task;
import models.TaskList;

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

        // show only the name of the task in the list
        taskListView.setCellFactory(param -> new TextFieldListCell<>() {});
    }

    @FXML
    void handleEnterOnNewTaskField(ActionEvent event) {
        taskList.addTask(new Task(newTaskField.getText()));
        newTaskField.setText("");
    }
}
