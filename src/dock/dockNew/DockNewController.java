package dock.dockNew;

import changescene.ChangeScene;
import control.Dock;
import control.Factory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DockNewController{
    private Factory factory = new Factory();

    @FXML
    private TextField dockNameField;

    @FXML
    private TextField xCoordField;

    @FXML
    private TextField yCoordField;

    @FXML
    private Button createNewDockBtn;

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
    void createNewDockConfirm(ActionEvent event){ // created a new dock
        try{
            Dock dock = new Dock(dockNameField.getText(), Double.parseDouble(xCoordField.getText()), Double.parseDouble(yCoordField.getText()));

            if(factory.addDock(dock)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("New dock saved!");
                alert.setHeaderText(null);
                alert.setContentText("Dock is now saved and can be used to store bikes.");
                alert.showAndWait();
                for (Dock d : factory.getDocks()) {
                    System.out.println(d);
                }
                ChangeScene cs = new ChangeScene();
                cs.setScene(event, "/dock/DockView.fxml");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something went wrong!");
                alert.setHeaderText(null);
                alert.setContentText("Dock is not saved, make sure to fill out the form in the given format.");
                alert.showAndWait();
                ChangeScene cs1 = new ChangeScene();
                cs1.setScene(event, "/dock/DockNew/DockNewView.fxml");
            }
        } catch (Exception e){
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong!");
            alert.setHeaderText(null);
            alert.setContentText("Dock is not saved, make sure to fill out the form in the given format");
            alert.showAndWait();
            System.out.println("Error createNewDockConfirm: " + e);
        }
    }







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
