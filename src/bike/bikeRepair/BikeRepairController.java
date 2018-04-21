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
import java.util.ResourceBundle;

public class BikeRepairController extends MainMethods implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LocalDateTime ldt = LocalDateTime.now();
        String time = ("" + ldt + "").replaceAll("T", " ");
        time = time.substring(0, time.length() - 4);
        dateSentField.setText(time);
    }

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
                        "out the form in the correct format");
            }//end condition
        }//end condition
        closeWindow(event);
    }//end method

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