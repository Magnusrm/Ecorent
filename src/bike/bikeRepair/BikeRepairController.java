package bike.bikeRepair;

import changescene.MainMethods;
import control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.BikeModel;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * BikeRepairController.java
 * @author Team 007
 * @version 1.0
 *
 * This class handles sending and returning repairs and navigating between BikeRepairReturnedView.fxml, BikeRepairSentView.fxml and BikeRepairView.fxml
 */
public class BikeRepairController extends MainMethods {

    @FXML
    private TextArea descReturnedTextArea;

    @FXML
    private TextField priceReturnedField;

    @FXML
    private TextField dateReturnedField;

    @FXML
    private TextArea descSentTextArea;

    @FXML
    private TextField dateSentField;

    @FXML
    private TextField bikeIdReturnedField;

    @FXML
    private TextField bikeIdSentField;

    /**
     * Registers a new repair which takes the various fields as entries. Register the specific repair when it's sent to repair.
     * @param event button click
     */
    @FXML
    void registerRepairSentConfirm(ActionEvent event){
        factory.updateSystem();
        int bikeId = Integer.parseInt(bikeIdSentField.getText());
        BikeModel b = new BikeModel();
        if(!b.bikeExists(bikeId)){
            newWarningAlert("Bike does not exist!", "Bike with ID " + bikeId + " does not exist!");
        }else {
            String date = dateSentField.getText().substring(0,4) + "-" +
                    dateSentField.getText().substring(4,6) + "-" +
                    dateSentField.getText().substring(6);
            String description = descSentTextArea.getText();
            RepairSent repairSent = new RepairSent(date, description, bikeId);
            if (factory.repairSent(repairSent)) {
                newInfoAlert("Repair confirmed", "Bike with ID " + bikeId + " is now registered in repair");
            } else {
                newWarningAlert("OPERATION FAILED", "Something went wrong! Please make sure you fill " +
                        "out the form in the correct format and that the bike is not already in for repair");
            }//end condition
        }//end condition
        closeWindow(event);
    }//end method

    /**
     * Register a returned repair which takes the various fields as entries. Registers the specific repair when it's returned.
     * @param event button click
     */
    @FXML
    void registerRepairReturnedConfirm(ActionEvent event){
        factory.updateSystem();
        boolean execute = false;
        int bikeID = Integer.parseInt(bikeIdReturnedField.getText());
        BikeModel b = new BikeModel();
        if(!b.bikeExists(bikeID)){
            newWarningAlert("Bike does not exist!","Bike with ID " + bikeID + " does not exist!");
        }else{
            for(Bike b1:factory.getBikes()){
                if(b1.getBikeId() == bikeID){
                    execute = b1.isRepairing();
                }//end condition
            }//end loop
            if(execute){
                String date = dateReturnedField.getText().substring(0,4) + "-" +
                        dateReturnedField.getText().substring(4,6) + "-" +
                        dateReturnedField.getText().substring(6);
                double price = Double.parseDouble(priceReturnedField.getText());
                String description = descReturnedTextArea.getText();
                RepairReturned repairReturned = new RepairReturned(date,description,price,bikeID);
                if(factory.repairReturned(repairReturned)){
                    newInfoAlert("Repair confirmed", "Bike with ID " + bikeID + " is now returned from repair");
                }else{
                    newWarningAlert("OPERATION FAILED","Something went wrong! Please make sure you fill " +
                            "out the form in the correct format");
                }//end condition
            }else{
                newWarningAlert("OPERATION FAILED", "The given bike is not in repairing!");
            }//end condition
        }//end condition
        closeWindow(event);
    }//end method

    @FXML
    void changeToRepairReturnedView(ActionEvent event)throws Exception {
        newPopup("/bike/bikeRepair/BikeRepairReturnedView.fxml", "Register Returned Repair");

    }

    @FXML
    void changeToRepairSentView(ActionEvent event) throws Exception {
        newPopup("/bike/bikeRepair/BikeRepairSentView.fxml", "Register Sent Repair");
    }
}//end class