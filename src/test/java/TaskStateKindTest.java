import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import singletasker.models.TaskStateKind;

public class TaskStateKindTest {

    private TaskStateKind taskStateKind;

    @Test
    public void testGetMotivationalText() {
        taskStateKind = TaskStateKind.FOCUS;
        Assertions.assertEquals(taskStateKind.getMotivationalText(), TaskStateKind.FOCUS.getMotivationalText());

        taskStateKind = TaskStateKind.SHORT_BREAK;
        Assertions.assertEquals(taskStateKind.getMotivationalText(), TaskStateKind.SHORT_BREAK.getMotivationalText());

        taskStateKind = TaskStateKind.LONG_BREAK;
        Assertions.assertEquals(taskStateKind.getMotivationalText(), TaskStateKind.LONG_BREAK.getMotivationalText());

        taskStateKind = TaskStateKind.FINISHED;
        Assertions.assertEquals(taskStateKind.getMotivationalText(), TaskStateKind.FINISHED.getMotivationalText());
    }
}
