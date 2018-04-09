package bike.bikeRepair;

import changescene.ChangeScene;
import changescene.CloseWindow;
import changescene.PopupScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
    void changeToRepairReturnedView(ActionEvent event)throws Exception {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        PopupScene ps = new PopupScene();
        ps.setScene(window, event, "/bike/bikeRepair/BikeRepairReturnedView.fxml");
        ps.setTitle("Register returned repair");
    }

    @FXML
    void changeToRepairSentView(ActionEvent event) throws Exception {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        PopupScene ps = new PopupScene();
        ps.setScene(window, event, "/bike/bikeRepair/BikeRepairSentView.fxml");
        ps.setTitle("Register sent repair");
    }

    @FXML
    void registerRepairSentConfirm(ActionEvent event){
        CloseWindow cw = new CloseWindow(event);
    }

    @FXML
    void registerRepairReturnedConfirm(ActionEvent event){
        CloseWindow cw = new CloseWindow(event);
    }








    // main buttons below

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
    void changeToMapScene(ActionEvent event) throws Exception {
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