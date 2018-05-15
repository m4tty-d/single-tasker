package singletasker.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import singletasker.dao.TaskEntity;
import singletasker.utils.DifficultyLevelRangeException;
import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a task.
 */
public class Task {
    /**
     * Id of the task.
     */
    private Long id;

    /**
     * Name of the task.
     */
    private String name;

    /**
     * The count of the pomodoros. A pomodoro is basically a work session.
     */
    private IntegerProperty pomodoroCount;

    /**
     * The current state of the task.
     */
    private TaskState currentState;

    /**
     * Contructor which sets the default values.
     */
    public Task() {
        this.currentState = new TaskState(TaskStateKind.FOCUS);
        this.pomodoroCount = new SimpleIntegerProperty(0);
    }

    /**
     * Contructor which sets the name, and the default values.
     * @param name the name of the task
     */
    public Task(String name) {
        this();
        this.name = name;
    }

    /**
     * Contrustor which sets the properties from a {@link TaskEntity} instance.
     * @param te {@link TaskEntity} instance
     */
    public Task(TaskEntity te) {
        this.id = te.getId();
        this.name = te.getName();
        this.pomodoroCount = new SimpleIntegerProperty(te.getPomodoroCount());
        this.currentState = new TaskState(te.getCurrentState());
    }

    /**
     * Gets the id of the task.
     * @return id of the task
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id of the task.
     * @param id the id to be set
     */
    public void setId(Long id) {
        this.id = id;
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
     * Gets the reactive property instance.
     * @return the reactive property
     */
    public IntegerProperty pomodoroCountProperty() {
        return pomodoroCount;
    }

    /**
     * Sets the pomodoro count.
     * @param pomodoroCount number of pomodoros
     */
    public void setPomodoroCount(int pomodoroCount) {
        this.pomodoroCount.set(pomodoroCount);
    }

    /**
     * Increments the pomodoro count by 1.
     */
    public void incrementPomodoroCount() {
        setPomodoroCount(getPomodoroCount() + 1);
    }

    /**
     * Gets the current state of the task.
     * @return the current state
     */
    public TaskState getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state's kind.
     * @param currentStateKind the state to be set
     */
    public void setCurrentStateKind(TaskStateKind currentStateKind) {
        currentState.setKind(currentStateKind);
    }

    /**
     * Sets the next state of the task according to the rules of pomodoro technique, which states that
     * if the pomodoro count exceeds 4 the user gets a long break, otherwise a short break.
     */
    public void setNextState() {
        if (currentState.getKind() == TaskStateKind.FOCUS) {
            incrementPomodoroCount();

            if (getPomodoroCount() % 4 == 0) {
                currentState.setKind(TaskStateKind.LONG_BREAK);
            } else {
                currentState.setKind(TaskStateKind.SHORT_BREAK);
            }
        } else {
            currentState.setKind(TaskStateKind.FOCUS);
        }
    }

    /**
     * Gets the point after the completion of the task.
     * The point is calculated from the difficulty level and the count of the pomodoros, with the following formula:
     * difficultyLevel / pomodoroCount * pointByDifficulty
     * where pointByDifficulty is difficultyLevel*10
     * @param difficultyLevel how hard was the task to complete (1-10)
     * @return the points
     */
    public int getRewardPoints(int difficultyLevel) {
        if (currentState.getKind() == TaskStateKind.FINISHED && getPomodoroCount() != 0) {
            int pointByDifficulty = difficultyLevel*10;
            return Math.round(difficultyLevel/getPomodoroCount()*pointByDifficulty);
        }

        return 0;
    }

    /**
     * Gets the task's string representation.
     * @return the string representation of the task
     */
    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", pomodoroCount=" + pomodoroCount +
                ", currentState=" + currentState +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
