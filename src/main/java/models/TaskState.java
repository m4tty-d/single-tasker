package models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskState {
    private TaskStateKind kind;
    private int remainingSeconds;

    private Logger logger = LoggerFactory.getLogger(TaskState.class);

    public TaskState(TaskStateKind kind) {
        this.kind = kind;
        this.remainingSeconds = kind.getDurationInSeconds();
        logger.info("TaskState object created");
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
        logger.info("TaskState kindness set to: " + kind.name());
    }

    public TaskStateKind getKind() {
        return kind;
    }
}
