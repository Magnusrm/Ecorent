package bike.bikeEdit;

import changescene.MainMethods;
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

/**
 * BikeEditController.java
 * @author Team 007
 * @version 1.0
 *
 * This class handles displaying the bike edit scene by using bikeEditView.fxml.
 *
 */
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
        buyDateField.setText(buyDate.substring(0,4) + buyDate.substring(5,7) + buyDate.substring(8));
        powerUsageField.setText(powerUsage);
        typeComboBox.getSelectionModel().select(bike.getType().getName());


    }//end method


    /**
     * Fills the TextFields with the information about the bike that is already stored in the database.
     * @param event     on button click.
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
     * Confirms the changed made to the bike.
     * @param event     on button click.
     */
    @FXML
    void saveChanges(ActionEvent event) {
        int bikeID = Integer.parseInt(bikeIdField.getText());
        String make = makeField.getText();
        double price = Double.parseDouble(priceField.getText());
        String date = buyDateField.getText().substring(0,4) + "-" + buyDateField.getText().substring(4,6) + "-" +
                buyDateField.getText().substring(6);
        LocalDate localDate = LocalDate.parse(date);
        double pwr = Double.parseDouble(powerUsageField.getText());
        Type type = new Type(typeComboBox.getSelectionModel().getSelectedItem());
        Bike editBike = new Bike(localDate, price, make, type, pwr);
        if (factory.editBike(bikeID, editBike)) {
            newInfoAlert("Success", "The info about bike with bike ID " + bikeID + " is now updated!");
        } else {
            newInfoAlert("Something went wrong!", "Cannot connect to system, please check your connection");
        }//end else
    }//end method
}

