package map;

import changescene.MainMethods;
import control.Dock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import model.BikeStatsModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

/**
 * MapController.java
 * @author Team007
 *
 * This is a controller for our map.
 * It visualizes the docks and bikes on the created map.
 */
public class MapController extends MainMethods implements Initializable{

    private BikeStatsModel bsm= new BikeStatsModel();
    private ArrayList<Dock> allDocks;

    @FXML
    private WebView root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        factory.updateSystem();

        engine = root.getEngine();
        engine.load(this.getClass().getResource("/map/googlemap.html").toExternalForm());
        engine.setJavaScriptEnabled(true);

        engine.getLoadWorker().stateProperty().addListener(e -> {
            showDocks();
        });

    }

    /**
     * Method to get all the docks ID and positions
     * @return All the docks and their dockID and positions in a double[][] format.
     */
    private double[][] dockToArray(){
        double[][] res = new double[allDocks.size()][3];
        for (int i = 0; i < res.length; i++) {
            res[i][0] = allDocks.get(i).getDockID();
            res[i][1] = allDocks.get(i).getxCoordinates();
            res[i][2] = allDocks.get(i).getyCoordinates();
        }
        return res;
    }

    /**
     * Method to visualize docks on the map
     */
    public void showDocks() {
        allDocks = factory.getDocks();
        String dockData = arrayToString(dockToArray());
        engine.executeScript("var items = " + dockData + ";" +
                "document.setDockMarkers(items);");
    }

    /**
     * Method to visualize bikes on the map.
     * @param event is an object of ActionEvent.java
     */
    @FXML
    void showBike(ActionEvent event) {

        double bikeXPos = 0;
        double bikeYPos = 0;
        engine.executeScript("document.removeBikeMarkers();");

        ArrayList<double[]> coordinates = bsm.getMostRecentCoordinates();
        for (double[] d : coordinates) {
            bikeXPos = d[1];
            bikeYPos = d[2];
            engine.executeScript("document.createBikeMarker(" + bikeXPos + ", " + bikeYPos + ");");
        }
        engine.executeScript("document.setMapOnAllBikes(document.map);");

    }

    /**
     * Method to convert a double[][] array to string so
     * it can be executed in javascript
     * @param data The double[][] you want to convert
     * @return The text to create a similar two dimentional array in javascript.
     */
    public String arrayToString(double[][] data){
        String res = "[[";
        for (int i = 0; i < data.length; i++){
            for (int j = 0; j < data[i].length; j++){
                res += data[i][j] + ", ";
            }
            res = res.substring(0, res.length() - 2);
            res += "], [";
        }
        res = res.substring(0, res.length() - 3);
        res += "]";
        return res;
    }
}
