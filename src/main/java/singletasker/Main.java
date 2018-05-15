package singletasker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import singletasker.dao.DatabaseManager;

import java.io.IOException;

/**
 * The main class starts the application.
 */
public class Main extends Application {

    private static DatabaseManager db = DatabaseManager.getInstance();
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private final Service<Void> connectToDBService = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                public Void call() {
                    try {
                        db.connect();
                    } catch (Exception e) {
                        logger.error("Cannot connect to database, " + e.getMessage());
                    }

                    return null;
                }
            };
        }
    };

    public static void main(String[] args) {
        logger.info("Application started");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        connectToDBService.restart();
        setStageToSplashScene(primaryStage);

        connectToDBService.setOnRunning(event -> {
            primaryStage.show();
            logger.info("Splash showed");
        });

        connectToDBService.setOnSucceeded(event -> {
            connectToDBService.cancel();
            primaryStage.hide();
            logger.info("Splash hidden");
            showHomeScreenStage();
        });
    }

    @Override
    public void stop() {
        db.disconnect();
        Platform.exit();
        System.exit(0);
    }

    private void setStageToSplashScene(Stage stage) {
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("/views/splash.fxml"));
        } catch (IOException e) {
            logger.error("Error while loading splash.fxml, here's some further info: {}", e.getMessage());
        }

        stage.setScene(new Scene(root, 600, 400));
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        logger.info("Splash created");
    }

    private void showHomeScreenStage() {
        Stage stage = new Stage();
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
        } catch (IOException|NullPointerException e) {
            logger.error("Error while loading home.fxml, here's some further info: " + e.getMessage());
        }

        stage.setScene(new Scene(root));
        logger.info("Home created");

        stage.show();
        logger.info("Home showed");
    }
}
