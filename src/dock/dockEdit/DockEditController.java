package dock.dockEdit;

import changescene.MainMethods;
import control.Dock;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

/**
 * DockEditController.java
 * @author Team 007
 * @version 1.0
 *
 * This class handles editing of existing docks and DockEditView.fxml
 */
public class DockEditController  extends MainMethods implements Initializable {

    @FXML
    private TextField dockNameField;

    @FXML
    private TextField xCoordField;

    @FXML
    private TextField yCoordField;

    @FXML
    private ComboBox<String> dockNameComboBox;

    @FXML
    private WebView root;

    private WebEngine engine;

    public class JavaBridge {
        /**
         * This method is used to get a message from JavaScript.
         * @param pos A string in the format "(" + number + ", " + number + ").
         * @return The string.
         */
        public String log(String pos) {
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


        dockNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override //Auto filling info
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Dock dock = null;
                for(Dock d: factory.getDocks())if(newValue.equals(d.getName()))dock = d; //Finding the dock in system
                if(dock != null) { //filling text when found
                    xCoordField.setText("" + dock.getxCoordinates());
                    yCoordField.setText("" + dock.getyCoordinates());
                    dockNameField.setText(dock.getName());
                    //Setting marker
                    engine.executeScript("document.createMarker1(" + xCoordField.getText() + ", " + yCoordField.getText() +
                    ");");
                }//end condition
            }//end method
        });

    }//end method

    /**
     * Sets the console.log() method in javascript to execute the method JavaBridge.log().
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

    /**
     * Saves the changes done in the docks textfields
     * @param event Buttonclick
     * @throws SQLException If something is wrong with the database connection.
     * @throws ClassNotFoundException If there is something with with the combobox's value.
     */
    @FXML
    void saveChanges(ActionEvent event) throws SQLException, ClassNotFoundException{
        String dockName = dockNameComboBox.getValue();
        Dock editDock = new Dock(dockNameField.getText(),Double.parseDouble(xCoordField.getText()), Double.parseDouble(yCoordField.getText()));

        System.out.println("test0");
        if(factory.editDocks(dockName, editDock)){
            newInfoAlert("Success", "The info about dock " + dockNameComboBox.getValue() + " is now updated!");
        }else{
            newInfoAlert("Something went wrong!", "Cannot connect to system, please check your connection");
        }
    }
}
