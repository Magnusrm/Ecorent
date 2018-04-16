package model;



import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * The class that handles saving and retrieving statistics about a bike.
 */
public class BikeStatsModel {

    /**
     * Returns an ArrayList of the most recent latitudes and longitudes + corresponding bikeID's.
     *
     * @return coordinates      an ArrayList of double[] with the most recent latitudes and longitudes + their corresponding bike_id's.
     */
    public ArrayList<double[]> getRecentCoordinates(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<double[]> coordinates = new ArrayList<double[]>();

        String cordsQuery = "SELECT bike_id, x_cord, y_cord FROM bike_stats WHERE time >= (now() - INTERVAL 1 MINUTE)";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(cordsQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                double[] row = new double[3];
                int row0 = resultSet.getInt("bike_id");
                row[0] = (double) row0;
                row[1] = resultSet.getDouble("x_cord");
                row[2] = resultSet.getDouble("y_cord");
                coordinates.add(row);
            }
            return coordinates;

        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getRecentCoordinates()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }


    /**
     * Retrieves the most recent coordinates of all bikes.
     *
     * @return recentCords          an ArrayList of double[] with the most recent x- and y-coordinate + the corresponding bikeID.
     * @return null                 if the method fails.
     */
    public ArrayList<double[]> getMostRecentCoordinates() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<double[]> recentCords = new ArrayList<>();

        String recentQuery = "SELECT bs.bike_id, bs.x_cord, bs.y_cord FROM bike_stats bs JOIN " +
                "(SELECT bike_id, MAX(time) AS maxtime FROM bike_stats GROUP BY bike_id) gbd " +
                "ON bs.bike_id = gbd.bike_id AND bs.time = gbd.maxtime JOIN bike " +
                "ON bs.bike_id = bike.bike_id WHERE active = 1";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement= connection.prepareStatement(recentQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                double[] row = new double[3];
                row[0] = (double) resultSet.getInt("bs.bike_id");
                row[1] = resultSet.getDouble("bs.x_cord");
                row[2] = resultSet.getDouble("bs.y_cord");
                recentCords.add(row);
            }

            return recentCords;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getMostRecentCoordinates()");
        }finally {
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }


    /**
     * Returns the trip number of a given bike.
     *
     * @param bikeID        the bike_id of the bike that is to be searched for in the database.
     * @return tripNr       the number of trips that bike has taken.
     * @return -1           if the method fails.
     */
    public int getTripNr(int bikeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BikeModel bikeModel = new BikeModel();

        int tripNr;
        String tripQuery = "SELECT MAX(trip_number) FROM bike_stats WHERE bike_id = ?";

        try{
            connection = DBCleanup.getConnection();

            if(bikeModel.bikeExists(bikeID)) {
                preparedStatement = connection.prepareStatement(tripQuery);
                preparedStatement.setInt(1, bikeID);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                tripNr = resultSet.getInt("MAX(trip_number)");
                return tripNr;
            }else{
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getTripNr()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    /**
     * Returns the most recent battery percentage of a given bike.
     *
     * @param bikeID        the bike_id of the bike that is to be searched for.
     * @return chargLvl     the current charg_lvl of that bike.
     * @return -1           if the method fails.
     */
    public int getChargLvl(int bikeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BikeModel bikeModel = new BikeModel();

        String chargLvlQuery = "SELECT bs.bike_id, bs.charg_lvl FROM bike_stats bs " +
                "JOIN (SELECT bike_id, MAX(time) AS maxtime FROM bike_stats GROUP BY bike_id) gbd " +
                "ON bs.bike_id = gbd.bike_id AND bs.time = gbd.maxtime JOIN bike ON bs.bike_id = bike.bike_id " +
                "WHERE active = 1 AND bike.bike_id = ?";

        try{
            connection = DBCleanup.getConnection();

            if(bikeModel.bikeExists(bikeID)){
                preparedStatement = connection.prepareStatement(chargLvlQuery);
                preparedStatement.setInt(1, bikeID);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return resultSet.getInt("charg_lvl");
            }else{
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getChargLvl()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    /**
     * Shows the total distance travelled by a given bike
     *
     * @param bikeID        the bike_id of the bike that is to be searched for
     * @return distance     the total distance travelled
     * @return -1           if the method fails
     */
    public double getDistance(int bikeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BikeModel bikeModel = new BikeModel();

        String distanceQuery = "SELECT bs.bike_id, bs.distance FROM bike_stats bs " +
                "JOIN (SELECT bike_id, MAX(time) AS maxtime FROM bike_stats GROUP BY bike_id) gbd " +
                "ON bs.bike_id = gbd.bike_id AND bs.time = gbd.maxtime JOIN bike " +
                "ON bs.bike_id = bike.bike_id WHERE active = 1 AND bike.bike_id = ?";

        try{
            connection = DBCleanup.getConnection();

            if(bikeModel.bikeExists(bikeID)){
                preparedStatement = connection.prepareStatement(distanceQuery);
                preparedStatement.setInt(1, bikeID);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return resultSet.getDouble("distance");
            }else{
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getDistance");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    /**
     * Since all stats are to be saved to the database, this adds new and updated stats to the database.
     *
     * @param time          when the stats are updated.
     * @param bikeID        which bike the stats are referring to.
     * @param chargLvl      the current charg_lvl of the bike.
     * @param xCord         the current x_cord of the bike.
     * @param yCord         the current y_cord of the bike.
     * @param distance      the total distance the bike has travelled.
     * @param tripNr        how many trips the bike has taken.
     * @return true         if the update is successful.
     * @return false        if the update failed.
     */
    public boolean updateStats(String time, int bikeID, int chargLvl, double xCord, double yCord, double distance, int tripNr){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BikeModel bikeModel = new BikeModel();

        String insertCoordinates = "INSERT INTO bike_stats(time, bike_id, charg_lvl, x_cord, y_cord, distance, trip_number) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?)";
        try{
            connection = DBCleanup.getConnection();

            if(bikeModel.bikeExists(bikeID)) {
                preparedStatement = connection.prepareStatement(insertCoordinates);
                preparedStatement.setString(1, time);
                preparedStatement.setInt(2, bikeID);
                preparedStatement.setInt(3, chargLvl);
                preparedStatement.setDouble(4, xCord);
                preparedStatement.setDouble(5, yCord);
                preparedStatement.setDouble(6, distance);
                preparedStatement.setInt(7, tripNr);
                if(preparedStatement.executeUpdate() != 0){
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - updateStats()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }
}
