package bike.bikeEdit;

import changescene.ChangeScene;
import changescene.MainMethods;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import loginAdm.CurrentAdmin;
import control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BikeEditController  extends MainMethods implements Initializable {

    @FXML
    private TextField bikeIdField;

    @FXML
    private TextField makeField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField buyDateField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField powerUsageField;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        factory.updateSystem();
        ObservableList<String> types = FXCollections.observableArrayList();
        String[] visualized = new String[factory.getTypes().size()];
        for (int i = 0; i < visualized.length; i++) {
            visualized[i] = factory.getTypes().get(i).getName();
        }//end loop
        types.addAll(visualized);
        typeComboBox.setItems(types);
        bike = factory.getBikes().get(0);
        String bikeID = "" + bike.getBikeId();
        String make = bike.getMake();
        String price = "" + bike.getPrice();
        String buyDate = "" + bike.getBuyDate();
        String powerUsage = "" + bike.getPowerUsage();
        bikeIdField.setText(bikeID);
        makeField.setText(make);
        priceField.setText(price);
        buyDateField.setText(buyDate);
        powerUsageField.setText(powerUsage);
        typeComboBox.getSelectionModel().select(bike.getType().getName());


    }//end method


    /**
     * @param event
     * @Author Team 007
     * <p>
     * Fills the TextFields with the information about the bike that is already stored in the database.
     */
    @FXML
    void fillInfo(ActionEvent event) {
        for (Bike b : factory.getBikes()) System.out.println(b);
        int bikeID = Integer.parseInt(bikeIdField.getText());
        for (int i = 0; i < factory.getBikes().size(); i++) {
            if (factory.getBikes().get(i).getBikeId() == bikeID) bike = factory.getBikes().get(i);
        }//end loop
        if (bike != factory.getBikes().get(0)) {
            String make = bike.getMake();
            String price = "" + bike.getPrice();
            String buyDate = "" + bike.getBuyDate();
            String powerUsage = "" + bike.getPowerUsage();
            makeField.setText(make);
            priceField.setText(price);
            buyDateField.setText(buyDate.substring(0,4) + buyDate.substring(5,7) + buyDate.substring(8));
            powerUsageField.setText(powerUsage);
            typeComboBox.getSelectionModel().select(bike.getType().getName());
        }//end if
    }//end method


    /**
     * @param event
     * @Author Team 007
     * <p>
     * Confirms the changed made to the bike.
     */
    @FXML
    void saveChanges(ActionEvent event) {
        int bikeID = Integer.parseInt(bikeIdField.getText());
        String make = makeField.getText();
        double price = Double.parseDouble(priceField.getText());
        LocalDate localDate = LocalDate.parse(buyDateField.getText());
        double pwr = Double.parseDouble(powerUsageField.getText());
        Type type = new Type(typeComboBox.getSelectionModel().getSelectedItem());
        Bike editBike = new Bike(localDate, price, make, type, pwr);
        if (factory.editBike(bikeID, editBike)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("The info about bike with bike ID " + bikeID + " is now updated!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong!");
            alert.setHeaderText(null);
            alert.setContentText("Cannot connect to system, please check your connection");
            alert.showAndWait();
        }//end else
    }//end method
}

