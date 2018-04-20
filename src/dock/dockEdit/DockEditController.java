package dock.dockEdit;

import changescene.ChangeScene;
import control.Dock;
import control.Factory;
import dock.dockNew.DockNewController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class DockEditController implements Initializable {
    private Factory factory = new Factory();
    @FXML
    private ComboBox<String> bikeIdComboBox;

    @FXML
    private TextField dockNameField;

    @FXML
    private TextField xCoordField;

    @FXML
    private TextField yCoordField;

    @FXML
    private ComboBox<String> dockNameComboBox;

    @FXML
    private Button saveChangesBtn;

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
    private WebView root;

    private WebEngine engine;

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

            // load map
            engine = root.getEngine();
            engine.load(this.getClass().getResource("/dock/dockNew/newdockmap.html").toExternalForm());
            engine.setJavaScriptEnabled(true);

            root.addEventHandler(MOUSE_CLICKED, e -> {
                setJavaBridge();
            });

            engine.getLoadWorker().stateProperty().addListener(e ->
            {
                setJavaBridge();
            });

            // add dockId's to comboBox
            ObservableList<String> docks = FXCollections.observableArrayList();
            String[] visualized = new String[factory.getDocks().size()];
            for (int i = 0; i < visualized.length; i++) {
                visualized[i] = factory.getDocks().get(i).getName();
            }//end loop
            docks.addAll(visualized);
            dockNameComboBox.setItems(docks);

            dockNameComboBox.getSelectionModel().selectFirst();
        }catch (Exception e){e.printStackTrace();}

    }

    public void setJavaBridge(){
        JSObject window = (JSObject) engine.executeScript("window");
        JavaBridge bridge = new JavaBridge();

        window.setMember("java", bridge);
        engine.executeScript("console.log = function(message)\n" +
                "{\n" +
                "    java.log(message);\n" +
                "};");
    }

    @FXML
    private void zoomIn(){
        engine.executeScript("document.zoomIn();");


        JSObject window = (JSObject) engine.executeScript("window");
        JavaBridge bridge = new JavaBridge();

        window.setMember("java", bridge);
        engine.executeScript("console.log = function(message)\n" +
                "{\n" +
                "    java.log(message);\n" +
                "};");
    }

    @FXML
    private void zoomOut(){
        engine.executeScript("document.zoomOut();");

        JSObject window = (JSObject) engine.executeScript("window");
        JavaBridge bridge = new JavaBridge();

        window.setMember("java", bridge);
        engine.executeScript("console.log = function(message)\n" +
                "{\n" +
                "    java.log(message);\n" +
                "};");
    }


    @FXML
    void saveChanges(ActionEvent event) throws SQLException,ClassNotFoundException{
        String dockName = dockNameComboBox.getValue();
        Dock editDock = new Dock(dockNameField.getText(),Double.parseDouble(xCoordField.getText()), Double.parseDouble(yCoordField.getText()));

        System.out.println("test0");
        if(factory.editDocks(dockName, editDock)){
            System.out.println("test02");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("The info about dock " + dockNameComboBox.getValue() + " is now updated!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Something went wrong!");
            alert.setHeaderText(null);
            alert.setContentText("Cannot connect to system, please check your connection");
            alert.showAndWait();
            System.out.println("test3");
        }

        System.out.println("test4");

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
