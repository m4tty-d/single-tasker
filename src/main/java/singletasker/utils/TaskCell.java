package singletasker.utils;

import singletasker.controllers.PomodoroController;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import singletasker.models.Task;
import singletasker.models.TaskStateKind;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

class TaskCell extends ListCell<Task> {

    private final HBox hBox = new HBox();
    private final TextField textField = new TextField();
    private final Button editBtn = new Button(">");
    private final Label taskStateSymbol = new Label("●");
    private final Label pomodoroSymbol = new Label("●");
    private final Label pomodoroCountLabel = new Label("0");
    private static Stage pomodoroStage;
    private static ContextMenu contextMenu = new ContextMenu();

    private Logger logger = LoggerFactory.getLogger(TaskCell.class);

    public TaskCell() {
        setBindings();

        setHbox();

        setContextMenu();

        handleEditBtnClick();

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
    }

    @Override
    public void updateItem(Task task, boolean empty) {
        super.updateItem(task, empty);

        if (empty || task == null) {
            setText(null);
            setGraphic(null);
        } else {
            getHBoxsTextField().setText(task.getName());

            setGraphic(hBox);
        }
    }

    private void setHbox() {
        hBox.getChildren().addAll(taskStateSymbol, textField, pomodoroSymbol, pomodoroCountLabel, editBtn);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(textField, Priority.ALWAYS);
        HBox.setMargin(pomodoroSymbol, new Insets(2, 5, 0, 0));
        HBox.setMargin(pomodoroCountLabel, new Insets(0, 10, 0, 0));
    }

    private void setContextMenu() {
        MenuItem deleteItem = new MenuItem();
        deleteItem.textProperty().bind(Bindings.format("Delete \"%s\"", this.itemProperty()));
        deleteItem.setOnAction(event -> System.out.println("adsf"));
        contextMenu.getItems().addAll(deleteItem);
    }

    private void handleTextFieldEvents() {
        textField.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            } else if (notSpecialKey(e)) {
                getItem().setName(getHBoxsTextField().getText());
            }
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

    private boolean notSpecialKey(KeyEvent e) {
        return  e.getCode() != KeyCode.RIGHT ||
                e.getCode() != KeyCode.LEFT ||
                e.getCode() != KeyCode.UP ||
                e.getCode() != KeyCode.DOWN ||
                e.isAltDown() ||
                e.isShiftDown() ||
                e.isControlDown();
    }
}