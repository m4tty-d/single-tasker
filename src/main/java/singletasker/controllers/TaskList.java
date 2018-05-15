package singletasker.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import singletasker.dao.TaskDAOImpl;
import singletasker.dao.TaskEntity;
import singletasker.models.Task;

import java.util.stream.Collectors;

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


    private TaskDAOImpl dao = TaskDAOImpl.getInstance();

    /**
     * Logger instance used for logging.
     */
    private Logger logger = LoggerFactory.getLogger(TaskList.class);

    /**
     *
     */
    private TaskList() {
        tasks = FXCollections.observableArrayList(dao.findAll().stream().map(te -> new Task(te)).collect(Collectors.toList()));
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
        tasks.add(t);
        dao.insert(new TaskEntity(t));
        logger.info("Task added to TaskList with name: " + t.getName());
    }

    /**
     * Removes a task from the list.
     * @param t the task to be removed
     */
    public void removeTask(Task t) {
        tasks.remove(t);
        dao.delete(t.getId());
        logger.info("Task removed from list named: " + t.getName());
    }

    public void updateTask(Task t) {
        if (tasks.contains(t)) {
            int index = tasks.indexOf(t);
            tasks.set(index, t);
            dao.update(new TaskEntity(t));
        }
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
