package login;

import changescene.MainMethods;
import control.Admin;
import control.Factory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import changescene.ChangeScene;
import loginAdm.CurrentAdmin;
import loginAdm.LoginBean;
import loginAdm.LoginDb;

import java.awt.event.ActionListener;
import java.lang.reflect.InvocationHandler;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ResourceBundle;

import model.AdminModel;

public class LoginController extends MainMethods {

    @FXML
    private Label incorrectLbl;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;


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

