import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singletasker.models.TaskState;
import singletasker.models.TaskStateKind;

public class TaskStateTest {

    private TaskState taskState;

    @BeforeEach
    public void setup() {
        taskState = new TaskState();
    }

    @Test
    public void testGetRemainingSeconds() {
        Assertions.assertEquals(taskState.getRemainingSeconds(), TaskStateKind.FOCUS.getDurationInSeconds());

        taskState.setKind(TaskStateKind.SHORT_BREAK);
        Assertions.assertEquals(taskState.getRemainingSeconds(), TaskStateKind.SHORT_BREAK.getDurationInSeconds());

        taskState.setKind(TaskStateKind.LONG_BREAK);
        Assertions.assertEquals(taskState.getRemainingSeconds(), TaskStateKind.LONG_BREAK.getDurationInSeconds());

        taskState.setKind(TaskStateKind.FINISHED);
        Assertions.assertEquals(taskState.getRemainingSeconds(), TaskStateKind.FINISHED.getDurationInSeconds());
    }

    @Test
    public void testTickRemainingSeconds() {
        taskState.tickRemainingSeconds();
        Assertions.assertEquals(taskState.getRemainingSeconds(), TaskStateKind.FOCUS.getDurationInSeconds() - 1);

        taskState.tickRemainingSeconds();
        Assertions.assertEquals(taskState.getRemainingSeconds(), TaskStateKind.FOCUS.getDurationInSeconds() - 2);
    }

    @Test
    public void testSetKind() {
        taskState.setKind(TaskStateKind.FINISHED);
        Assertions.assertEquals(taskState.getKind(), TaskStateKind.FINISHED);
    }
}
