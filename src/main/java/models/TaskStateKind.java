package models;

/**
 * Represents the different task states.
 */
public enum TaskStateKind {
    /**
     * Represents the focus state, which is when the user is working.
     */
    FOCUS(6, "It's time to focus!"),

    /**
     * Represents a short break, which is the default break after focus state.
     */
    SHORT_BREAK(3, "Stand up, take a short break."),

    /**
     * Represents a long break, which is set after 4 work sessions.
     */
    LONG_BREAK(5, "Take a longer break, you deserve it! :)"),

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
    private String motivationText;

    /**
     * Constructor which sets the duration and motivational text.
     * @param durationInSeconds duration of the state
     * @param motivationText motivational text for the state
     */
    TaskStateKind(int durationInSeconds, String motivationText) {
        this.durationInSeconds = durationInSeconds;
        this.motivationText = motivationText;
    }

    /**
     * Gets the state duration.
     * @return duration in seconds
     */
    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    /**
     * Gets the motivational text for the state.
     * @return motivational text
     */
    public String getMotivationText() {
        return motivationText;
    }
}
