import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singletasker.models.User;
import singletasker.models.UserRankKind;

import java.lang.reflect.Field;

public class UserTest {

    private User user;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        Field instance = User.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        user = User.getInstance();
    }

    @Test
    public void testAddToTotalPoints() {
        user.addToTotalPoints(100);
        Assertions.assertEquals(user.getTotalPoints(), 100);

        user.addToTotalPoints(100);
        Assertions.assertEquals(user.getTotalPoints(), 200);
    }

    @Test
    public void testIncrementCompletedTasks() {
        user.incrementCompletedTasks();
        Assertions.assertEquals(user.getCompletedTasks(), 1);

        user.incrementCompletedTasks();
        Assertions.assertEquals(user.getCompletedTasks(), 2);
    }

    @Test
    public void testSetUserRankByPoint() {
        Assertions.assertEquals(user.getUserRank(), UserRankKind.ROOKIE.getDisplayName());

        user.addToTotalPoints(1001);
        Assertions.assertEquals(user.getUserRank(), UserRankKind.LEVEL_2.getDisplayName());

        user.addToTotalPoints(1000);
        Assertions.assertEquals(user.getUserRank(), UserRankKind.LEVEL_3.getDisplayName());

        user.addToTotalPoints(3000);
        Assertions.assertEquals(user.getUserRank(), UserRankKind.LEVEL_4.getDisplayName());

        user.addToTotalPoints(5000);
        Assertions.assertEquals(user.getUserRank(), UserRankKind.PRODUCTIVITY_NINJA.getDisplayName());
    }
}
