import model.BikeStatsModel;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.lang.Thread.sleep;

public class Simulation implements Runnable{
    private int id;
    private Thread t;

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
        BikeStatsModel bts = new BikeStatsModel();
        double xBefore = 53.43388;
        double xAfter;
        double yBefore = 10.400313;
        double yAfter;
        double distance = bts.getDistance(bikeID);

        double i = 0;
        LocalDateTime ldt;
        while(true){

            ldt = LocalDateTime.now();
            String time = ("" + ldt + "").replaceAll("T"," ");
            time = time.substring(0, time.length() - 4);

            i -= 0.000021;
            xAfter = xBefore - i;
            yAfter = yBefore;

            bts.updateStats(time, bikeID, 100, xAfter, yAfter, distance + distance(xBefore, yBefore, xAfter, yAfter), 1);

            xBefore = xAfter;
            yBefore = yAfter;


            try{
                sleep(3000);
            } catch(Exception e){
                System.out.println("Error: " + e);
            }

        }
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
        Simulation s = new Simulation(54);
        Simulation s2 = new Simulation(56);
        s.start();
        s2.start();

    }

}


//'YYYY-MM-DD HH:MM:SS