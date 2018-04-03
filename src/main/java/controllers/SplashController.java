package controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    @FXML
    private StackPane rootPane;

    private static int SHOWTIME = 500;

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
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            rootPane.getScene().getWindow().hide();

                            Stage stage = new Stage();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.show();
                        }
                    });


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
