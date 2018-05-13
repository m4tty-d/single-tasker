package singletasker.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import singletasker.models.Task;
import singletasker.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import singletasker.utils.TaskCellFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the home view.
 */
public class HomeController implements Initializable {

    @FXML
    private TextField newTaskField;

    @FXML
    private ListView<Task> taskListView;

    @FXML
    private Label pointsLabel;

    @FXML
    private Label completedTasksLabel;

    @FXML
    private Label rankLabel;

    @FXML
    private ImageView settingsIcon;

    private TaskListController taskListController = TaskListController.getInstance();

    private Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taskListView.setItems(taskListController.getTasks());
        logger.info("Setting up tasks");

        // pointsLabel.textProperty().bind(user.totalPointsProperty().asString().concat(" points"));
        // completedTasksLabel.textProperty().bind(user.completedTasksProperty().asString().concat(" completed tasks"));
        // rankLabel.textProperty().bind(user.userRankProperty());

        settingsIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Stage settings = createSettingsStage();
            settings.show();
            event.consume();
        });

        taskListView.setEditable(true);
        taskListView.setCellFactory(new TaskCellFactory());
    }

    private Stage createSettingsStage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/settings.fxml"));

        Stage stage = new Stage();

        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            logger.error("Error while loading settings.fxml, here's some further info: {}", e.getMessage());
        }
        stage.setResizable(false);
        logger.info("Settings created");

        return stage;
    }

    @FXML
    void handleEnterOnNewTaskField(ActionEvent event) {
        if (newTaskFieldNotEmpty()) {
            String text = newTaskField.getText();
            taskListController.addTask(new Task(text));
            logger.info("New task handled with name: " + text);
            emptyNewTaskField();
        }
    }

    private boolean newTaskFieldNotEmpty() {
        return !newTaskField.getText().equals("");
    }

    private void emptyNewTaskField() {
        newTaskField.setText("");
        logger.info("New task field emptied");
    }
}
