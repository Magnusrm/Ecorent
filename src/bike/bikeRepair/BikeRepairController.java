package bike.bikeRepair;

import changescene.ChangeScene;
import changescene.CloseWindow;
import changescene.PopupScene;
import control.Repair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import loginAdm.CurrentAdmin;
import control.*;
import model.BikeModel;

public class BikeRepairController {
    private Factory factory = new Factory();

    @FXML
    private Button bikeRepairReturnedBtn;

    @FXML
    private Button bikeRepairSentBtn;

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

    @FXML
    private TextArea descReturnedTextArea;

    @FXML
    private TextField priceReturnedField;

    @FXML
    private TextField dateReturnedField;

    @FXML
    private Button submitSentBtn;

    @FXML
    private Button returnedSubmitBtn;

    @FXML
    private TextArea descSentTextArea;

    @FXML
    private TextField dateSentField;

    @FXML
    private TextField bikeIdReturnedField;

    @FXML
    private TextField bikeIdSentField;


    @FXML
    void changeToRepairReturnedView(ActionEvent event)throws Exception {
        PopupScene ps = new PopupScene();
        ps.setScene(event, "/bike/bikeRepair/BikeRepairReturnedView.fxml");
        ps.setTitle("Register returned repair");

    }

    @FXML
    void changeToRepairSentView(ActionEvent event) throws Exception {
        PopupScene ps = new PopupScene();
        ps.setScene(event, "/bike/bikeRepair/BikeRepairSentView.fxml");
        ps.setTitle("Register sent repair");
    }

    @FXML
    void registerRepairSentConfirm(){
        factory.updateSystem();
        System.out.println(bikeIdSentField.getText());
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
            System.out.println(date);
            String description = descSentTextArea.getText();
            Repair repairSent = new Repair(date, description, bikeId);
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
                Repair repairReturned = new Repair(date,description,price,bikeID);
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
        CloseWindow cw = new CloseWindow(event);
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
    }//end method
}//end class