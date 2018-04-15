package model;



import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class BikeStatsModel {

    /**
     * @Author Team 007
     *
     * Returns an ArrayList of the most recent latitudes and longitudes + corresponding bikeID's.
     *
     * @return ArrayList
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
     * @Author Team 007
     *
     * Returns the trip number of a given bike.
     *
     * @param bikeID
     * @return int
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
                System.out.print("trip number: " + tripNr);
                return tripNr;
            }else{
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getTripNr()");
            return -1;
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }

    }

    /**
     * @Author Team 007
     *
     * Returns the most recent battery percentage of a given bike.
     *
     * @param bikeID
     * @return int
     */
    public int getChargLvl(int bikeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BikeModel bikeModel = new BikeModel();

        String chargLvlQuery = "SELECT charg_lvl FROM bike_stats WHERE time >= (now() - INTERVAL 1 MINUTE) AND bike_id = ?";

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
            return -1;
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }

    }

    public double getDistance(int bikeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        BikeModel bikeModel = new BikeModel();

        String distanceQuery = "SELECT distance FROM bike_stats WHERE time >= (now() - INTERVAL 1 MINUTE) AND bike_id = ?";

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
     * @Author Team 007
     *
     * Since all stats are to be saved to the database, this adds new and updated stats to the database.
     * Returns true/false.
     *
     * @param time
     * @param bikeID
     * @param chargLvl
     * @param xCord
     * @param yCord
     * @param distance
     * @param tripNr
     * @return boolean
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
