package utils;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.Task;

public class TaskCellFactory implements Callback<ListView<Task>,ListCell<Task>> {

    @Override
    public ListCell<Task> call(ListView<Task> listView) {
        return new TaskCell();
    }
}
