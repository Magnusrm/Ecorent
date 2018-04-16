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

public class MapViewController implements Initializable{

    private Factory myFactory = new Factory();
    private BikeStatsModel bsm= new BikeStatsModel();
    private ArrayList<Dock> allDocks;
    private ArrayList<Bike> allBikes;

    private ExecutorService executor = Executors.newFixedThreadPool(1);

    private int i = 0;

    private Runnable worker = new WorkerThread("" + i);

    @FXML
    private Button showBikeBtn;

    @FXML
    private TextField bikeIdField;

    @FXML
    private WebView root;

    private WebEngine engine;

    class RunnableDemo implements Runnable {
        private Thread t;
        private String threadName;
        private double bikeX;
        private double bikeY;
        private double xDifference;
        private double yDifference;
        private int bikeID;
        private double distance;
        private int tripNr = 0;
        private double distanceChange;
        private int charge_lvl;

        public RunnableDemo(String threadName, double bikeX, double bikeY, double xDifference, double yDifference,
                            int bikeId, double distance, int tripNr, double distanceChange, int charge_lvl) {
            this.threadName = threadName;
            this.bikeX = bikeX;
            this.bikeY = bikeY;
            this.xDifference = xDifference;
            this.yDifference = yDifference;
            this.distance = distance;
            this.tripNr = tripNr;
            this.distanceChange = distanceChange;
            this.charge_lvl = charge_lvl;
        }

        RunnableDemo(String name, double bikeX, double bikeY, double xDifference, double yDifference) {
            threadName = name;
            this.bikeX = bikeX;
            this.bikeY = bikeY;
            this.xDifference = xDifference;
            this.yDifference = yDifference;

            System.out.println("Creating " +  threadName );
        }

        public void run (){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    System.out.println("Running " + threadName);
                    if (tripNr == 0) {
                        engine.executeScript("document.updateBikeMarker(" + bikeX + ", " + bikeY + ", " + xDifference + ", " + yDifference + ");");
                    } else{
                        updateBikeToDatabase(bikeID, charge_lvl, bikeX, bikeY, distance,
                                            tripNr, distanceChange, xDifference, yDifference);
                    }
                    System.out.println("Thread " + threadName + " exiting.");
                }
            });
        }

        public void start() {
            System.out.println("Starting " +  threadName );
            if (t == null) {
                t = new Thread (this, threadName);
                t.start();
            }
        }
    }

    class JavaBridge {

        public String log(String pos) {
            System.out.println("okokokok");
            return pos;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        myFactory.updateSystem();

        engine = root.getEngine();
        engine.load(this.getClass().getResource("/mapTest/googlemap.html").toExternalForm());
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
                "document.setMarkers(items);");
    }

    /**
     * @param lat1 x coordinate 1
     * @param lon1 y coordinate 1
     * @param lat2 x coordinate 2
     * @param lon2 y coordinate 2
     * @return distance between position 1 and 2 in meters.
     */
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 111.189577 * 1000;

        return dist;
    }

    /*::	This function converts decimal degrees to radians						 :*/
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*::	This function converts radians to decimal degrees						 :*/
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    public void moveBikeToDock(int bikeId, Dock d){
        allBikes = myFactory.getBikes();
        allDocks = myFactory.getDocks();
        ArrayList<double[]> recentCoordinates= bsm.getRecentCoordinates();
        Bike b = new Bike(LocalDate.now(), 0, "", new Type(""), 0);
        double[] bikePos = {allDocks.get(0).getxCoordinates(), allDocks.get(0).getyCoordinates()};
        int tripNr = bsm.getTripNr(bikeId) + 1;
        int charg_lvl = 100;
        double distance = 0;

        if (tripNr > 1){
            charg_lvl = bsm.getChargLvl(bikeId);
            distance = bsm.getDistance(bikeId);
        }

        for (Bike aBike : allBikes) {
            if (aBike.getBikeId() == bikeId) {
                b = aBike;
            }
        }
        for (double[] recentPos : recentCoordinates){
            if (recentPos[0] == bikeId) {
                bikePos[0] = recentPos[1];
                bikePos[1] = recentPos[2];
            }
        }
        double xDifference = d.getxCoordinates() - bikePos[0];
        double yDifference = d.getyCoordinates() - bikePos[1];
        if (Math.sqrt(xDifference*xDifference) < 0.0000002 || Math.sqrt(yDifference*yDifference) < 0.0000002){
            return;
        }
        double distanceChange = distance(bikePos[0], bikePos[1], d.getxCoordinates(), d.getyCoordinates()) / 10;
/*
        Task task = new Task<Void>() {
            @Override public Void call() {
                engine.executeScript("document.updateBikeMarker(" + bikePos[0] + ", " + bikePos[1] + ", " + xDifference + ", " + yDifference + ");");
                return null;
            }
        };
        new Thread(task).start();
        */
/*
        new Thread(new Runnable() {
            @Override public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("kjøre script");
                        engine.executeScript("document.updateBikeMarker(" + bikePos[0] + ", " + bikePos[1] + ", " + xDifference + ", " + yDifference + ");");
                        System.out.println("script kjørt");
                    }
                });
            }
            }).start();
            */
        /*
        RunnableDemo animer = new RunnableDemo("Thread1", bikePos[0], bikePos[1], xDifference, yDifference);
        animer.start();*/
        engine.executeScript("document.updateBikeMarker(" + bikePos[0] + ", " + bikePos[1] + ", " + xDifference + ", " + yDifference + ");");
        RunnableDemo data = new RunnableDemo("Thread2", bikePos[0], bikePos[1], xDifference, yDifference,
                                                bikeId, distance, tripNr, distanceChange, charg_lvl);
        data.start();

    }



    public void updateBikeToDatabase(int bikeId, int charg_lvl, double posX, double posY, double distance,
                                     int tripNr, double distanceChange, double xDifference, double yDifference) {
        double x = posX;
        double y = posY;
        int battery = charg_lvl;
        double travelDistance = distance;
        for (int i = 0; i < 10; i++){
            x += xDifference / 10;
            y += yDifference / 10;
            distance += distanceChange;
            battery --;
           // bsm.updateStats(LocalDate.now().toString(), bikeId, battery, x, y, travelDistance, tripNr);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("sleep interrupted");
            }
            System.out.println("\n" + "interval: " + i + "\n");
        }
    }


    @FXML
    void showBike(ActionEvent event) {
        Random random = new Random();
        allDocks = myFactory.getDocks();
        allBikes = myFactory.getBikes();

        int bikeId = Integer.parseInt(bikeIdField.getText());
        Bike b = allBikes.get(0);
        for (Bike aBike : allBikes){
            if (aBike.getBikeId() == bikeId){
                b = aBike;
            }
        }
        Dock d = allDocks.get(0);
        int i = 1;
        while(i == 1){
            int randomDock = random.nextInt(allDocks.size());

            if (b.getDockId() != randomDock){
                i = 0;
                d = allDocks.get(randomDock);
            }
        }
        moveBikeToDock(bikeId, d);
        BikeModel bm = new BikeModel();
        //bm.editBikeDock(, d.getDockID());
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




}
