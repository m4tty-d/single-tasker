package singletasker.dao;

import singletasker.models.Task;
import singletasker.models.TaskStateKind;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="TASKS")
public class TaskEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int pomodoroCount;

    @Enumerated
    private TaskStateKind currentState;

    public TaskEntity() {
        this.pomodoroCount = 0;
        this.currentState = TaskStateKind.FOCUS;
    }

    public TaskEntity(Task t) {
        this.id = t.getId();
        this.name = t.getName();
        this.pomodoroCount = t.getPomodoroCount();
        this.currentState = t.getCurrentState().getKind();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPomodoroCount() {
        return pomodoroCount;
    }

    public void setPomodoroCount(int pomodoroCount) {
        this.pomodoroCount = pomodoroCount;
    }

    public TaskStateKind getCurrentState() {
        return currentState;
    }

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
