package main;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import loginAdm.CurrentAdmin;
import java.net.URL;
import java.util.ResourceBundle;
import changescene.MainMethods;


public class MainController extends MainMethods implements Initializable{

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
