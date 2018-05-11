package singletasker.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the task list.
 */
public class TaskList {

    /**
     * Stores the tasks.
     */
    private ObservableList<Task> tasks;

    /**
     * The task list's instance.
     */
    private static TaskList instance = null;

    /**
     * Logger instance used for logging.
     */
    private Logger logger = LoggerFactory.getLogger(TaskList.class);

    /**
     * The contructor is disabled. (singleton)
     */
    private TaskList() {
        tasks = FXCollections.observableArrayList();
    }

    /**
     * Creates the TaskList instance or returns it if it's already created.
     * @return the task list instance
     */
    public static TaskList getInstance() {
        if (instance == null) {
            instance = new TaskList();
        }

        return instance;
    }

    /**
     * Adds a task to the list.
     * @param t the task to be added
     */
    public void addTask(Task t) {
        tasks.add(0, t);
        logger.info("Task added to TaskList with name: " + t.getName());
    }

    /**
     * Removes a task from the list.
     * @param t the task to be removed
     */
    public void removeTask(Task t) {
        tasks.remove(t);
        logger.info("Task removed from list named: " + t.getName());
    }

    /**
     * Gets the size of the task list.
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Gets all the tasks.
     * @return the task list
     */
    public ObservableList<Task> getTasks() {
        return tasks;
    }
}
