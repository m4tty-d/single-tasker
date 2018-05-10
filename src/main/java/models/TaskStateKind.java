package models;

/**
 * Represents the different task states.
 */
public enum TaskStateKind {
    /**
     * Represents the focus state, which is when the user is working.
     */
    FOCUS(6),

    /**
     * Represents a short break, which is the default break after focus state.
     */
    SHORT_BREAK(3),

    /**
     * Represents a long break, which is set after 4 work sessions.
     */
    LONG_BREAK(5),

    /**
     * Represents the finished state.
     */
    FINISHED(0);

    /**
     * The duration of the states in seconds.
     */
    private int durationInSeconds;

    /**
     * Constructor, which sets the duration.
     * @param durationInSeconds
     */
    TaskStateKind(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    /**
     * Gets the state duration.
     * @return duration in seconds
     */
    public int getDurationInSeconds() {
        return durationInSeconds;
    }

}
