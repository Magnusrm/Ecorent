package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import changescene.ChangeScene;
import javafx.scene.control.TextFormatter;

public class mainController {

    @FXML
    private Button newAdminBtn;

    @FXML
    private Button bikesBtn;

    @FXML
    private Button docksBtn;

    @FXML
    private Button mapBtn;

    @FXML
    private Button statsBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    void changeToBikeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeView.fxml");
    }

    @FXML
    void changeToDockScene(ActionEvent event) {

    }

    @FXML
    void changeToMapScene(ActionEvent event) {

    }

    @FXML
    void changeToStatsScene(ActionEvent event) {

    }

    @FXML
    void createNewAdmin(ActionEvent event) {

    }

    @FXML
    void logOut(ActionEvent event) throws Exception {

        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/login/loginView.fxml");

    }

}
