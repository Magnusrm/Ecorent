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
import java.util.concurrent.*;

public class Simulation2 implements Runnable{
    private int id;
    private Thread t;
    private static Semaphore[] sem;

    private static Factory factory = new Factory();
    private static ArrayList<Dock> docks = factory.getDocks();
    private static Random random = new Random();
    private static BikeModel bm = new BikeModel();
    private static BikeStatsModel bts = new BikeStatsModel();
    private static DockStatsModel dsm = new DockStatsModel();
    private static boolean access = true;


    /**
     * Simulation2.java
     * @author Team 007
     * @version 1.0
     * This class creates Threads and simulates data to update bikestats and dockstats.
     */
    public Simulation2(Semaphore[] sem, int id){
        this.id = id;
        this.sem = sem;
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

    /**
     * Simulates movement, charging, docking/undocking and power usages.
     * @param bikeID the Bike id of the bike you want to start a simulation for.
     */
    static void sim(int bikeID){
        int steps = 10;

        while (true) {
            try {
                sem[0].acquire();
                double distance = bts.getDistance(bikeID);
                int trip = bts.getTripNr(bikeID) + 1;
                int batteryLevel = bts.getChargLvl(bikeID);
                ArrayList<double[]> lastPositions = bts.getMostRecentCoordinates();
                factory.updateSystem();
                ArrayList<Bike> bikes = factory.getBikes();
                sem[0].release();

                String time;
                for (Bike b : bikes) {
                    if (b.getBikeId() == bikeID) {
                        for (Dock d : docks) {
                            if (d.getDockID() == b.getDockId()) {
                                //int checkouts = dsm.getCheckouts(d.getDockID()) + 1;
                                time = getNow();
                                sem[1].acquire();
                                dsm.updateDockStats(d.getDockID(), time, b.getPowerUsage() * 0.016666667, 1);
                                bm.setDockID(bikeID, -1);
                                sem[1].release();
                            }
                        }
                    }
                }

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
                while (check == 0) {
                    randomD = randomDock();
                    xDestination = randomD.getxCoordinates();
                    yDestination = randomD.getyCoordinates();
                    distanceChange = distance(xPos, yPos, xDestination, yDestination);
                    if (distanceChange > 10) {
                        check = 1;
                    }
                }

                double xDifference = xDestination - xPos;
                double yDifference = yDestination - yPos;



                for (int i = 0; i < steps; i++) {

                    time = getNow();

                    xPos += xDifference / steps;
                    yPos += yDifference / steps;
                    batteryLevel -= 5;
                    if (batteryLevel < 0) {
                        batteryLevel = 0;
                    }
                    distance += distanceChange / steps;
                    sem[2].acquire();
                    bts.updateStats(time, bikeID, batteryLevel, xPos, yPos, distance, trip);
                    sem[2].release();
                    try {
                        sleep(500);
                    } catch (Exception e) {
                        System.out.println("Error: " + e);
                    }
                }
                sem[3].acquire();
                bm.setDockID(bikeID, randomD.getDockID());
                sem[3].release();
                for (int j = 0; j < 5; j++) {
                    time = getNow();
                    batteryLevel += 10;
                    sem[4].acquire();
                    bts.updateStats(time, bikeID, batteryLevel, xPos, yPos, distance, trip);
                    sem[4].release();
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println("charging sleep interrupted.");
                    }
                }
            } catch(InterruptedException e){
                System.out.println("damn");
            }
        }
    }

    /**
     * looks for a random dock
     * @return A random dock from the database
     */
    public static Dock randomDock(){
        factory.updateSystem();
        docks = factory.getDocks();
        int randomIndex = random.nextInt(docks.size());
        return docks.get(randomIndex);
    }

    /**
     * Gets the current date and time and parses it.
     * @returnThe current date and time parsed to suit the input format of the database.
     */
    public static String getNow() {
        String time;
        LocalDateTime ldt;
        ldt = LocalDateTime.now();
        time = ("" + ldt + "").replaceAll("T", " ");
        time = time.substring(0, time.length() - 4);
        return time;
    }

    /**
     * Calculates the distance between two positions using latitude and longitude.
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

    /*	This function converts decimal degrees to radians						 */
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*	This function converts radians to decimal degrees						 */
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}

class RunSimulation2{

    public static void main(String[] args){
        Semaphore[] sem = new Semaphore[5];
        for (int i = 0; i < sem.length; i++){
            sem[i] = new Semaphore(1);
        }

        Factory factory = new Factory();
        factory.updateSystem();
        ArrayList<Bike> bikes = factory.getBikes();
        System.out.println(bikes.size());
        Simulation2[] simulations = new Simulation2[bikes.size()];
        for (int i = 0; i < bikes.size(); i++){
            simulations[i] = new Simulation2(sem, bikes.get(i).getBikeId());
            simulations[i].start();
        }

    }

}
//'YYYY-MM-DD HH:MM:SS