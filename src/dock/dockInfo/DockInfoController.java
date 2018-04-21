package dock.dockInfo;

import changescene.MainMethods;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import control.*;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

public class DockInfoController extends MainMethods implements Initializable {

    @FXML
    private Label nameLbl;

    @FXML
    private Label powerDrawLbl;

    @FXML
    private ComboBox<String> dockNameComboBox;

    @FXML
    private ListView<String> bikeIdListView;

    private WebEngine engine;

    @FXML
    private WebView root;


    //Notice the bikes and docks are converted to String array.
    //This is to simplify the clicking and fetching process.
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try {
            factory.updateSystem();

            engine = root.getEngine();
            engine.load(this.getClass().getResource("/dock/dockNew/newdockmap.html").toExternalForm());
            engine.setJavaScriptEnabled(true);

            // add dockId's to comboBox
            ObservableList<String> docks = FXCollections.observableArrayList();
            String[] visualized2 = new String[factory.getDocks().size()];
            for (int i = 0; i < visualized2.length; i++) {
                visualized2[i] = factory.getDocks().get(i).getName();
            }//end loop
            docks.addAll(visualized2);
            dockNameComboBox.setItems(docks);

            dockNameComboBox.getSelectionModel().selectFirst();
            engine.getLoadWorker().stateProperty().addListener(e ->
            {
                showInfo();
            });

            dockNameComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override //Auto filling info
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    showInfo();
                }//end method
            });
        }catch (Exception e){e.printStackTrace();}
    }

    @FXML
    void showInfo(){

        // add bikeId's to listview
        ObservableList<String> bikes= FXCollections.observableArrayList();
        int[] visualizedInt = factory.dockedBikes(dockNameComboBox.getValue());
        String[] visualized = new String[visualizedInt.length];
        for (int i = 0; i < visualized.length; i++) {
            visualized[i] = "" + visualizedInt[i];
        }//end loop
        bikes.addAll(visualized);
        bikeIdListView.setItems(bikes);

        // add dockName to dockNameLbl
        nameLbl.setText(dockNameComboBox.getValue());

        // add powerDraw to powerDrawLbl
        powerDrawLbl.setText("" + factory.powerUsage(dockNameComboBox.getValue()));
        // show marker on map
        ArrayList<Dock> docks = factory.getDocks();
        for (Dock d : docks){
            if (d.getName().equals(dockNameComboBox.getValue()) ){
                engine.executeScript("document.createMarker1(" + d.getxCoordinates() + ", " + d.getyCoordinates() + ");");
            }
        }

    }
}
