package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the splash view.
 */
public class SplashController implements Initializable {

    @FXML
    private StackPane rootPane;

    private static int SHOWTIME = 700;

    private static Logger logger = LoggerFactory.getLogger(SplashController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        (new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(SHOWTIME);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showHome();
                        }

                        private void showHome() {
                            Parent root = null;

                            try {
                                root = FXMLLoader.load(getClass().getResource("/views/home.fxml"));
                            } catch (IOException|NullPointerException e) {
                                logger.error("Error while loading home.fxml, here's some further info: {}", e.getMessage());
                                return;
                            }

                            rootPane.getScene().getWindow().hide();
                            logger.info("Splash hidden");

                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            logger.info("Home created");

                            stage.show();
                            logger.info("Home showed");
                        }
                    });


                } catch (InterruptedException e) {
                    logger.warn("Something went wrong while showing the splash, here's some further info: {}", e.getMessage());
                }

            }
        }).start();
    }
}
