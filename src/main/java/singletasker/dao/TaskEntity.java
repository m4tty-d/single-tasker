package singletasker.dao;

import singletasker.models.Task;
import singletasker.models.TaskStateKind;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a task.
 */
@Entity
@Table(name="TASKS")
public class TaskEntity {
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
     * Count of the pomodoros. A pomodoro is basically a work session.
     */
    private int pomodoroCount;

    /**
     * Gets the state of the task. See more: {@link TaskStateKind}
     */
    @Enumerated
    private TaskStateKind currentState;

    /**
     * Default contructor.
     */
    public TaskEntity() {
        this.pomodoroCount = 0;
        this.currentState = TaskStateKind.FOCUS;
    }

    /**
     * Contructor which creates a task entity from a {@link Task} instance.
     * @param t {@link Task} instance to be set
     */
    public TaskEntity(Task t) {
        this.id = t.getId();
        this.name = t.getName();
        this.pomodoroCount = t.getPomodoroCount();
        this.currentState = t.getCurrentState().getKind();
    }

    /**
     * Gets the id of the entity.
     * @return the id of the entity
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id of the entity.
     * @param id id to be set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the entity.
     * @return name of the entity
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the entity.
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the pomodoro count.
     * @return pomodoro count
     */
    public int getPomodoroCount() {
        return pomodoroCount;
    }

    /**
     * Sets the pomodoro count of the entity.
     * @param pomodoroCount the pomodoro count to be set
     */
    public void setPomodoroCount(int pomodoroCount) {
        this.pomodoroCount = pomodoroCount;
    }

    /**
     * Gets the current state.
     * @return the {@link TaskStateKind} state kind
     */
    public TaskStateKind getCurrentState() {
        return currentState;
    }

    /**
     * Sets the current state.
     * @param currentState {@link TaskStateKind} state to be set
     */
    public void setCurrentState(TaskStateKind currentState) {
        this.currentState = currentState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
