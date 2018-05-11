package singletasker.models;

/**
 * Represents the different user ranks.
 */
public enum UserRankKind {
    /**
     * Represents the default user rank. (0-1000 points)
     */
    ROOKIE("Rookie"),
    /**
     * Represents the second level. (1000-2000 points)
     */
    LEVEL_2("Level2"),
    /**
     * Represents the third level. (2000-5000 points)
     */
    LEVEL_3("Level3"),
    /**
     * Represents the fourth level. (5000-10000 points)
     */
    LEVEL_4("Level4"),
    /**
     * Represents the highest level. (10000- points)
     */
    PRODUCTIVITY_NINJA("Ninja");

    private String displayName;

    /**
     * Constructor of the user rank kinds.
     * @param displayName
     */
    UserRankKind(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the user rank display name.
     * @return the display name of the user rank
     */
    public String getDisplayName() {
        return this.displayName;
    }
}
