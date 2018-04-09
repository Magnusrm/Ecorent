package bike.bikeNew;

import control.Factory;
import changescene.ChangeScene;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class BikeNewController implements Initializable{
    private Factory factory = new Factory();

    @FXML
    private TextField makeField;

    @FXML
    private Button bikeViewBtn;

    @FXML
    private TextField priceField;

    @FXML
    private TextField buyDateField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private Button saveBtn;

    @FXML
    private Button homeBtn;

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
    private TextField powerUsageField;

    //Notice the types are converted to String array.
    //This is to simplify the clicking and fetching process.
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            factory.updateSystem();
            ObservableList<String> types = FXCollections.observableArrayList();
            String[] visualized = new String[factory.getTypes().size()];
            for (int i = 0; i < visualized.length; i++) {
                visualized[i] = factory.getTypes().get(i).getName();
            }//end loop
            types.addAll(visualized);
            typeComboBox.setItems(types);
        }catch (Exception e){e.printStackTrace();}
    }//end constructor


    @FXML
    void createNewBike(ActionEvent event) {
        try {
           Bike bike = new Bike(LocalDate.now(),Double.parseDouble(priceField.getText()),
                   makeField.getText(),new Type(typeComboBox.getValue()),0);
           if(factory.addBike(bike)){
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Bike saved!");
               alert.setHeaderText(null);
               alert.setContentText("Bike is now saved and can be rented out");
               alert.showAndWait();
               for(Bike b:factory.getBikes()){
                   System.out.println(b);
               }//end loop
               ChangeScene change = new ChangeScene();
               change.setScene(event, "/bike/BikeView.fxml");
           }//end if
            else{
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Something went wrong!");
               alert.setHeaderText(null);
               alert.setContentText("Bike is not saved, make sure to fill out the form in the given format");
               alert.showAndWait();
               ChangeScene cs1 = new ChangeScene();
               cs1.setScene(event, "/bike/BikeNewView.fxml");
           }//end else
        }//end try
        catch(Exception e){
                e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong!");
            alert.setHeaderText(null);
            alert.setContentText("Bike is not saved, make sure to fill out the form in the given format");
            alert.showAndWait();
            }//end catch
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
