package main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import changescene.ChangeScene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import loginAdm.CurrentAdmin;
import java.net.URL;
import java.util.ResourceBundle;
import changescene.ChangeToMainScenes;


public class MainController extends ChangeToMainScenes implements Initializable{

    @FXML
    private Label adminEmailLbl;

    @FXML
    private Label adminRightsLbl;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        adminEmailLbl.setText("Logged in as: " + CurrentAdmin.getInstance().getAdmin().getEmail());
        if(CurrentAdmin.getInstance().getAdmin().isMainAdmin()){
            adminRightsLbl.setText("You have main admin priviliges");
         } else {
         adminRightsLbl.setText("Your priviliges might be restricted");
        }
    }//end method





}
