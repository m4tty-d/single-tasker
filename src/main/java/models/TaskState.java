package models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a task state.
 */
public class TaskState {
    /**
     * The type of the state.
     */
    private ObjectProperty<TaskStateKind> kind = new SimpleObjectProperty<>();

    /**
     * The remaining seconds.
     */
    private int remainingSeconds;

    /**
     * Logger instance for logging.
     */
    private Logger logger = LoggerFactory.getLogger(TaskState.class);

    /**
     * Contructor which sets the default values.
     * @param kind the type of the state
     */
    public TaskState(TaskStateKind kind) {
        setKind(kind);
        logger.info("TaskState object created");
    }

    /**
     * Gets the remaining seconds.
     * @return the remaining seconds
     */
    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    /**
     * Decrements the remaining seconds by 1.
     */
    public void tickRemainingSeconds() {
        remainingSeconds--;
    }

    /**
     * Sets the type of the task's state.
     * @param kind the type of the task's state
     */
    public void setKind(TaskStateKind kind) {
        this.kind.set(kind);
        this.remainingSeconds = getKind().getDurationInSeconds();
    }

    /**
     * Gets the type of the task's state.
     * @return the state of the task
     */
    public TaskStateKind getKind() {
        return kind.get();
    }

    public ObjectProperty<TaskStateKind> kindProperty() {
        return kind;
    }
}
