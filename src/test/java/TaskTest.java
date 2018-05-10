import models.Task;
import models.TaskState;
import models.TaskStateKind;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.DifficultyLevelRangeException;

class TaskTest {
    private Task task;

    @BeforeEach
    public void setup() {
        task = new Task();
    }

    @Test
    public void testGettersAndSetter() {
        task.setName("test");
        Assertions.assertEquals(task.getName(), "test");

        task.setCurrentState(new TaskState(TaskStateKind.FOCUS));
        Assertions.assertEquals(task.getCurrentState().getKind(), TaskStateKind.FOCUS);

        try {
            task.setDifficultyLevel(1);
        } catch (DifficultyLevelRangeException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(task.getDifficultyLevel(), 1);

        task.incrementPomodoroCount();
        Assertions.assertEquals(task.getPomodoroCount(), 1);

        task.incrementPomodoroCount();
        Assertions.assertEquals(task.getPomodoroCount(), 2);
    }

    @Test
    public void testSetNextState() {
        task.setNextState();
        Assertions.assertEquals(task.getCurrentState().getKind(), TaskStateKind.SHORT_BREAK);

        task.setNextState();
        Assertions.assertEquals(task.getCurrentState().getKind(), TaskStateKind.FOCUS);

        task.incrementPomodoroCount();
        task.incrementPomodoroCount();
        task.setNextState();
        Assertions.assertEquals(task.getCurrentState().getKind(), TaskStateKind.LONG_BREAK);

        task.setNextState();
        Assertions.assertEquals(task.getCurrentState().getKind(), TaskStateKind.FOCUS);
    }
}