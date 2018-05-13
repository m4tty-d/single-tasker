package singletasker.models;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.persistence.*;

/**
 * Represents the user.
 */
@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Total points of the user.
     */
    @Transient
    private LongProperty totalPoints = new SimpleLongProperty(0);

    /**
     * Completed tasks.
     */
    @Transient
    private LongProperty completedTasks = new SimpleLongProperty(0);

    /**
     * The rank of the user.
     */
    @Transient
    private StringProperty userRank = new SimpleStringProperty(UserRankKind.ROOKIE.getDisplayName());

    @Transient
    private User instance;

    public User() {}

    public Long getId() {
        return id;
    }

    /**
     * Gets the total points property.
     * @return the total points property
     */
    public LongProperty totalPointsProperty() {
        return totalPoints;
    }

    private void setTotalPoints(long totalPoints) {
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
    @Access(AccessType.PROPERTY)
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

    private void setCompletedTasks(long completedTasks) {
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
    @Access(AccessType.PROPERTY)
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

    private void setUserRank(String userRank) {
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
    @Access(AccessType.PROPERTY)
    public String getUserRank() {
        return userRank.get();
    }
}
