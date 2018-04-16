package bike.bikeInfo;

import changescene.ChangeScene;
import control.Bike;
import control.Factory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import loginAdm.CurrentAdmin;
import model.BikeStatsModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BikeInfoController implements Initializable {
    private Factory factory = new Factory();
    private BikeStatsModel bsm= new BikeStatsModel();

    @FXML
    private Label priceLbl;

    @FXML
    private Label typeLbl;

    @FXML
    private Label makeLbl;

    @FXML
    private Label dateLbl;

    @FXML
    private Label batteryLbl;
    
    @FXML
    private Button showInfoBtn;

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
    private Button homeBtn;

    private WebEngine engine;

    @FXML
    private ListView<String> repairIdListView;

    @FXML
    private WebView root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            factory.updateSystem();

            engine = root.getEngine();
            engine.load(this.getClass().getResource("bikemap.html").toExternalForm());
            engine.setJavaScriptEnabled(true);

            repairIdListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    System.out.println("nice, du valgte: " + newValue + " bror");
                }
            });

        }catch (Exception e){e.printStackTrace();}

    }
    public void getBikePosition() {

    }

    /**
     * @Author Team 007
     *
     * Displays the info about the bike described in the bikeIdField.
     * This method also fills the ListView with the repairs the bike have had in the past.
     *
     */
    @FXML
    void showInfo(){
        factory.updateSystem();


       /*


       HER MÅ MAN LEGGE TIL REPAIRS TIL LISTVIEW,VENTER PÅ MODEL SKAL LAGE GET ALL REPAIRS


        */


        for(Bike b:factory.getBikes()){System.out.println(b);}
        Bike bike = null;
        int bikeID = Integer.parseInt(bikeIdField.getText());
        for(int i = 0; i<factory.getBikes().size();i++){
            if(factory.getBikes().get(i).getBikeId() == bikeID)bike = factory.getBikes().get(i);
        }//end loop
        if(bike != null){
            String price = "" + bike.getPrice();
            String type = "" + bike.getType().getName();
            String make = "" + bike.getMake();
            String date = "" + bike.getBuyDate().toString();
            String battery = "" + bike.getPowerUsage();
            priceLbl.setText(price);
            typeLbl.setText(type);
            makeLbl.setText(make);
            dateLbl.setText(date);
            batteryLbl.setText(battery);
        }//end if
        if(bike == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong!");
            alert.setHeaderText(null);
            alert.setContentText("Cannot find the given bike!");
            alert.showAndWait();
        }
        ArrayList<double[]> recentPositions = bsm.getRecentCoordinates();
        for (double[] p : recentPositions){
            if (p[0] == bikeID){
                engine.executeScript("document.createMarkerEgen(" + p[0] + ", " + p[1] + ", " + p[2] + ");");
            }
        }

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
        CurrentAdmin.getInstance().setAdmin(null);
        ChangeScene cs = new ChangeScene();
        cs.setScene(event, "/login/LoginView.fxml");

    }
}
