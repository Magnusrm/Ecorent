package bike;

import bike.bikeType.bikeTypeController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import changescene.ChangeScene;

public class bikeController {

    @FXML
    private Button editBikeBtn;

    @FXML
    private Button infoBikeBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button deleteBikeBtn;

    @FXML
    private Button repairBikeBtn;

    @FXML
    private Button newBikeBtn;

    @FXML
    private Button editBikeTypesBtn;

    @FXML
    private TextField bikeIdField;

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
    void changeToBikeEditView(ActionEvent event) throws Exception {

    }

    @FXML
    void changeToBikeTypeView(ActionEvent event) throws Exception {

            ChangeScene cs = new ChangeScene();
            cs.setScene(event, "/bike/bikeType/bikeTypeView.fxml");

    }


    @FXML
    void changeToBikeNewView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeNew/bikeNewView.fxml");
    }

    @FXML
    void changeToBikeRepairView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeRepair/bikeRepairView.fxml");
    }

    @FXML
    void changetoBikeInfoView(ActionEvent event) {

    }

    @FXML
    void deleteBike(ActionEvent event) {

    }

    @FXML
    void deleteAllBikesWithoutType(ActionEvent event){

    }

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
