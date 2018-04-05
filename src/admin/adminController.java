package admin;

import changescene.ChangeScene;
import changescene.CloseWindow;
import changescene.popupScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class adminController {
// test

    @FXML
    private Button homeBtn;

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
    private Button adminBtn;

    @FXML
    private Button createNewAdminBtn;

    @FXML
    private TextField newAdminEmailField;

    @FXML
    private TextField deleteAdminEmailField;

    @FXML
    private TextField newPasswordField2;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField oldPasswordField;



    @FXML
    void createNewAdmin(ActionEvent event) throws Exception {
        popupScene ps = new popupScene();
        ps.setScene(event, "/admin/adminNewAdminView.fxml");
    }

    @FXML
    void deleteAdmin(ActionEvent event) throws Exception {
        popupScene ps = new popupScene();
        ps.setScene(event, "/admin/adminDeleteAdminView.fxml");
    }

    @FXML
    void changePassword(ActionEvent event) throws Exception {
        popupScene ps = new popupScene();
        ps.setScene(event, "/admin/adminChangePasswordView.fxml");
    }


    @FXML
    void changeToBikeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeView.fxml");
    }

    @FXML
    void changeToDockScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/dock/dockView.fxml");
    }

    @FXML
    void changeToMapScene(ActionEvent event) {
        /*ChangeScene cs = new ChangeScene();
        cs.setScene(event, "");*/
    }

    @FXML
    void changeToStatsScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/stats/statsView.fxml");
    }

    @FXML
    void changeToAdminScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/admin/adminView.fxml");
    }

    @FXML
    void changeToHomeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/main/mainView.fxml");
    }


    @FXML
    void logOut(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/login/loginView.fxml");
    }

    @FXML
    void createNewAdminConfirm(ActionEvent event) throws Exception{
        String email = newAdminEmailField.getText();

        CloseWindow cw = new CloseWindow(event);

        System.out.println(email);
    }

    @FXML
    void deleteAdminConfirm(ActionEvent event) throws Exception{
        String email = deleteAdminEmailField.getText();

        CloseWindow cw = new CloseWindow(event);

        System.out.println(email);
    }

    @FXML
    void changePasswordConfirm(ActionEvent event) {
        String oldPassword = oldPasswordField.getText();
        String newPassword = newPasswordField.getText();
        String newPassword2 = newPasswordField2.getText();
        System.out.println("Old password: " + oldPassword + "\n New Password: " + newPassword + "\nRepeated password: " + newPassword2);
        //+ "\nRepeated password: " + newPassword2

        CloseWindow cw = new CloseWindow(event);
    }


}
