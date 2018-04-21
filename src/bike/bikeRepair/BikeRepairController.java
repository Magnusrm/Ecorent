package bike.bikeRepair;

import changescene.ChangeScene;
import changescene.CloseWindow;
import changescene.MainMethods;
import changescene.PopupScene;
import control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import loginAdm.CurrentAdmin;
import control.*;
import model.BikeModel;

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

    @FXML
    void registerRepairSentConfirm(ActionEvent event){
        factory.updateSystem();
        int bikeId = Integer.parseInt(bikeIdSentField.getText());
        BikeModel b = new BikeModel();
        if(!b.bikeExists(bikeId)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Bike does not exist!");
            alert.setHeaderText(Alert.AlertType.WARNING.name());
            alert.setContentText("Bike with ID " + bikeId + " does not exist!");
            alert.showAndWait();
        }else {
            String date = dateSentField.getText().substring(0,4) + "-" +
                    dateSentField.getText().substring(4,6) + "-" +
                    dateSentField.getText().substring(6);
            String description = descSentTextArea.getText();
            RepairSent repairSent = new RepairSent(date, description, bikeId);
            if (factory.repairSent(repairSent)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Repair confirmed");
                alert.setHeaderText(Alert.AlertType.INFORMATION.name());
                alert.setContentText("Bike with ID " + bikeId + " is now registered in repair");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("OPERATION FAILED");
                alert.setHeaderText(Alert.AlertType.WARNING.name());
                alert.setContentText("Something went wrong! Please make sure you fill " +
                        "out the form in the correct format");
                alert.showAndWait();
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Bike does not exist!");
            alert.setHeaderText(Alert.AlertType.WARNING.name());
            alert.setContentText("Bike with ID " + bikeID + " does not exist!");
            alert.showAndWait();
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
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Repair confirmed");
                    alert.setHeaderText(Alert.AlertType.INFORMATION.name());
                    alert.setContentText("Bike with ID " + bikeID + " is now returned from repair");
                    alert.showAndWait();
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("OPERATION FAILED");
                    alert.setHeaderText(Alert.AlertType.WARNING.name());
                    alert.setContentText("Something went wrong! Please make sure you fill " +
                            "out the form in the correct format");
                    alert.showAndWait();
                }//end condition
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("OPERATION FAILED");
                alert.setHeaderText(Alert.AlertType.WARNING.name());
                alert.setContentText("The given bike is not in repairing!");
                alert.showAndWait();
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