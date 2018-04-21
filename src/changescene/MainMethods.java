package changescene;

import control.Bike;
import control.Dock;
import control.Factory;
import control.Type;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.web.WebEngine;
import javafx.stage.Stage;
import loginAdm.CurrentAdmin;
import model.BikeStatsModel;
import model.DockStatsModel;

import java.util.Optional;

/**
 * MainMethods.java
 * @author Team007
 *
 * This class contains frequently used methods and object variables that all the classes inherits.
 */
public class MainMethods {

    public Factory factory = new Factory();
    public Bike bike;
    public Dock dock;
    public Type type;
    public WebEngine engine = new WebEngine();
    public BikeStatsModel bsm = new BikeStatsModel();
    public DockStatsModel dsm = new DockStatsModel();
    public Button bikesBtn;
    public Button docksBtn;
    public Button mapBtn;
    public Button statsBtn;
    public Button adminBtn;
    public Button logoutBtn;

    /**
     * Creates an information alert window.
     * @param title
     * @param content
     */
    public void newInfoAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(Alert.AlertType.INFORMATION.name());
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Creates a warning alert window
     * @param title
     * @param content
     */
    public void newWarningAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(Alert.AlertType.WARNING.name());
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Crea
     * @param title
     * @param content
     * @return
     */
    public boolean newConfirmationAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Creates a new popup
     * @param fxmlname
     * @param title
     */
    public void newPopup(String fxmlname, String title){
        Stage popup;
        try{
            Parent parent = FXMLLoader.load(getClass().getResource(fxmlname));
            Scene scene = new Scene(parent);

            // lag ny stage
            popup = new Stage();
            popup.setScene(scene);
            popup.setTitle(title);
            popup.setResizable(false);
            popup.show();
        }catch(Exception e){
            System.out.println("Error i popup kontruktør: " + e);
        }
    }

    public void closeWindow(ActionEvent event){
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    public void changeScene(ActionEvent event, String fxmlname) throws Exception{

        Parent parent = FXMLLoader.load(getClass().getResource(fxmlname));
        Scene scene = new Scene(parent);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }


    // main buttons
    @FXML
    void changeToBikeScene(ActionEvent event) throws Exception {
        changeScene(event, "/bike/BikeView.fxml");
    }

    @FXML
    void changeToDockScene(ActionEvent event) throws Exception {
        changeScene(event, "/dock/DockView.fxml");
    }

    @FXML
    void changeToMapScene(ActionEvent event) throws Exception {
        changeScene(event, "/map/MapView.fxml");
    }

    @FXML
    void changeToStatsScene(ActionEvent event) throws Exception {
        changeScene(event, "/stats/StatsView.fxml");
    }

    @FXML
    void changeToAdminScene(ActionEvent event) throws Exception {
        changeScene(event, "/admin/AdminView.fxml");
    }

    @FXML
    void changeToHomeScene(ActionEvent event) throws Exception {
        changeScene(event, "/main/MainView.fxml");
    }

    @FXML
    void logOut(ActionEvent event) throws Exception {
        CurrentAdmin.getInstance().setAdmin(null);
        changeScene(event, "/login/LoginView.fxml");
    }
}
