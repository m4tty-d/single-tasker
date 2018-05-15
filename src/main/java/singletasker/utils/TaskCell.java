package singletasker.utils;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import singletasker.controllers.PomodoroController;
import singletasker.controllers.TaskList;
import singletasker.models.Task;
import singletasker.models.TaskStateKind;

import java.io.IOException;

class TaskCell extends ListCell<Task> {

    private final HBox hBox = new HBox();
    private final TextField textField = new TextField();
    private final Button editBtn = new Button("▷");
    private final Button removeBtn = new Button("✕");
    private final Label taskStateSymbol = new Label("●");
    private final Label pomodoroSymbol = new Label("●");
    private final Label pomodoroCountLabel = new Label("0");
    private static Stage pomodoroStage;

    private TaskList taskList = TaskList.getInstance();

    private Logger logger = LoggerFactory.getLogger(TaskCell.class);

    public TaskCell() {
        setBindings();
        setHbox();
        handleEditBtnClick();
        handleRemoveBtnClick();
        handleTextFieldEvents();
    }

    @Override
    public void startEdit() {
        super.startEdit();
        getHBoxsTextField().setText(getItem().getName());
        getHBoxsTextField().requestFocus();
        getHBoxsTextField().selectAll();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        getHBoxsTextField().setText(getItem().getName());
        hBox.requestFocus();
    }

    @Override
    public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setText(null);
            setGraphic(null);
        } else {
            getHBoxsTextField().setText(task.getName());
            if (task.getCurrentState().getKind() == TaskStateKind.FINISHED) {
                taskStateSymbol.setText("✓");
                hBox.getStyleClass().add("finished");
            }

            setGraphic(hBox);
        }
    }

    private void setHbox() {
        editBtn.getStyleClass().add("editBtn");
        removeBtn.getStyleClass().add("removeBtn");
        hBox.getChildren().addAll(taskStateSymbol, textField, pomodoroSymbol, pomodoroCountLabel, editBtn, removeBtn);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(textField, Priority.ALWAYS);
        HBox.setMargin(pomodoroSymbol, new Insets(2, 5, 0, 0));
        HBox.setMargin(pomodoroCountLabel, new Insets(0, 10, 0, 0));
        HBox.setMargin(editBtn, new Insets(2, 0, 0, 0));
        HBox.setMargin(removeBtn, new Insets(2, 0, 0, 0));
    }

    private void handleTextFieldEvents() {
        textField.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });

        textField.setOnAction(event -> {
            getItem().setName(getHBoxsTextField().getText());
            taskList.updateTask(getItem());
            hBox.requestFocus();
        });
    }

    private void handleEditBtnClick() {
        editBtn.setOnAction(event -> {
            if (pomodoroStage != null) {
                pomodoroStage.hide();
            }
            pomodoroStage = buildPomodoro(getItem());
            pomodoroStage.show();
            logger.info("Pomodoro showed");
        });
    }

    private void handleRemoveBtnClick() {
        removeBtn.setOnAction(event -> {
            taskList.removeTask(getItem());
            logger.info("Task removed");
        });
    }

    private TextField getHBoxsTextField() {
        return ((TextField) hBox.getChildren().get(1));
    }

    private Stage buildPomodoro(Task selectedTask) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pomodoro.fxml"));

        Stage stage = new Stage();

        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            logger.error("Error while loading pomodoro.fxml, here's some further info: {}", e.getMessage());
        }
        stage.setResizable(false);
        logger.info("Pomodoro created");

        PomodoroController controller = loader.getController();
        controller.setTask(selectedTask);
        logger.info("Task sent to Pomodoro");

        return stage;
    }

    private void setBindings() {
        itemProperty().addListener((obs, oldItem, newItem) -> {
            if (oldItem == null && newItem != null) {
                pomodoroCountLabel.textProperty().bind(getItem().pomodoroCountProperty().asString());
                newItem.getCurrentState().kindProperty().addListener((o, oldVal, newVal) -> {
                    if (getItem() != null && getItem().equals(newItem) && newVal == TaskStateKind.FINISHED) {
                        taskStateSymbol.setText("✓");
                        hBox.getStyleClass().add("finished");
                    }
                });
            }
        });
    }
}