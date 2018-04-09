package model;



import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

public class BikeStatsModel {

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
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

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

    public static void main(String[] args){
        BikeStatsModel bms = new BikeStatsModel();
        for(int i = 0; i < bms.getRecentCoordinates().size(); i++) {
            double[] doubles = bms.getRecentCoordinates().get(i);
            System.out.println("BikeID: " + doubles[0] + ", x-coordinate: " + doubles[1] + ", y-coordinate: " + doubles[2]);
        }
        System.out.println("Charging lvl: " + bms.getChargLvl(1) + "%");
        System.out.println("Trip nr: " + bms.getTripNr(1));
    }
}
