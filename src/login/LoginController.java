package login;

import changescene.MainMethods;
import control.Admin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import loginAdm.CurrentAdmin;
import loginAdm.LoginBean;
import loginAdm.LoginDb;

import java.security.GeneralSecurityException;

import model.AdminModel;

/**
 * LoginController.java
 * @author Team007
 * @version 1.0
 *
 * This class handles logging in as an admin, authenticating password
 */
public class LoginController extends MainMethods {

    @FXML
    private Label incorrectLbl;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    /**
     * Handles logging in based on the information given in the usernameField passwordField.
     * Authenticates user by using LoginBean.
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void signIn(ActionEvent event) throws Exception {
        factory.updateSystem();
        LoginBean loginBean = new LoginBean(usernameField.getText(),passwordField.getText());
        if(LoginDb.authenticateUser(loginBean)){
            AdminModel adminModel = new AdminModel();
            Admin admin = adminModel.getAdmin(loginBean.getEmail());
            CurrentAdmin currentAdmin = CurrentAdmin.getInstance();
            currentAdmin.setAdmin(admin);
            changeScene(event, "/main/MainView.fxml");

        }else{
            incorrectLbl.setTextFill(Color.web("#ff0000"));
            incorrectLbl.setText("Your password or email is incorrect!");
            throw new GeneralSecurityException("Feil passord eller email");
        }
    }

}

