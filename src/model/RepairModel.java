package model;

import control.Repair;

import java.sql.*;
import java.util.ArrayList;

public class RepairModel {

    /**
     * @Author Team 007
     *
     * Sends the first part of the repair to the database.
     * Returns the repairID that is set in the database.
     * Returns -1 if method fails.
     *
     * @param bikeID
     * @param dateSent
     * @param bDescription
     * @return int
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
     * @Author Team 007
     *
     * Adds the second part of the repair to the database.
     * Returns true/false.
     *
     * @param repairID
     * @param dateReceived
     * @param aDescription
     * @param price
     * @return boolean
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
     * @Author Team 007
     *
     * Returns a repair Object from the database.
     *
     * @param repairID
     * @return Object
     */
    public Repair getRepair(int repairID){
        Connection connection = null;

        PreparedStatement getDateSent = null;
        PreparedStatement getBeforeDesc = null;
        PreparedStatement getDateReceived = null;
        PreparedStatement getAfterDesc = null;
        PreparedStatement getPrice = null;
        PreparedStatement getBikeID = null;

        ResultSet rsDateSent = null;
        ResultSet rsBeforeDesc = null;
        ResultSet rsDateReceived = null;
        ResultSet rsAfterDesc = null;
        ResultSet rsPrice = null;
        ResultSet rsBikeID = null;

        String dateSent;
        String beforeDesc;
        String dateReceived;
        String afterDesc;
        double price;
        int bikeID;

        Repair repair = null;

        String dateSentQuery = "SELECT date_sent FROM repair WHERE repair_id = ?";
        String beforeDescQuery = "SELECT before_desc FROM repair WHERE repair_id = ?";
        String dateReceivedQuery = "SELECT date_recieved FROM repair WHERE repair_id = ?";
        String afterDescQuery = "SELECT after_desc FROM repair WHERE repair_id = ?";
        String priceQuery = "SELECT price FROM repair WHERE repair_id = ?";
        String bikeIDQuery = "SELECT bike_id FROM repair WHERE repair_id = ?";

        try{
            connection = DBCleanup.getConnection();

            getDateSent = connection.prepareStatement(dateSentQuery);
            getDateSent.setInt(1, repairID);
            rsDateSent = getDateSent.executeQuery();
            rsDateSent.next();
            dateSent = rsDateSent.getString("date_sent");

            getBeforeDesc = connection.prepareStatement(beforeDescQuery);
            getBeforeDesc.setInt(1, repairID);
            rsBeforeDesc = getBeforeDesc.executeQuery();
            rsBeforeDesc.next();
            beforeDesc = rsBeforeDesc.getString("before_desc");

            getDateReceived = connection.prepareStatement(dateReceivedQuery);
            getDateReceived.setInt(1, repairID);
            rsDateReceived = getDateReceived.executeQuery();
            rsDateReceived.next();
            dateReceived = rsDateReceived.getString("date_recieved");

            getAfterDesc = connection.prepareStatement(afterDescQuery);
            getAfterDesc.setInt(1, repairID);
            rsAfterDesc = getAfterDesc.executeQuery();
            rsAfterDesc.next();
            afterDesc = rsAfterDesc.getString("after_desc");

            getPrice = connection.prepareStatement(priceQuery);
            getPrice.setInt(1, repairID);
            rsPrice = getPrice.executeQuery();
            rsPrice.next();
            price = rsPrice.getDouble("price");

            getBikeID = connection.prepareStatement(bikeIDQuery);
            getBikeID.setInt(1, repairID);
            rsBikeID = getBikeID.executeQuery();
            rsBikeID.next();
            bikeID = rsBikeID.getInt("bike_id");

            repair = new Repair(dateSent, beforeDesc, price, bikeID);
            repair.setRepairId(repairID);
            repair.setBikeId(bikeID);
            return repair;

        }catch (SQLException e){
            System.out.println(e.getMessage() + " - getRepair()");
        }finally{
            DBCleanup.closeStatement(getDateSent);
            DBCleanup.closeStatement(getBeforeDesc);
            DBCleanup.closeStatement(getDateReceived);
            DBCleanup.closeStatement(getAfterDesc);
            DBCleanup.closeStatement(getPrice);
            DBCleanup.closeStatement(getBikeID);

            DBCleanup.closeResultSet(rsDateSent);
            DBCleanup.closeResultSet(rsBeforeDesc);
            DBCleanup.closeResultSet(rsDateReceived);
            DBCleanup.closeResultSet(rsAfterDesc);
            DBCleanup.closeResultSet(rsPrice);
            DBCleanup.closeResultSet(rsBikeID);

            DBCleanup.closeConnection(connection);
        }
        return null;
    }
}

