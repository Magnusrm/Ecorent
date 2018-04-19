package model;

import control.*;
import control.RepairReturned;
import control.RepairSent;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * The class that handles saving and retrieving repairs to the database.
 */
public class RepairModel {

    /**
     * Sends the first part of the repair to the database.
     *
     * @param bikeID            bike_id of the bike that the repair should be registered on.
     * @param dateSent          the date that the bike is sent to repair.
     * @param bDescription      the description of what to be done to the bike.
     * @return                  the repair_id of the repair that is registered.
     */
    public int sendRepair(int bikeID, String dateSent, String bDescription){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String sendInsert = "INSERT INTO repair(repair_id, date_sent, before_desc, date_recieved, after_desc, price, bike_id)" +
                "VALUES(DEFAULT, ?, ?,NULL, NULL, NULL, ?)";
        String maxQuery = "SELECT MAX(repair_id) FROM repair";
        try{
            connection = DBCleanup.getConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(sendInsert);
            preparedStatement.setString(1, dateSent);
            preparedStatement.setString(2, bDescription);
            preparedStatement.setInt(3, bikeID);

            if(preparedStatement.executeUpdate() != 0){
                preparedStatement = connection.prepareStatement(maxQuery);
                resultSet = preparedStatement.executeQuery();
                connection.commit();
                resultSet.next();
                return resultSet.getInt("MAX(repair_id)");
            } else{
                connection.rollback();
                return -1;
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage() + " - sendRepair()");
        }finally {
            DBCleanup.setAutoCommit(connection);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }


    /**
     * Adds the second part of the repair to the database.
     *
     * @param repairID          what repair_id the second part belongs to.
     * @param dateReceived      when the bike is returned from repair.
     * @param aDescription      the description of what is done.
     * @param price             the cost of the repair.
     * @return                  if the second part of the repair is successfully saved.
     */
    public boolean returnRepair(int repairID, String dateReceived, String aDescription, double price)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String returnInsert = "UPDATE repair SET date_recieved = ?, after_desc = ?, price = ? WHERE repair_id = ?";
        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(returnInsert);
            preparedStatement.setString(1, dateReceived);
            preparedStatement.setString(2, aDescription);
            preparedStatement.setDouble(3, price);
            preparedStatement.setInt(4, repairID);
            if(preparedStatement.executeUpdate() != 0){
                return true;
            }else{
                return false;
            }

        }catch(SQLException e) {
            System.out.println(e.getMessage() + " - returnRepair()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    /**
     * Returns an arraylist of all repairID's that is sent but not returned.
     *
     * @return             an ArrayList of all repairs that is sent but not returned.
     */
    public ArrayList<Integer> getRepairIDs(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Integer> repIDs = new ArrayList<>();

        String IDQuery = "SELECT repair_id FROM repair WHERE date_recieved IS NULL";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(IDQuery);

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                repIDs.add(resultSet.getInt("repair_id"));
            }
            return repIDs;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getRepairIDs()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    /**
     * Returns an ArrayList of all repair objects that are both sent and returned.
     *
     * @return           an ArrayList of repair objects.
     */
    public ArrayList<RepairReturned> getRepairsReturned(){
        Connection connection = null;

        PreparedStatement preparedStatement = null;


        ResultSet resultSet = null;


        String dateSent;
        String beforeDesc;
        String dateReceived;
        String afterDesc;
        double price;
        int bikeID;
        int repairID;

        RepairReturned repair;
        ArrayList<RepairReturned> repairs = new ArrayList<>();

        String repQuery = "SELECT repair_id, date_sent, before_desc, date_recieved, after_desc, price, bike_id FROM repair WHERE date_recieved IS NOT NULL";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(repQuery);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                repairID = resultSet.getInt("repair_id");
                dateSent = resultSet.getString("date_sent");
                beforeDesc = resultSet.getString("before_desc");
                dateReceived = resultSet.getString("date_recieved");
                afterDesc = resultSet.getString("after_desc");
                price = resultSet.getDouble("price");
                bikeID = resultSet.getInt("bike_id");

                repair = new RepairReturned(dateSent, beforeDesc, dateReceived, afterDesc, price, bikeID);
                repair.setRepairId(repairID);
                repairs.add(repair);
            }
            return repairs;
        }catch (SQLException e){
            System.out.println(e.getMessage() + " - getRepair()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }



    /**
     * Returns a repair Object from the database.
     *
     * @param repairID          the repair_id of the repair that is to be returned.
     * @return                  a repair object.
     */
    public RepairSent getRepair(int repairID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String dateSent;
        String beforeDesc;
        double price;
        int bikeID;

        RepairSent repair = null;

        String repairQuery = "SELECT date_sent, before_desc, date_recieved, after_desc, price, bike_id FROM repair WHERE repair_id = ?";


        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(repairQuery);
            preparedStatement.setInt(1, repairID);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                dateSent = resultSet.getString("date_sent");
                beforeDesc = resultSet.getString("before_desc");
                price = resultSet.getDouble("price");
                bikeID = resultSet.getInt("bike_id");
                repair = new RepairSent(dateSent, beforeDesc,bikeID);
                repair.setRepairId(repairID);
                repair.setBikeId(bikeID);
            }
            return repair;
        }catch (SQLException e){
            System.out.println(e.getMessage() + " - getRepair()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    /**
     * Gets the total cost of all repairs to be used in statistical analysis.
     * @return          the cost of all repairs.
     */
    public double getPriceOfAllRepairs(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        double sum = 0;

        String priceQuery = "SELECT price FROM repair";

        try {
            connection = DBCleanup.getConnection();
            preparedStatement = connection.prepareStatement(priceQuery);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                sum += resultSet.getDouble("price");
            }
            return sum;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getPriceOfAllRepairs");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return sum;
    }
}
