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
import loginAdm.CurrentAdmin;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class BikeNewController implements Initializable{
    private Factory factory = new Factory();

    @FXML
    private TextField makeField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField powerUsageField;

    @FXML
    private TextField buyDateField;

    @FXML
    private TextField amountField;

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


    /**
     *
     * Creates a new bike based on the information given in the TextFields.
     *
     * @param event is an object of ActionEvent.java
     */

    @FXML
    void createNewBike(ActionEvent event) {
        String date = buyDateField.getText().substring(0,4) + "-" + buyDateField.getText().substring(4,6) + "-" +
                buyDateField.getText().substring(6);
        LocalDate date1 = LocalDate.parse(date);
        try {
           Bike bike = new Bike(date1,Double.parseDouble(priceField.getText()),
                   makeField.getText(),new Type(typeComboBox.getValue()),Double.parseDouble(powerUsageField.getText()));
           for(int i = 0; i<Integer.parseInt(amountField.getText())-1;i++){
            factory.addBike(bike);
           }
            if(factory.addBike(bike)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bike saved!");
                alert.setHeaderText(null);
                alert.setContentText("Bike is now saved and can be rented out");
                alert.showAndWait();
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
               cs1.setScene(event, "/bike/bikeNew/BikeNewView.fxml");
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
