import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import singletasker.models.Task;
import singletasker.models.TaskList;

import java.lang.reflect.Field;

public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        Field instance = TaskList.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        taskList = TaskList.getInstance();
    }

    @Test
    public void testAddTask() {
        taskList.addTask(new Task("test"));
        Assertions.assertEquals(1, taskList.size());

        taskList.addTask(new Task("another"));
        Assertions.assertEquals(2, taskList.size());
    }

    @Test
    public void testRemoveTask() {
        Task t = new Task("first");
        taskList.addTask(t);
        taskList.removeTask(t);
        Assertions.assertEquals(0, taskList.size());
    }

    @Test
    public void testGetTasks() {
        ObservableList<Task> taskList1 = FXCollections.observableArrayList();

        Task first = new Task("first");
        Task second = new Task("second");

        taskList1.add(first);
        taskList1.add(second);

        taskList.addTask(first);
        taskList.addTask(second);

        Assertions.assertEquals(taskList.getTasks().size(), taskList1.size());
    }
}
