package bike.bikeRepair;

import changescene.ChangeScene;
import changescene.popupScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
        popupScene ps = new popupScene();
        ps.setScene(event, "/bike/bikeRepair/bikeRepairReturnedView.fxml");
        ps.setTitle("Register returned repair");
    }

    @FXML
    void changeToRepairSentView(ActionEvent event) throws Exception {
        popupScene ps = new popupScene();
        ps.setScene(event, "/bike/bikeRepair/bikeRepairSentView.fxml");
        ps.setTitle("Register sent repair");
    }

    @FXML
    void registerRepairSentConfirm(){

    }

    @FXML
    void registerRepairReturnedConfirm(){

    }




    @FXML
    void changeToBikeScene(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
      //  cs.setScene(event, "/bike/bikeView.fxml");
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
}