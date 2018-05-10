package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Objects;

public class Task implements Serializable {

    private String name;
    private int difficultyLevel;
    private IntegerProperty pomodoroCount;
    private TaskState currentState;

    private Logger logger = LoggerFactory.getLogger(Task.class);

    public Task(String name) {
        this.name = name;
        this.currentState = new TaskState(TaskStateKind.FOCUS);
        this.difficultyLevel = 0;
        this.pomodoroCount = new SimpleIntegerProperty(0);
        logger.info("Task object created");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        logger.info("Task's name set with: " + name);
    }

    public int getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(int difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        logger.info("Task's difficulty level set: " + difficultyLevel);
    }

    public int getPomodoroCount() {
        return pomodoroCount.get();
    }

    public IntegerProperty pomodoroCountProperty() {
        return pomodoroCount;
    }

    public void setPomodoroCount(int pomodoroCount) {
        this.pomodoroCount.set(pomodoroCount);
        logger.info("Task's pomodoro count set to: " + pomodoroCount);
    }

    public void incrementPomodoroCount() {
        this.pomodoroCount.set(this.pomodoroCount.get() + 1);
    }

    public TaskState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(TaskState currentState) {
        this.currentState = currentState;
        logger.info("Task's current state set: ", currentState);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", pomodoroCount=" + pomodoroCount +
                ", currentState=" + currentState +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return difficultyLevel == task.difficultyLevel &&
                pomodoroCount == task.pomodoroCount &&
                Objects.equals(name, task.name) &&
                Objects.equals(currentState, task.currentState);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, difficultyLevel, pomodoroCount, currentState);
    }
}
