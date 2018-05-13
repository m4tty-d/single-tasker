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
        Assertions.assertEquals(TaskStateKind.FOCUS.getDurationInSeconds(), taskState.getRemainingSeconds());

        taskState.setKind(TaskStateKind.SHORT_BREAK);
        Assertions.assertEquals(TaskStateKind.SHORT_BREAK.getDurationInSeconds(), taskState.getRemainingSeconds());

        taskState.setKind(TaskStateKind.LONG_BREAK);
        Assertions.assertEquals(TaskStateKind.LONG_BREAK.getDurationInSeconds(), taskState.getRemainingSeconds());

        taskState.setKind(TaskStateKind.FINISHED);
        Assertions.assertEquals(TaskStateKind.FINISHED.getDurationInSeconds(), taskState.getRemainingSeconds());
    }

    @Test
    public void testTickRemainingSeconds() {
        taskState.tickRemainingSeconds();
        Assertions.assertEquals(TaskStateKind.FOCUS.getDurationInSeconds() - 1, taskState.getRemainingSeconds());

        taskState.tickRemainingSeconds();
        Assertions.assertEquals(TaskStateKind.FOCUS.getDurationInSeconds() - 2, taskState.getRemainingSeconds());
    }

    @Test
    public void testSetKind() {
        taskState.setKind(TaskStateKind.FINISHED);
        Assertions.assertEquals(TaskStateKind.FINISHED, taskState.getKind());
    }
}
