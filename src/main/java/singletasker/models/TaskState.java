package singletasker.models;

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
     * Default contructor, which sets the default state.
     */
    public TaskState() {
        setKind(TaskStateKind.FOCUS);
    }

    /**
     * Contructor which sets the kind of the task state.
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
     * Sets the remaining seconds.
     * @param remainingSeconds the remaining seconds to set
     */
    public void setRemainingSeconds(int remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
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
        setRemainingSeconds(getKind().getDurationInSeconds());
    }

    /**
     * Gets the type of the task's state.
     * @return the state of the task
     */
    public TaskStateKind getKind() {
        return kind.get();
    }

    /**
     * Gets the reactive task state property.
     * @return the state property
     */
    public ObjectProperty<TaskStateKind> kindProperty() {
        return kind;
    }
}
