package bike;

import control.Factory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import changescene.ChangeScene;
import model.BikeModel;

import java.util.Optional;

public class BikeController {
    Factory factory = new Factory();

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
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeEdit/BikeEditView.fxml");
    }

    @FXML
    void changeToBikeTypeView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeType/BikeTypeView.fxml");
    }

    @FXML
    void changeToBikeNewView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeNew/BikeNewView.fxml");
    }

    @FXML
    void changeToBikeRepairView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeRepair/BikeRepairView.fxml");
    }

    @FXML
    void changetoBikeInfoView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/bike/bikeInfo/BikeInfoView.fxml");
    }


    /**
     * @Author Team 007
     *
     * Deleted the bike based on the clients input in the TextField.
     *
     * @param event
     */
    @FXML
    void deleteBike(ActionEvent event) {
        factory.updateSystem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Are you sure?");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you would like to delete this bike?");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            if(factory.delBike(Integer.parseInt(bikeIdField.getText()))){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success!");
                alert1.setHeaderText(null);
                alert1.setContentText("Bike " + bikeIdField.getText() + " is now deleted!");
                alert1.showAndWait();
            }else{
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Failed!");
                alert1.setHeaderText(null);
                alert1.setContentText("Something went wrong! Please try again");
                alert1.showAndWait();
            }//end condition
        }else{
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Canceling");
            alert2.setHeaderText(null);
            alert2.setContentText("Bike " + bikeIdField.getText() + " will not be deleted");
            alert2.showAndWait();
        }//end condition
    }//end method









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
