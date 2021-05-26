package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WelcomeViewController {
    @FXML
    Label welcomeMainLabel;

    @FXML
    Button welcomeLibraryButton;

    @FXML
    Button welcomeArenaButton;

    public void welcomeLibraryButtonOnClick(javafx.event.ActionEvent actionEvent) {
        try {
            SceneSwitchController.switchToLibraryView(actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void welcomeArenaButtonOnClick(javafx.event.ActionEvent actionEvent) {
        try {
            SceneSwitchController.switchToView(actionEvent, "arenaView");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
