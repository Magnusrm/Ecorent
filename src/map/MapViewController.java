package map;

import changescene.ChangeScene;
import control.Dock;
import control.Factory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class MapViewController implements Initializable{

    private Factory myFactory = new Factory();
    private ArrayList<Dock> allDocks;

    @FXML
    private Button showBikeBtn;

    @FXML
    private TextField bikeIdField;

    @FXML
    private WebView root;

    private WebEngine engine;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myFactory.updateSystem();

        engine = root.getEngine();
        root.getEngine().load(this.getClass().getResource("/mapTest/googlemap.html").toExternalForm());
        engine.setJavaScriptEnabled(true);


        engine.getLoadWorker().stateProperty().addListener(e -> {
            showDocks();
        });

    }

    public double[][] dockPos = {
            {0, 63.426505, 10.393597},
            {1, 63.427859, 10.387157},
            {2, 63.430663, 10.392245},
            {3, 63.433388, 10.400313}
    };

    private double[][] dockToArray(){
        double[][] res = new double[allDocks.size()][3];
        for (int i = 0; i < res.length; i++) {
            res[i][0] = allDocks.get(i).getDockID();
            res[i][1] = allDocks.get(i).getxCoordinates();
            res[i][2] = allDocks.get(i).getyCoordinates();
        }
        return res;
    }

    public void showDocks() {
        allDocks = myFactory.getDocks();
        String dockData = arrayToString(dockToArray());
        engine.executeScript("var items = " + dockData + ";" +
                "document.updateMarkers(items);");
    }

    @FXML
    void showBike(ActionEvent event) {

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

    public static void main(String args[]){
        MapViewController myMapViewController = new MapViewController();
        myMapViewController.showDocks();
    }

}
