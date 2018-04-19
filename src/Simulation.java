import control.Bike;
import control.Dock;
import control.Factory;
import model.BikeModel;
import model.BikeStatsModel;
import model.DockStatsModel;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Simulation implements Runnable{
    private int id;
    private Thread t;

    private static Factory factory = new Factory();
    private static ArrayList<Dock> docks;
    private static Random random = new Random();
    private static BikeModel bm = new BikeModel();
    private static BikeStatsModel bts = new BikeStatsModel();
    private static DockStatsModel dsm = new DockStatsModel();

    public Simulation(int id){
       this.id = id;
    }

    public void run(){
        sim(id);
    }

    public void start () {
        if (t == null) {
            t = new Thread (this);
            t.start ();
        }
    }

    static void sim(int bikeID){
        int steps = 10;

        while (true) {
            double distance = bts.getDistance(bikeID);
            int trip = bts.getTripNr(bikeID) + 1;
            int batteryLevel = bts.getChargLvl(bikeID);
            ArrayList<double[]> lastPositions = bts.getMostRecentCoordinates();
            double xPos = 0;
            double yPos = 0;
            for (double[] d : lastPositions) {
                if (d[0] == bikeID) {
                    xPos = d[1];
                    yPos = d[2];
                }
            }
            int check = 0;
            double xDestination = 0;
            double yDestination = 0;
            double distanceChange = 0;
            Dock randomD = null;
            while(check == 0) {
                randomD = randomDock();
                xDestination = randomD.getxCoordinates();
                yDestination = randomD.getyCoordinates();
                distanceChange = distance(xPos, yPos, xDestination, yDestination);
                if (distanceChange > 10){
                    check = 1;
                }
            }

            double xDifference = xDestination - xPos;
            double yDifference = yDestination - yPos;

            LocalDateTime ldt;

            bm.setDockID(bikeID, -1);
            for (int i = 0; i < steps; i++) {

                ldt = LocalDateTime.now();
                String time = ("" + ldt + "").replaceAll("T", " ");
                time = time.substring(0, time.length() - 4);

                xPos += xDifference / steps;
                yPos += yDifference / steps;
                batteryLevel -= 5;
                if (batteryLevel < 0){
                    batteryLevel = 0;
                }
                distance += distanceChange / steps;

                bts.updateStats(time, bikeID, batteryLevel, xPos, yPos, distance, trip);

                try {
                    sleep(3000);
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            }
            bm.setDockID(bikeID, randomD.getDockID());


            for (int j = 0; j < 5; j++){
                ldt = LocalDateTime.now();
                String time = ("" + ldt + "").replaceAll("T", " ");
                time = time.substring(0, time.length() - 4);
                batteryLevel += 10;
                bts.updateStats(time, bikeID, batteryLevel, xPos, yPos, distance, trip);
                try {
                    sleep(1000);
                } catch(InterruptedException e){
                    System.out.println("charging sleep interrupted.");
                }
            }
        }
    }

    public static Dock randomDock(){
        factory.updateSystem();
        docks = factory.getDocks();
        int randomIndex = random.nextInt(docks.size());
        return docks.get(randomIndex);
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

}

class RunSimulation{

    public static void main(String[] args){
        Factory factory = new Factory();
        factory.updateSystem();
        ArrayList<Bike> bikes = factory.getBikes();
        System.out.println(bikes.size());
        Simulation[] simulations = new Simulation[bikes.size()];
        for (int i = 0; i < bikes.size(); i++){
            simulations[i] = new Simulation(bikes.get(i).getBikeId());
            simulations[i].start();
            try{
                sleep(500);
            } catch(InterruptedException e){
                System.out.println("simulation main sleep interrupted.");
            }
        }

    }

}


//'YYYY-MM-DD HH:MM:SS