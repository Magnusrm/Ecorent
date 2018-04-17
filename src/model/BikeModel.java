package model;

import control.Bike;
import control.Dock;
import control.Type;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * The class that handles saving, deleting and editing new bikes to the database.
 */
public class BikeModel {


    /**
     * Checks if a given bike is in the database.
     *
     * @param bikeID        the bike_id that is to be searched for
     * @return true         if the bike exists.
     * @return false        if the bike doesn't exist.
     */
    public boolean bikeExists(int bikeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String existsQuery = "SELECT bike_id FROM bike WHERE bike_id = ? AND active = 1";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(existsQuery);
            preparedStatement.setInt(1, bikeID);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - bikeExists()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    /**

     * Return a bike Object from the database.
     *
     * @param bikeID        the bike_id that is to be searched for in the database.
     * @return bike         the bike object with the data corresponding to the bike_id.
     * @return null         if the method fails.
     */
    public Bike getBike(int bikeID)  {

        Connection connection = null;

        Type type;
        Bike bike;

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;


        String bikeQuery = "SELECT reg_date, price, make, pwr_usg, name FROM type LEFT JOIN bike ON bike.type_id = type.type_id WHERE bike_id = ? AND active = 1";

        String regDate;
        LocalDate localDate;
        double price;
        String make;
        String typeName;
        double pwrUsg;

        try {
            connection = DBCleanup.getConnection();

            if(bikeExists(bikeID)) {
                preparedStatement = connection.prepareStatement(bikeQuery);
                preparedStatement.setInt(1, bikeID);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    regDate = resultSet.getString("reg_date");
                    price = resultSet.getDouble("price");
                    make = resultSet.getString("make");
                    typeName = resultSet.getString("name");
                    pwrUsg = resultSet.getDouble("pwr_usg");
                    localDate = LocalDate.parse(regDate);
                    type = new Type(typeName);
                    bike = new Bike(localDate, price, make, type,pwrUsg);
                    bike.setBikeId(bikeID);
                    bike.setRepairing(isRepairing(bikeID));
                    return bike;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - getBike()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }


    /**
     * Sets the dockID for a bike in the database.
     *
     * @param bikeID            the bike_id of the bike that is to be altered.
     * @param dockID            the dock_id that the bike is stationed at.
     * @return true             if the method is successful.
     * @return false            if the method fails.
     */
    public boolean setDockID(int bikeID, int dockID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String bikeInsert = "UPDATE bike SET dock_id = ? WHERE bike_id = ? AND active = 1";
        try{
            connection = DBCleanup.getConnection();


            if(bikeExists(bikeID)) {
                preparedStatement = connection.prepareStatement(bikeInsert);
                preparedStatement.setInt(1, dockID);
                preparedStatement.setInt(2, bikeID);

                return preparedStatement.executeUpdate() != 0;

            }
        }catch(SQLException e) {
            System.out.println(e.getMessage() + " - setDockID");
        }finally {
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    /**
     * Changes the values of a bike in the database.
     *
     * @param bikeID        the bike_id of the bike that is going to be edited.
     * @param regDate       the edited reg_date.
     * @param price         the edited price.
     * @param make          the edited make.
     * @param dockID        the edited dock_id.
     * @param pwrUsg        the edited price.
     * @param typeName      the edited type.name. This uses the method typeExists to return the correct type_id.
     * @return true         if the edited changes has been saved.
     * @return false        if the changes hasn't been saved.
     */
    public boolean editBike(int bikeID, String regDate, double price, String make, int dockID, double pwrUsg, String typeName) {
        int typeID = TypeModel.typeExists(typeName);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String bikeInsert = "UPDATE bike SET reg_date = ?, price = ?, make = ?, dock_id = ?, pwr_usg = ?, type_id = ? " +
                "WHERE bike_id = ? AND active = 1";
        try{
            connection = DBCleanup.getConnection();
            connection.setAutoCommit(false);


            if(bikeExists(bikeID)) {
                preparedStatement = connection.prepareStatement(bikeInsert);
                preparedStatement.setString(1, regDate);
                preparedStatement.setDouble(2, price);
                preparedStatement.setString(3, make);
                preparedStatement.setInt(4, dockID);
                preparedStatement.setDouble(5, pwrUsg);
                preparedStatement.setInt(6, typeID);
                preparedStatement.setInt(7, bikeID);


                if (preparedStatement.executeUpdate() != 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage() + " - editBike()");
        }finally {
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.setAutoCommit(connection);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    /**
     * "Deletes" bikes that does not have a type.
     *
     * @return true         if the bikes has been deleted.
     * @return false        if the method fails.
     */
    public boolean deleteBikesWhereTypeIsNULL(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteUpdate = "UPDATE bike SET active = 0 WHERE type_id IS NULL";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(deleteUpdate);

            return preparedStatement.executeUpdate() != 0;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - deleteBikesWhereTypeIsNULL()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    /**
     * "Deletes" a bike from the database by setting the active-bit to 0.
     *
     * @param bikeID        the bike_id of the bike that is to be "deleted".
     * @return true         if the bike has been "deleted".
     * @return false        if the method fails.
     */
    public boolean deleteBike(int bikeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteUpdate = "UPDATE bike SET active = 0 WHERE bike_id = ?";
        try{
            connection = DBCleanup.getConnection();

            if(bikeExists(bikeID)) {
                preparedStatement = connection.prepareStatement(deleteUpdate);
                preparedStatement.setInt(1, bikeID);

                return preparedStatement.executeUpdate() != 0;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - deleteBike()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }


    /**
     * Adds a new bike to the database.
     *
     * @param date          the date of witch the bike is registered.
     * @param price         the price of the bike.
     * @param make          the make of the type.
     * @param typeName      the type.name of the type of the bike. This uses the method typeExists to return the correct type_id.
     * @param pwrUsg        the pwr_usage of the bike.
     * @param repair        if the bike is sent to repair or not.
     * @return bikeID       the bike_id that is set by auto increment in the database.
     * @return -1           if the method fails.
     */
    public int addBike(String date, double price, String make, String typeName, double pwrUsg, boolean repair){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int typeID = TypeModel.typeExists(typeName);

        String bikeInsert = "INSERT INTO bike(bike_id, reg_date, price, make, type_id, pwr_usg, repairing, active) VALUES " +
                "(DEFAULT, ?, ?, ?, ?, ?, ?, 1);";
        String maxBikeID = "SELECT MAX(bike_id) FROM bike WHERE active = 1";

        byte rep = 1;
        byte notRep = 0;

        try{
            connection = DBCleanup.getConnection();
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(bikeInsert);
            preparedStatement.setString(1, date);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, make);
            preparedStatement.setInt(4, typeID);
            preparedStatement.setDouble(5, pwrUsg);
            //preparedStatement.setInt(6, dockID);
            if(repair){
                preparedStatement.setByte(6, rep);
            }else{
                preparedStatement.setByte(6, notRep);
            }

            if(preparedStatement.executeUpdate() != 0){
                preparedStatement = connection.prepareStatement(maxBikeID);
                resultSet = preparedStatement.executeQuery();
                connection.commit();
                resultSet.next();
                return resultSet.getInt("MAX(bike_id)");
            }else{
                connection.rollback();
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - addBike()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.setAutoCommit(connection);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    /**
     * Returns an ArrayList of all bikes that is in the database.
     *
     * @return allBikes         an ArrayList of bike objects of all the bikes that are saved in the database.
     * @return null             if the method fails.
     */
    public ArrayList<Bike> getAllBikes(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Bike> allBikes = new ArrayList<Bike>();

        String bikesQuery = "SELECT bike_id FROM bike WHERE active = 1";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(bikesQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                allBikes.add(getBike(resultSet.getInt("bike_id")));
            }
            return allBikes;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getAllBikes()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    /**
     * Returns all the active bikes in the system.
     *
     * @return activeBikes      an ArrayList of the bike_id's of all the active bikes in the system.
     * @return null             if the method fails.
     */
    public ArrayList<Integer> getActiveBikes(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Integer> activeBikes = new ArrayList<>();

        String allBikesQuery = "SELECT DISTINCT bike.bike_id, bike_stats.bike_id, active\n" +
                "FROM bike\n" +
                "    LEFT JOIN bike_stats ON bike_stats.bike_id = bike.bike_id\n" +
                "WHERE (active = 1) AND (bike_stats.bike_id IS NOT NULL)";

        try{
            connection = DBCleanup.getConnection();
            preparedStatement = connection.prepareStatement(allBikesQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                activeBikes.add(resultSet.getInt("bike_id"));
            }
            return activeBikes;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getActiveBikes()");
        }finally{
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    /**
     * Checks if a given bike is repairing.
     *
     * @param bikeID        the bike_id of the bike that is to be checked.
     * @return true         if the bike is sent to repair.
     * @return false        if the bike isn't sent to repair.
     */
    public boolean isRepairing(int bikeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String repairQuery = "SELECT repairing FROM bike WHERE bike_id = ? AND active = 1";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(repairQuery);
            preparedStatement.setInt(1, bikeID);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getByte("repairing") != 0;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getRepair()");
        }finally{
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    /**
     * Checks what value the repair-bit is and changes it.
     *
     * @param bikeID        the bike_id of the bike that is sent to repair.
     * @return true         if the method is successful.
     * @retrun false        if the method fails.
     */
    public boolean changeRepair(int bikeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String repairQuery = "SELECT repairing FROM bike WHERE bike_id = ? AND active = 1";
        String repairUpdate = "UPDATE bike SET repairing = ? WHERE bike_id = ? AND active = 1";

        try{
            connection = DBCleanup.getConnection();
            preparedStatement = connection.prepareStatement(repairQuery);
            preparedStatement.setInt(1, bikeID);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            byte rep = resultSet.getByte("repairing");

            if(rep == 0){
                byte changeRep = 1;
                preparedStatement = connection.prepareStatement(repairUpdate);
                preparedStatement.setByte(1, changeRep);
                preparedStatement.setInt(2, bikeID);
                return preparedStatement.executeUpdate() != 0;
            }else{
                byte changeRep = 0;
                preparedStatement = connection.prepareStatement(repairUpdate);
                preparedStatement.setByte(1, changeRep);
                preparedStatement.setInt(2, bikeID);
                return preparedStatement.executeUpdate() != 0;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - changeRepair");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }
}
