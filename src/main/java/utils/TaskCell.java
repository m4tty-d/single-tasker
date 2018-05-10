package utils;

import controllers.PomodoroController;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import models.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

class TaskCell extends ListCell<Task> {

    private final HBox hBox = new HBox();
    private final TextField textField = new TextField();
    private final Button editBtn = new Button(">");
    private final Label circle1 = new Label("●");
    private final Label circle2 = new Label("●");
    private final Label pomodoroCountLabel = new Label("0");
    private static Stage pomodoroStage;
    private static DataFormat taskDataFormat = new DataFormat("task");
    private static ContextMenu contextMenu = new ContextMenu();

    private Logger logger = LoggerFactory.getLogger(TaskCell.class);

    public TaskCell() {
        setPomodoroCountLabelBinding();

        setHbox();

        setContextMenu();

        handleEditBtnClick();

        handleTextFieldEvents();

        handleDragAndDrop(this);
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
        hBox.getChildren().addAll(circle1, textField, circle2, pomodoroCountLabel, editBtn);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(textField, Priority.ALWAYS);
        HBox.setMargin(circle2, new Insets(2, 5, 0, 0));
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

    private void handleDragAndDrop(ListCell<Task> actualCell) {
        setOnDragDetected(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard dragboard = startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();

            content.put(taskDataFormat, getItem());

            dragboard.setContent(content);

            event.consume();
        });

        setOnDragOver(event -> {
            if (event.getGestureSource() != actualCell &&
                    event.getDragboard().hasContent(taskDataFormat)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }

            event.consume();
        });

        setOnDragEntered(event -> {
            if (event.getGestureSource() != actualCell &&
                    event.getDragboard().hasContent(taskDataFormat)) {
                setOpacity(0.3);
            }
        });

        setOnDragExited(event -> {
            if (event.getGestureSource() != actualCell &&
                    event.getDragboard().hasContent(taskDataFormat)) {
                setOpacity(1);
            }
        });

        setOnDragDropped(event -> {
            if (getItem() == null) {
                return;
            }

            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasContent(taskDataFormat)) {
                ObservableList<Task> items = getListView().getItems();

                Task draggedTask = (Task) db.getContent(taskDataFormat);

                int draggedIdx = items.indexOf(draggedTask);
                int thisIdx = items.indexOf(getItem());

                items.set(draggedIdx, getItem());
                items.set(thisIdx, draggedTask);

                getListView().getItems().setAll(new ArrayList<>(getListView().getItems()));

                success = true;
            }
            event.setDropCompleted(success);

            event.consume();
        });

        setOnDragDone(DragEvent::consume);
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

    private void setPomodoroCountLabelBinding() {
        itemProperty().addListener((obs, oldItem, newItem) -> {
            if (oldItem == null && newItem != null) {
                pomodoroCountLabel.textProperty().bind(getItem().pomodoroCountProperty().asString());
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