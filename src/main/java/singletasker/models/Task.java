package singletasker.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import singletasker.utils.DifficultyLevelRangeException;
import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a task.
 */
@Entity
@Table(name="TASKS")
public class Task {
    /**
     * Id of the task.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Name of the task.
     */
    private String name;

    /**
     * Difficulty level of the task. (1-10)
     */
    @Transient
    private int difficultyLevel;

    /**
     * The count of the pomodoros. A pomodoro is basically a work session.
     */
    @Transient
    private IntegerProperty pomodoroCount;

    /**
     * The current state of the task.
     */
    @Embedded
    private TaskState currentState;

    /**
     * Contructor which sets the default values.
     */
    public Task() {
        this.currentState = new TaskState(TaskStateKind.FOCUS);
        this.difficultyLevel = 0;
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
     * Gets the task's id.
     * @return the id of the task
     */
    public Long getId() {
        return id;
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
        if (difficultyLevel < 1 || difficultyLevel > 10) {
            throw new DifficultyLevelRangeException("Difficulty must be between 1 and 10");
        } else {
            this.difficultyLevel = difficultyLevel;
        }
    }


    /**
     * Gets the task's pomodoro count.
     * A pomodoro is a work session.
     * @return the pomodoro count
     */
    @Access(AccessType.PROPERTY)
    public int getPomodoroCount() {
        return pomodoroCount.get();
    }

    public IntegerProperty pomodoroCountProperty() {
        return pomodoroCount;
    }

    public void setPomodoroCount(int pomodoroCount) {
        this.pomodoroCount.set(pomodoroCount);
    }

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
     * @param currentStateKind
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
     * @return the points
     */
    public int getRewardPoints() {
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
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
