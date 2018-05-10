package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DifficultyLevelRangeException;

/**
 * Represents a task.
 */
public class Task {

    /**
     * Name of the task.
     */
    private String name;

    /**
     * Difficulty level of the task. (1-10)
     */
    private int difficultyLevel;

    /**
     * The count of the pomodoros. A pomodoro is basically a work session.
     */
    private IntegerProperty pomodoroCount;

    /**
     * The current state of the task.
     */
    private TaskState currentState;

    /**
     * Logger instance used for logging.
     */
    private Logger logger = LoggerFactory.getLogger(Task.class);

    /**
     * Contructor which sets the default values.
     */
    public Task() {
        this.currentState = new TaskState(TaskStateKind.FOCUS);
        this.difficultyLevel = 0;
        this.pomodoroCount = new SimpleIntegerProperty(0);
    }

    /**
     * Default contructor.
     * @param name the name of the task
     */
    public Task(String name) {
        this();
        this.name = name;
        logger.info("Task object created");
    }

    /**
     * Gets the name of the task.
     * @return the name of the task
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     * @param name name of the task
     */
    public void setName(String name) {
        this.name = name;
        logger.info("Task's name set with: " + name);
    }

    /**
     * Gets the task's difficulty level.
     * The difficulty is an integer ranged between 0 and 10, it is used to calculate the point which the user gets.
     * From it we can measure how hard was the task for the user.
     * @return the difficulty level of the task
     */
    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    /**
     * Sets the task's difficulty level.
     * The difficulty is an integer ranged between 0 and 10, it is used to calculate the point which the user gets.
     * From it we can measure how hard was the task for the user.
     * @param difficultyLevel integer between 0 and 10, used to calculate the point
     * @throws DifficultyLevelRangeException if difficultyLevel is not in the correct range
     */
    public void setDifficultyLevel(int difficultyLevel) throws DifficultyLevelRangeException {
        if (difficultyLevel < 0 || difficultyLevel > 10) {
            throw new DifficultyLevelRangeException("Difficulty must be between 0 and 10!");
        } else {
            this.difficultyLevel = difficultyLevel;
        }
        logger.info("Task's difficulty level set: " + difficultyLevel);
    }

    /**
     * Gets the task's pomodoro count.
     * A pomodoro is a work session.
     * @return the pomodoro count
     */
    public int getPomodoroCount() {
        return pomodoroCount.get();
    }

    /**
     * Gets the PomodoroCount's reactive property.
     * @return the pomodoroCount property
     */
    public IntegerProperty pomodoroCountProperty() {
        return pomodoroCount;
    }


    private void setPomodoroCount(int pomodoroCount) {
        this.pomodoroCount.set(pomodoroCount);
        logger.info("Task's pomodoro count set to: " + pomodoroCount);
    }

    /**
     * Incremements the pomodoro count by 1.
     */
    public void incrementPomodoroCount() {
        this.pomodoroCount.set(this.pomodoroCount.get() + 1);
    }

    /**
     * Gets the current state of the task.
     * @return the current state
     */
    public TaskState getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state of the task.
     * @param currentState the current state
     */
    public void setCurrentState(TaskState currentState) {
        this.currentState = currentState;
        logger.info("Task's current state set");
    }

    /**
     * Sets the next state of the task according to the rules of pomodoro technique, which states that
     * if the pomodoro count exceeds 4 the user gets a long break, otherwise a short break.
     */
    public void setNextState() {
        if (this.getCurrentState().getKind() == TaskStateKind.FOCUS) {
            this.incrementPomodoroCount();

            if (this.getPomodoroCount() % 4 == 0) {
                this.setCurrentState(new TaskState(TaskStateKind.LONG_BREAK));
            } else {
                this.setCurrentState(new TaskState(TaskStateKind.SHORT_BREAK));
            }
        } else {
            this.setCurrentState(new TaskState((TaskStateKind.FOCUS)));
        }
    }

    /**
     * Gets the task's string representation.
     * @return the string representation of the task
     */
    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", pomodoroCount=" + pomodoroCount +
                ", currentState=" + currentState +
                '}';
    }
}
