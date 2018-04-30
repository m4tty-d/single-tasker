package models;

public class TaskState {
    private TaskStateKind kind;
    private int remainingSeconds;

    public TaskState(TaskStateKind kind) {
        this.kind = kind;
        this.remainingSeconds = kind.getDurationInSeconds();
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public void tickRemainingSeconds() {
        remainingSeconds--;
    }

    public void setKind(TaskStateKind kind) {
        this.kind = kind;
        this.remainingSeconds = kind.getDurationInSeconds();
    }

    public TaskStateKind getKind() {
        return kind;
    }
}
