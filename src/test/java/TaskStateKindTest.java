import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import singletasker.models.TaskStateKind;

public class TaskStateKindTest {

    private TaskStateKind taskStateKind;

    @Test
    public void testGetMotivationalText() {
        taskStateKind = TaskStateKind.FOCUS;
        Assertions.assertEquals(TaskStateKind.FOCUS.getMotivationalText(), taskStateKind.getMotivationalText());

        taskStateKind = TaskStateKind.SHORT_BREAK;
        Assertions.assertEquals(TaskStateKind.SHORT_BREAK.getMotivationalText(), taskStateKind.getMotivationalText());

        taskStateKind = TaskStateKind.LONG_BREAK;
        Assertions.assertEquals(TaskStateKind.LONG_BREAK.getMotivationalText(), taskStateKind.getMotivationalText());

        taskStateKind = TaskStateKind.FINISHED;
        Assertions.assertEquals(TaskStateKind.FINISHED.getMotivationalText(), taskStateKind.getMotivationalText());
    }

    @Test
    public void testSetDurationInMinutes() {
        TaskStateKind.FOCUS.setDurationInMinutes(30);
        Assertions.assertEquals(30, TaskStateKind.FOCUS.getDurationInMinutes());

        TaskStateKind.SHORT_BREAK.setDurationInMinutes(10);
        Assertions.assertEquals(10, TaskStateKind.SHORT_BREAK.getDurationInMinutes());
    }
}
