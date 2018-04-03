package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This class is used to handle the storage of the tasks.
 */
public class TaskList {

    private ObservableList<Task> tasks;

    private static TaskList instance = null;

    private TaskList() {
        tasks = FXCollections.observableArrayList();
    }

    public static TaskList getInstance() {
        if (instance == null) {
            instance = new TaskList();
        }

        return instance;
    }

    public void addTask(Task t) {
        tasks.add(0, t);
    }

    public void removeTask(Task t) {
        tasks.remove(t);
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }
}
