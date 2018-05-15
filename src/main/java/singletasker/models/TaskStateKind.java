package singletasker.models;

/**
 * Represents the different task states.
 */
public enum TaskStateKind {
    /**
     * Represents the focus state, which is when the user is working.
     */
    FOCUS(25, "It's time to focus!"),

    /**
     * Represents a short break, which is the default break after focus state.
     */
    SHORT_BREAK(5, "Stand up, take a short break."),

    /**
     * Represents a long break, which is set after 4 work sessions.
     */
    LONG_BREAK(15, "Take a longer break, you deserve it! :)"),

    /**
     * Represents the finished state.
     */
    FINISHED(0, "");

    /**
     * The duration of the states in seconds.
     */
    private int durationInSeconds;

    /**
     * Motivational text for the state.
     */
    private String motivationalText;

    /**
     * Constructor which sets the duration and motivational text.
     * @param durationInMinutes duration of the state in minutes (will be converted to seconds)
     * @param motivationalText motivational text for the state
     */
    TaskStateKind(int durationInMinutes, String motivationalText) {
        this.durationInSeconds = durationInMinutes*60;
        this.motivationalText = motivationalText;
    }

    /**
     * Gets the state's duration in seconds.
     * @return duration in seconds
     */
    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    /**
     * Gets the task state's duration in minutes.
     * @return the duration in minutes
     */
    public int getDurationInMinutes() {
        return durationInSeconds / 60;
    }

    /**
     * Gets the motivational text for the state.
     * @return motivational text
     */
    public String getMotivationalText() {
        return motivationalText;
    }

    /**
     * Sets the duration in minutes.
     * @param durationInMinutes the duration in minutes
     */
    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInSeconds = durationInMinutes*60;
    }
}
