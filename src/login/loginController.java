package login;

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

    private String user = "admin";
    private String pw = "root";

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
        if(usernameField.getText().equals(user) && passwordField.getText().equals(pw)){

            System.out.println("Nice!");
            ChangeScene cs = new ChangeScene();
            cs.setScene(event, "/main/mainView.fxml");

        } else {
            incorrectLbl.setTextFill(Color.web("#ff0000"));
            incorrectLbl.setText("Your password is incorrect!");
        }

    }




}
