package bike.bikeRepair;

import changescene.ChangeScene;
import changescene.CloseWindow;
import changescene.PopupScene;
import control.Bike;
import control.Factory;
import control.Repair;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.BikeModel;

public class BikeRepairController {
    private Factory factory = new Factory();
    private int bikeID;

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
    private TextField bikeIdField;


    @FXML
    void changeToRepairReturnedView(ActionEvent event)throws Exception {
        if(bikeIdField.getText() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("EMPTY FIELD");
            alert.setHeaderText(Alert.AlertType.WARNING.name());
            alert.setContentText("You have to write in a bike ID!");
        }else {
            bikeID = Integer.parseInt(bikeIdField.getText());
            PopupScene ps = new PopupScene();
            ps.setScene(event, "/bike/bikeRepair/BikeRepairReturnedView.fxml");
            ps.setTitle("Register returned repair");
        }//end condition
    }//end method

    @FXML
    void changeToRepairSentView(ActionEvent event) throws Exception {
        if(bikeIdField.getText() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("EMPTY FIELD");
            alert.setHeaderText(Alert.AlertType.WARNING.name());
            alert.setContentText("You have to write in a bike ID!");
            alert.showAndWait();
        }else {
            bikeID = Integer.parseInt(bikeIdField.getText());
            BikeModel bikeM = new BikeModel();
            if(bikeM.bikeExists(bikeID)){
                System.out.println(bikeID + " bike exists");
                for(int i = 0; i<factory.getBikes().size();i++){
                    if(factory.getBikes().get(i).getBikeId() == bikeID)factory.getBikes().get(i).setRepairing(true);
                }//end loop
            }else{
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("BIKE ID DON'T EXIST");
                alert1.setHeaderText(Alert.AlertType.WARNING.name());
                alert1.setContentText("The given bike id do not exist!");
                alert1.showAndWait();
            }//end condition
            PopupScene ps = new PopupScene();
            ps.setScene(event, "/bike/bikeRepair/BikeRepairSentView.fxml");
            ps.setTitle("Register sent repair");
        }//end condition
    }

    @FXML
    void registerRepairSentConfirm(ActionEvent event){
        factory.updateSystem();
       String dateSent = dateSentField.getText().substring(0,4) + "-" + dateSentField.getText().substring(4,6) +
                "-" + dateSentField.getText().substring(6);
       String beforeDesc = descSentTextArea.getText();
       for(Bike b: factory.getBikes()){
           if(!b.isRepairing())System.out.println(b.getBikeId());
       }//end loop
      /* Repair repair = new Repair(dateSent,beforeDesc,bikeID);
       //repair.setBikeId(bikeID);
       if(factory.addRepair(repair)){
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Success!");
           alert.setHeaderText(Alert.AlertType.INFORMATION.name());
           alert.setContentText("Repair is now registered!");
           alert.showAndWait();
       }else{
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setTitle("Failed!");
           alert.setHeaderText(Alert.AlertType.INFORMATION.name());
           alert.setContentText("Repair is not registered, try again!");
           alert.showAndWait();
       }//end condition
       */
        CloseWindow closeWindow = new CloseWindow(event);
    }//end method

    @FXML
    void registerRepairReturnedConfirm(){

    }




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
    void changeToMapScene(ActionEvent event) throws Exception{
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