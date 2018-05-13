import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singletasker.models.User;
import singletasker.models.UserRankKind;

import java.lang.reflect.Field;

public class UserTest {

    private User user;

    @BeforeEach
    public void setup() {
        /*Field instance = User.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        user = User.getInstance();*/
        user = new User();
    }

    @Test
    public void testAddToTotalPoints() {
        user.addToTotalPoints(100);
        Assertions.assertEquals(100, user.getTotalPoints());

        user.addToTotalPoints(100);
        Assertions.assertEquals(200, user.getTotalPoints());
    }

    @Test
    public void testIncrementCompletedTasks() {
        user.incrementCompletedTasks();
        Assertions.assertEquals(1, user.getCompletedTasks());

        user.incrementCompletedTasks();
        Assertions.assertEquals(2, user.getCompletedTasks());
    }

    @Test
    public void testSetUserRankByPoint() {
        Assertions.assertEquals(UserRankKind.ROOKIE.getDisplayName(), user.getUserRank());

        user.addToTotalPoints(1001);
        Assertions.assertEquals(UserRankKind.LEVEL_2.getDisplayName(), user.getUserRank());

        user.addToTotalPoints(1000);
        Assertions.assertEquals(UserRankKind.LEVEL_3.getDisplayName(), user.getUserRank());

        user.addToTotalPoints(3000);
        Assertions.assertEquals(UserRankKind.LEVEL_4.getDisplayName(), user.getUserRank());

        user.addToTotalPoints(5000);
        Assertions.assertEquals(UserRankKind.PRODUCTIVITY_NINJA.getDisplayName(), user.getUserRank());
    }
}
