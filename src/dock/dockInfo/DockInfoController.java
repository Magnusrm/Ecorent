package dock.dockInfo;

import changescene.ChangeScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import control.*;

public class DockInfoController implements Initializable {

    private Factory factory = new Factory();

    @FXML
    private Button showInfoBtn;

    @FXML
    private Label nameLbl;

    @FXML
    private Label powerDrawLbl;

    @FXML
    private ComboBox<String> dockIdComboBox;

    @FXML
    private ListView<String> bikeIdListView;

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
    void showInfo(ActionEvent event){

        String dockName = dockIdComboBox.getValue();

    //    bikesAtDock(dockName);

        // add bikeId's to listview
        ObservableList<String> bikes= FXCollections.observableArrayList();
        String[] visualized = new String[factory.getBikes().size()];
        for (int i = 0; i < visualized.length; i++) {
            visualized[i] = "" + factory.getBikes().get(i).getBikeId();
        }//end loop
        bikes.addAll(visualized);
        bikeIdListView.setItems(bikes);

    }

    //Notice the bikes and docks are converted to String array.
    //This is to simplify the clicking and fetching process.
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            factory.updateSystem();

            // add dockId's to comboBox
            ObservableList<String> docks = FXCollections.observableArrayList();
            String[] visualized2 = new String[factory.getDocks().size()];
            for (int i = 0; i < visualized2.length; i++) {
                visualized2[i] = factory.getDocks().get(i).getName();
            }//end loop
            docks.addAll(visualized2);
            dockIdComboBox.setItems(docks);

        }catch (Exception e){e.printStackTrace();}
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
