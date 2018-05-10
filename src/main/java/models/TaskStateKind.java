package models;

public enum TaskStateKind {
    FOCUS(6),
    SHORT_BREAK(3),
    LONG_BREAK(5),
    FINISHED(0);

    private int durationInSeconds;

    TaskStateKind(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

}
