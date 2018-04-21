package map;

import changescene.ChangeScene;
import control.Bike;
import control.Dock;
import control.Factory;
import control.Type;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import loginAdm.CurrentAdmin;
import model.BikeModel;
import model.BikeStatsModel;
import netscape.javascript.JSObject;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

public class MapController implements Initializable{

    private Factory myFactory = new Factory();
    private BikeStatsModel bsm= new BikeStatsModel();
    private ArrayList<Dock> allDocks;

    @FXML
    private WebView root;

    private WebEngine engine;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myFactory.updateSystem();

        engine = root.getEngine();
        engine.load(this.getClass().getResource("/map/googlemap.html").toExternalForm());
        engine.setJavaScriptEnabled(true);

        engine.getLoadWorker().stateProperty().addListener(e -> {
            showDocks();
        });

    }

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
                "document.setDockMarkers(items);");
    }

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
        CurrentAdmin.getInstance().setAdmin(null);
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
}
