package bike.bikeRepair;

import changescene.ChangeScene;
import changescene.PopupScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;

public class BikeRepairController {

    @FXML
    private Button bikeRepairReturnedBtn;

    @FXML
    private Button bikeRepairSentBtn;

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
    private Button homeBtn;

    @FXML
    private TextArea descReturnedTextArea;

    @FXML
    private TextField priceReturnedField;

    @FXML
    private TextField dateReturnedField;

    @FXML
    private Button submitSentBtn;

    @FXML
    private Button returnedSubmitBtn;

    @FXML
    private TextArea descSentTextArea;

    @FXML
    private TextField dateSentField;

    @FXML
    private TextField bikeIdReturnedField;

    @FXML
    private TextField bikeIdSentField;


    @FXML
    void changeToRepairReturnedView(ActionEvent event)throws Exception {
        PopupScene ps = new PopupScene();
        ps.setScene(event, "/bike/bikeRepair/BikeRepairReturnedView.fxml");
        ps.setTitle("Register returned repair");

    }

    @FXML
    void changeToRepairSentView(ActionEvent event) throws Exception {
        PopupScene ps = new PopupScene();
        ps.setScene(event, "/bike/bikeRepair/BikeRepairSentView.fxml");
        ps.setTitle("Register sent repair");
    }

    @FXML
    void registerRepairSentConfirm(){
        System.out.println(bikeIdSentField.getText());
    }

    @FXML
    void registerRepairReturnedConfirm(){
        System.out.println(bikeIdReturnedField.getText());
    }




    @FXML
    void changeToBikeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
      cs.setScene(event, "/bike/BikeView.fxml");
    }

    @FXML
    void changeToDockScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/dock/DockView.fxml");
    }

    @FXML
    void changeToMapScene(ActionEvent event) throws Exception{
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/map/MapView.fxml");
    }

    @FXML
    void changeToStatsScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/stats/StatsView.fxml");
    }

    @FXML
    void changeToAdminScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/admin/AdminView.fxml");
    }

    @FXML
    void changeToHomeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/main/MainView.fxml");
    }

    @FXML
    void logOut(ActionEvent event) throws Exception {

        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/login/LoginView.fxml");

    }
}