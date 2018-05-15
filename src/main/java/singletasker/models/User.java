package singletasker.models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents the user.
 */
public class User {
    /**
     * Total points of the user.
     */
    private LongProperty totalPoints = new SimpleLongProperty();

    /**
     * Completed tasks.
     */
    private LongProperty completedTasks = new SimpleLongProperty();

    /**
     * The rank of the user.
     */
    private StringProperty userRank = new SimpleStringProperty(UserRankKind.ROOKIE.getDisplayName());

    private static User instance;

    private User() {}

    /**
     * Creates the User instance or returns it if it's already created.
     * @return the {@link User} instance
     */
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }

        return instance;
    }

    /**
     * Gets the total points property.
     * @return the total points property
     */
    public LongProperty totalPointsProperty() {
        return totalPoints;
    }

    /**
     * Sets the total points property.
     * @param totalPoints the total points
     */
    public void setTotalPoints(long totalPoints) {
        this.totalPoints.set(totalPoints);
    }

    /**
     * Adds to the total points.
     * @param points the points to add
     */
    public void addToTotalPoints(int points) {
        long totalPoints = getTotalPoints();
        setTotalPoints(totalPoints + points);
        setUserRankByPoint();
    }

    /**
     * Gets the total points.
     * @return the total points
     */
    public long getTotalPoints() {
        return totalPoints.get();
    }

    /**
     * Gets the completed tasks property.
     * @return the completed tasks property
     */
    public LongProperty completedTasksProperty() {
        return completedTasks;
    }

    /**
     * Sets the number of completed tasks.
     * @param completedTasks number of completed tasks
     */
    public void setCompletedTasks(long completedTasks) {
        this.completedTasks.set(completedTasks);
    }

    /**
     * Increments the completed tasks by 1.
     */
    public void incrementCompletedTasks() {
        setCompletedTasks(getCompletedTasks() + 1);
    }

    /**
     * Gets the completed tasks.
     * @return the completed tasks
     */
    public long getCompletedTasks() {
        return completedTasks.get();
    }

    /**
     * Gets the user rank property.
     * @return the user rank property
     */
    public StringProperty userRankProperty() {
        return userRank;
    }

    /**
     * Sets the user's rank.
     * @param userRank the rank of the user
     */
    public void setUserRank(String userRank) {
        this.userRank.set(userRank);
    }

    /**
     * Sets the user's rank.
     */
    public void setUserRankByPoint() {
        long totalPoints = getTotalPoints();

        if (totalPoints >= 0 && totalPoints <= 1000) {
            setUserRank(UserRankKind.ROOKIE.getDisplayName());
        } else if (totalPoints > 1000 && totalPoints <= 2000) {
            setUserRank(UserRankKind.LEVEL_2.getDisplayName());
        } else if (totalPoints > 2000 && totalPoints <= 5000) {
            setUserRank(UserRankKind.LEVEL_3.getDisplayName());
        } else if (totalPoints > 5000 && totalPoints <= 10000) {
            setUserRank(UserRankKind.LEVEL_4.getDisplayName());
        } else {
            setUserRank(UserRankKind.PRODUCTIVITY_NINJA.getDisplayName());
        }
    }

    /**
     * Gets the user's rank.
     * @return rank of the user
     */
    public String getUserRank() {
        return userRank.get();
    }
}
