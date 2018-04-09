package bike.bikeRepair;

import changescene.ChangeScene;
import changescene.CloseWindow;
import changescene.popupScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class bikeRepairController {

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
        popupScene ps = new popupScene();
        ps.setScene(window, event, "/bike/bikeRepair/bikeRepairReturnedView.fxml");
        ps.setTitle("Register returned repair");
    }

    @FXML
    void changeToRepairSentView(ActionEvent event) throws Exception {
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        popupScene ps = new popupScene();
        ps.setScene(window, event, "/bike/bikeRepair/bikeRepairSentView.fxml");
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
        cs.setScene(event, "/bike/bikeView.fxml");
    }

    @FXML
    void changeToDockScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/dock/dockView.fxml");
    }

    @FXML
    void changeToMapScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/map/mapView.fxml");
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
}