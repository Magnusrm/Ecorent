package changescene;

import control.Bike;
import control.Dock;
import control.Factory;
import control.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import loginAdm.CurrentAdmin;
import model.BikeStatsModel;
import model.DockStatsModel;
import model.RepairModel;

public class MainMethods {

    public Factory factory = new Factory();
    public Bike bike;
    public Dock dock;
    public Type type;
    public WebEngine engine = new WebEngine();
    public BikeStatsModel bsm = new BikeStatsModel();
    public DockStatsModel dsm = new DockStatsModel();
    public DockStatsModel dockStatsModel = new DockStatsModel();


    public void newPopup(String fxmlname, String title){
        Stage popup;
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(fxmlname));
            Scene scene = new Scene(parent);

            // lag ny stage
            popup = new Stage();
            popup.setScene(scene);
            popup.setTitle(title);
            popup.setResizable(false);
            popup.show();
        }catch(Exception e){
            System.out.println("Error i popup kontruktør: " + e);
        }
    }

    public void closeWindow(ActionEvent event){
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void changeScene(ActionEvent event, String fxmlname) throws Exception{

        Parent parent = FXMLLoader.load(getClass().getResource(fxmlname));
        Scene scene = new Scene(parent);

        // hent stage info
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }


    // main buttons
    @FXML
    void changeToBikeScene(ActionEvent event) throws Exception {
        changeScene(event, "/bike/BikeView.fxml");
    }

    @FXML
    void changeToDockScene(ActionEvent event) throws Exception {
        changeScene(event, "/dock/DockView.fxml");
    }

    @FXML
    void changeToMapScene(ActionEvent event) throws Exception {
        changeScene(event, "/map/MapView.fxml");
    }

    @FXML
    void changeToStatsScene(ActionEvent event) throws Exception {
        changeScene(event, "/stats/StatsView.fxml");
    }

    @FXML
    void changeToAdminScene(ActionEvent event) throws Exception {
        changeScene(event, "/admin/AdminView.fxml");
    }

    @FXML
    void changeToHomeScene(ActionEvent event) throws Exception {
        changeScene(event, "/main/MainView.fxml");
    }

    @FXML
    void logOut(ActionEvent event) throws Exception {
        CurrentAdmin.getInstance().setAdmin(null);
        changeScene(event, "/login/LoginView.fxml");
    }
}
