package singletasker.models;

/**
 * Represents the different task states.
 */
public enum TaskStateKind {
    /**
     * Represents the focus state, which is when the user is working.
     */
    FOCUS(25*60, "It's time to focus!"),

    /**
     * Represents a short break, which is the default break after focus state.
     */
    SHORT_BREAK(5*60, "Stand up, take a short break."),

    /**
     * Represents a long break, which is set after 4 work sessions.
     */
    LONG_BREAK(15*60, "Take a longer break, you deserve it! :)"),

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
     * @param durationInSeconds duration of the state
     * @param motivationalText motivational text for the state
     */
    TaskStateKind(int durationInSeconds, String motivationalText) {
        this.durationInSeconds = durationInSeconds;
        this.motivationalText = motivationalText;
    }

    /**
     * Gets the state's duration.
     * @return duration in seconds
     */
    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    /**
     * Gets the motivational text for the state.
     * @return motivational text
     */
    public String getMotivationalText() {
        return motivationalText;
    }
}
