package login;

import control.Admin;
import control.Factory;
import control.Password;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import changescene.ChangeScene;
import javafx.event.ActionEvent;
import java.awt.*;

public class loginController {

    private String[] user;
    private String[] pw;
    private Factory factory = new Factory();

    @FXML
    private Button signInBtn;

    @FXML
    private Label incorrectLbl;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    void signIn(ActionEvent event) throws Exception {
        factory.updateSystem();
        //Getting user names and password from database
        for(int i = 0; i<factory.getAdmins().size();i++){
            user = new String[factory.getAdmins().size()];
            user[i] = factory.getAdmins().get(i).getEmail();
            pw = new String[factory.getAdmins().size()];
            pw[i] = factory.getAdmins().get(i).getPassword();
        }//end loop
        for(int i = 0; i<user.length;i++){
            //if((usernameField.getText().equals(user[i]) && Password.check(passwordField.getText(),factory.password(user[i])))) {
                System.out.println("Nice!");
                ChangeScene cs = new ChangeScene();
                cs.setScene(event, "/main/mainView.fxml");
           // }//end if
        } //end loop
        incorrectLbl.setTextFill(Color.web("#ff0000"));
        incorrectLbl.setText("Your password is incorrect!");
    }//end method




}
