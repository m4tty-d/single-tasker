import clover.org.apache.commons.lang3.builder.Diff;
import singletasker.models.Task;
import singletasker.models.TaskState;
import singletasker.models.TaskStateKind;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singletasker.utils.DifficultyLevelRangeException;

class TaskTest {
    private Task task;

    @BeforeEach
    public void setup() {
        task = new Task();
    }

    @Test
    public void testSetName() {
        task.setName("test");
        Assertions.assertEquals(task.getName(), "test");

        task.setName("");
        Assertions.assertEquals(task.getName(), "");
    }

    @Test
    public void testSetCurrentStateKind() {
        task.setCurrentStateKind(TaskStateKind.FOCUS);
        Assertions.assertEquals(task.getCurrentState().getKind(), TaskStateKind.FOCUS);

        task.setCurrentStateKind(TaskStateKind.SHORT_BREAK);
        Assertions.assertEquals(task.getCurrentState().getKind(), TaskStateKind.SHORT_BREAK);

        task.setCurrentStateKind(TaskStateKind.LONG_BREAK);
        Assertions.assertEquals(task.getCurrentState().getKind(), TaskStateKind.LONG_BREAK);

        task.setCurrentStateKind(TaskStateKind.FINISHED);
        Assertions.assertEquals(task.getCurrentState().getKind(), TaskStateKind.FINISHED);
    }

    @Test
    public void testSetDifficultyLevel() {
        Assertions.assertDoesNotThrow(() -> task.setDifficultyLevel(1));
        Assertions.assertDoesNotThrow(() -> task.setDifficultyLevel(10));
        Assertions.assertThrows(DifficultyLevelRangeException.class, () -> task.setDifficultyLevel(11), "Difficulty must be between 0 and 10");
        Assertions.assertThrows(DifficultyLevelRangeException.class, () -> task.setDifficultyLevel(0), "Difficulty must be between 0 and 10");

        try {
            task.setDifficultyLevel(1);
        } catch (DifficultyLevelRangeException e) {}

        Assertions.assertEquals(task.getDifficultyLevel(), 1);
    }

    @Test
    public void testIncrementPomodoroCount() {
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

    @Test
    public void testGetPoints() {
        Assertions.assertEquals(task.getPoints(), 0);

        task.setCurrentStateKind(TaskStateKind.FINISHED);

        Assertions.assertEquals(task.getPoints(), 0);

        task.incrementPomodoroCount();

        try {
            task.setDifficultyLevel(1);
        } catch (DifficultyLevelRangeException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(task.getPoints(), 10);
    }
}