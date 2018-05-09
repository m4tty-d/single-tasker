package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Singleton
 * This class is used to handle the storage of the tasks.
 */
public class TaskList {

    private ObservableList<Task> tasks;

    private static TaskList instance = null;

    private Logger logger = LoggerFactory.getLogger(TaskList.class);

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
        logger.info("Task added to TaskList with name: " + t.getName());
    }

    public void removeTask(Task t) {
        tasks.remove(t);
        logger.info("Task removed from list named: " + t.getName());
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }
}
