package bike.bikeNew;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class BikeNewController extends MainMethods implements Initializable{

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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            LocalDateTime ldt = LocalDateTime.now();
            String time = ldt.format(formatter);
            buyDateField.setText(time);

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
           for(int i = 0; i<Integer.parseInt(amountField.getText())-1;i++){ //Loop to add multiple bikes
            factory.addBike(bike);
           }//end loop
            if(factory.addBike(bike)) { //The last bike (so we can check the success)
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Bike saved!");
                alert.setHeaderText(null);
                alert.setContentText("Bike is now saved and can be rented out");
                alert.showAndWait();
               changeScene(event, "/bike/BikeView.fxml");
           }//end if
            else{
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Something went wrong!");
               alert.setHeaderText(null);
               alert.setContentText("Bike is not saved, make sure to fill out the form in the given format");
               alert.showAndWait();
               changeScene(event, "/bike/BikeView.fxml");
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

}
