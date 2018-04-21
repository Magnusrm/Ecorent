package bike.bikeInfo;

import changescene.ChangeScene;
import control.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import loginAdm.CurrentAdmin;
import model.BikeStatsModel;
import model.RepairModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.DuplicateFormatFlagsException;
import java.util.Iterator;
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
    private TextField bikeIdField;

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
                    int repairID = Integer.parseInt(newValue);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information about repair " + repairID);
                    alert.setHeaderText(Alert.AlertType.INFORMATION.name());
                    ArrayList<Repair> repairs = new ArrayList<>();
                    repairs.addAll(factory.getRepairsNotReturned());
                    repairs.addAll(factory.getRepairsCompleted());
                    String s = "";
                    for(Repair r:repairs)if(repairID == r.getRepair_id())s+=r.toString();
                    alert.setContentText(s);
                    alert.showAndWait();
                }//end method
            });

        }catch (Exception e){e.printStackTrace();}

    }


    /**
     * Displays the info about the bike described in the bikeIdField.
     * This method also fills the ListView with the repairs the bike have had in the past.
     */
    @FXML
    void showInfo(){
        factory.updateSystem();
        int bikeID = Integer.parseInt(bikeIdField.getText());

        //Creating a object-view list
        ObservableList<String> repairIds = FXCollections.observableArrayList();
        ArrayList<String> visualized = new ArrayList<>();
        ArrayList<String> visualized1 = new ArrayList<>();
        ArrayList<String> complete = new ArrayList<>();

        //Adding the repair ids registered on the bike
        for(int i = 0; i<factory.getRepairsNotReturned().size();i++){
            String s = null;
            if(factory.getRepairsNotReturned().get(i).getBikeId() == bikeID) s = "" +
                    factory.getRepairsNotReturned().get(i).getRepair_id();
            if(s!= null)visualized.add(s);
        }//end loop
        for(int i = 0;i<factory.getRepairsCompleted().size();i++){
            String s = null;
            if(factory.getRepairsCompleted().get(i).getBikeId() == bikeID)s = "" +
                    factory.getRepairsCompleted().get(i).getRepair_id();
            if(s!=null)visualized1.add(s);
        }//end loop

        //Removing duplicate version
        for(int i = 0; i<factory.getRepairsNotReturned().size();i++){
            for(RepairReturned r:factory.getRepairsCompleted()){
                if(factory.getRepairsNotReturned().get(i).getRepair_id() == r.getRepair_id()){
                    factory.getRepairsNotReturned().remove(i);
                }//end condition
            }//end loop
        }//end loop

        complete.addAll(visualized);
        complete.addAll(visualized1);
        //Adding them in list view
        repairIds.addAll(complete);
        repairIdListView.setItems(repairIds);

        RepairModel repairModel = new RepairModel();
        for(RepairSent i : factory.getRepairsNotReturned())System.out.println(i);

        Bike bike = null;
        for(int i = 0; i<factory.getBikes().size();i++){
            if(factory.getBikes().get(i).getBikeId() == bikeID)bike = factory.getBikes().get(i);
        }//end loop
        if(bike != null){
            String price = "" + bike.getPrice();
            String type = "" + bike.getType().getName();
            String make = "" + bike.getMake();
            String date = "" + bike.getBuyDate().toString();
            String battery = "" + bike.getBattery() + "%";
            priceLbl.setText(price);
            typeLbl.setText(type);
            makeLbl.setText(make);
            dateLbl.setText(date);
            batteryLbl.setText(battery);

        }//end condition
        if(bike == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong!");
            alert.setHeaderText(null);
            alert.setContentText("Cannot find the given bike!");
            alert.showAndWait();
        }//end condition
        // show marker on map
        ArrayList<double[]> recentPositions = bsm.getMostRecentCoordinates();
        for (double[] p : recentPositions){
            if (p[0] == bikeID){
                engine.executeScript("document.createMarkerEgen(" + p[0] + ", " + p[1] + ", " + p[2] + ");");
            }
        }

    }//end method








    // main buttons
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
