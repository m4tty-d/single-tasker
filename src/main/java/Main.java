import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main class starts the application.
 */
public class Main extends Application {

    /**
     * Logger instance used for logging.
     */
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/splash.fxml"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        logger.info("Splash created");

        primaryStage.show();
        logger.info("Splash showed");
    }

    public static void main(String[] args) {
        logger.info("Application started");

        launch(args);
    }
}
