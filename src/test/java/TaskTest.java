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
        Assertions.assertEquals("test", task.getName());

        task.setName("");
        Assertions.assertEquals("", task.getName());
    }

    @Test
    public void testSetCurrentStateKind() {
        task.setCurrentStateKind(TaskStateKind.FOCUS);
        Assertions.assertEquals(TaskStateKind.FOCUS, task.getCurrentState().getKind());

        task.setCurrentStateKind(TaskStateKind.SHORT_BREAK);
        Assertions.assertEquals(TaskStateKind.SHORT_BREAK, task.getCurrentState().getKind());

        task.setCurrentStateKind(TaskStateKind.LONG_BREAK);
        Assertions.assertEquals(TaskStateKind.LONG_BREAK, task.getCurrentState().getKind());

        task.setCurrentStateKind(TaskStateKind.FINISHED);
        Assertions.assertEquals(TaskStateKind.FINISHED, task.getCurrentState().getKind());
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

        Assertions.assertEquals(1, task.getDifficultyLevel());
    }

    @Test
    public void testIncrementPomodoroCount() {
        task.incrementPomodoroCount();
        Assertions.assertEquals(1, task.getPomodoroCount());

        task.incrementPomodoroCount();
        Assertions.assertEquals(2, task.getPomodoroCount());
    }

    @Test
    public void testSetNextState() {
        task.setNextState();
        Assertions.assertEquals(TaskStateKind.SHORT_BREAK, task.getCurrentState().getKind());

        task.setNextState();
        Assertions.assertEquals(TaskStateKind.FOCUS, task.getCurrentState().getKind());

        task.incrementPomodoroCount();
        task.incrementPomodoroCount();
        task.setNextState();
        Assertions.assertEquals(TaskStateKind.LONG_BREAK, task.getCurrentState().getKind());

        task.setNextState();
        Assertions.assertEquals(TaskStateKind.FOCUS, task.getCurrentState().getKind());
    }

    @Test
    public void testGetPoints() {
        Assertions.assertEquals(0, task.getPoints());

        task.setCurrentStateKind(TaskStateKind.FINISHED);

        Assertions.assertEquals(0, task.getPoints());

        task.incrementPomodoroCount();

        try {
            task.setDifficultyLevel(1);
        } catch (DifficultyLevelRangeException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(10, task.getPoints());
    }
}