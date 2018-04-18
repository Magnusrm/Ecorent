package dock.dockNew;

import changescene.ChangeScene;
import control.Dock;
import control.Factory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mapTest.WebMap;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ResourceBundle;

public class DockNewController implements Initializable{
    private Factory factory = new Factory();

    @FXML
    private WebView root;

    private WebEngine engine;

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

    public class JavaBridge {

        public String log(String pos) {
            System.out.println(pos);
            String[] data = pos.split(", ");
            String xValue = data[0].substring(1);
            String yValue = data[1].substring(0, data[1].length() - 1);
            xCoordField.setText(xValue);
            yCoordField.setText(yValue);
            return pos;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            factory.updateSystem();

            engine = root.getEngine();
            engine.load(this.getClass().getResource("newdockmap.html").toExternalForm());
            engine.setJavaScriptEnabled(true);


            engine.getLoadWorker().stateProperty().addListener(e ->
            {
                JSObject window = (JSObject) engine.executeScript("window");
                JavaBridge bridge = new JavaBridge();

                window.setMember("java", bridge);
                engine.executeScript("console.log = function(message)\n" +
                        "{\n" +
                        "    java.log(message);\n" +
                        "};");
            });

        }catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void createNewDockConfirm(ActionEvent event){ // created a new dock

        try{
            System.out.println(xCoordField.getText() + "   " + yCoordField.getText());
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
