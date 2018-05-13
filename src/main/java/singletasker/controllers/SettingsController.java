package singletasker.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class SettingsController implements Initializable {

    @FXML
    private ComboBox<String> pomodoroTimeBox;

    @FXML
    private ComboBox<String> shortBreakBox;

    @FXML
    private ComboBox<String> longBreakBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Integer> times = Arrays.asList(5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60);
        List<String> timeString = times.stream().map(time -> time + " minutes").collect(Collectors.toList());

        pomodoroTimeBox.getItems().addAll(timeString);
        pomodoroTimeBox.getSelectionModel().select(4);

        shortBreakBox.getItems().addAll(timeString);
        shortBreakBox.getSelectionModel().select(0);

        longBreakBox.getItems().addAll(timeString);
        longBreakBox.getSelectionModel().select(2);
    }

    public void handleSaveBtnClick(ActionEvent event) {
        getNumberFromString(pomodoroTimeBox.getValue());
        getNumberFromString(shortBreakBox.getValue());
        getNumberFromString(longBreakBox.getValue());
    }

    private Integer getNumberFromString(String str) {
        return Integer.parseInt(str.replaceAll("\\D+",""));
    }
}
