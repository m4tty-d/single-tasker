package models;

public enum UserRankKind {
    ROOKIE("Rookie"),
    LEVEL_2("Level2"),
    LEVEL_3("Level3"),
    LEVEL_4("Level4"),
    PRODUCTIVITY_NINJA("Ninja");

    private String displayName;

    UserRankKind(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
