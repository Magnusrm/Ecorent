package dock;

import changescene.ChangeScene;
import control.Factory;
import control.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DockController implements Initializable {

    private Factory factory = new Factory();

    @FXML
    private ComboBox<String> dockIdComboBox;

    @FXML
    private Button editDockBtn;

    @FXML
    private Button dockInfoBtn;

    @FXML
    private Button deleteDockBtn;

    @FXML
    private Button newDockBtn;

    @FXML
    private TextField dockIdField;

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
    private Button deleteDockConfirmBtn;


    //Notice the docks are converted to String array.
    //This is to simplify the clicking and fetching process.
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            factory.updateSystem();
            ObservableList<String> docks = FXCollections.observableArrayList();
            String[] visualized = new String[factory.getDocks().size()];
            for (int i = 0; i < visualized.length; i++) {
                visualized[i] = factory.getDocks().get(i).getName();
            }//end loop
            docks.addAll(visualized);
            dockIdComboBox.setItems(docks);
        }catch (Exception e){e.printStackTrace();}
    }//end constructor


    @FXML
    void changeToNewDockView(ActionEvent event) throws Exception{
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/dock/dockNew/DockNewView.fxml");
    }

    @FXML
    void changeToDockEditView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/dock/dockEdit/DockEditView.fxml");
    }

    @FXML
    void changeToDockInfoView(ActionEvent event) throws Exception {
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/dock/dockInfo/DockInfoView.fxml");
    }

    @FXML
    void deleteDock(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete dock");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you would like to delete the selected dock?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            //... IF OK

        } else {
            // ... IF CANCEL
        }
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
