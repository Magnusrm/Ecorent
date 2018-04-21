package dock.dockNew;

import changescene.ChangeScene;
import changescene.MainMethods;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import loginAdm.CurrentAdmin;

import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;
import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;
import static javafx.scene.input.MouseEvent.MOUSE_PRESSED;

public class DockNewController extends MainMethods implements Initializable{

    @FXML
    private WebView root;

    private WebEngine engine;

    @FXML
    private TextField dockNameField;

    @FXML
    private TextField xCoordField;

    @FXML
    private TextField yCoordField;

    public class JavaBridge {
        /**
         * This method is used to get a message from JavaScript.
         * @param pos A string in the format "(" + number + ", " + number + ").
         * @return The string.
         */
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

            root.addEventHandler(MOUSE_CLICKED, e -> {
                setJavaBridge();
            });

            engine.getLoadWorker().stateProperty().addListener(e ->
            {
                setJavaBridge();
            });

        }catch (Exception e){e.printStackTrace();}
    }

    /**
     * Sets the console.log() method in javascript to execute the method JavaBridge.log()
     * We found this has to be set anew after we zoom or move the map.
     */
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
    }

    @FXML
    private void zoomOut(){
        engine.executeScript("document.zoomOut();");
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
               changeScene(event, "/dock/DockView.fxml");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Something went wrong!");
                alert.setHeaderText(null);
                alert.setContentText("Dock is not saved, make sure to fill out the form in the given format.");
                alert.showAndWait();
                changeScene(event, "/dock/DockNew/DockNewView.fxml");
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
}
