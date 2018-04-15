package model;

import control.Bike;
import control.Dock;
import control.Type;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class BikeModel {


    /**
     * @Author Team 007
     *
     * Checks if a given bike is in the database.
     * Returns true/false.
     *
     * @param bikeID
     * @return boolean
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
     * @Author Team 007
     *
     * Return a bike Object from the database.
     * Returns null if the method fails.
     *
     * @param bikeID
     * @return Object
     */
    public Bike getBike(int bikeID)  {

        Connection connection = null;

        Type type;
        Bike bike;

        PreparedStatement getDate = null;
        PreparedStatement getPrice = null;
        PreparedStatement getMake = null;
        PreparedStatement getType = null;
        PreparedStatement getPwr = null;
        //PreparedStatement getDockID = null;

        String dateQuery = "SELECT reg_date FROM bike WHERE bike_id = ? AND active = 1";
        String priceQuery = "SELECT price FROM bike WHERE bike_id = ? AND active = 1";
        String makeQuery = "SELECT make FROM bike WHERE bike_id = ? AND active = 1";
        String typeQuery = "SELECT name FROM type WHERE type_id IN(SELECT type_id FROM bike WHERE bike_id = ? AND active = 1)";
        String pwrQuery = "SELECT pwr_usg FROM bike WHERE bike_id = ? AND active = 1";
        //String dockIDQuery = "SELECT dock_id FROM bike WHERE bike_id = ?";

        ResultSet rsDate = null;
        ResultSet rsPrice = null;
        ResultSet rsMake = null;
        ResultSet rsType = null;
        ResultSet rsPwr = null;
        //ResultSet rsDockID = null;

        String regDate;
        LocalDate localDate;
        double price;
        String make;
        String typeName;
        double pwrUsg;
        //int dockID;

        try {
            connection = DBCleanup.getConnection();

            if(bikeExists(bikeID)) {
                getDate = connection.prepareStatement(dateQuery);
                getDate.setInt(1, bikeID);
                rsDate = getDate.executeQuery();
                rsDate.next();
                regDate = rsDate.getString("reg_date");
                localDate = LocalDate.parse(regDate);

                getPrice = connection.prepareStatement(priceQuery);
                getPrice.setInt(1, bikeID);
                rsPrice = getPrice.executeQuery();
                rsPrice.next();
                price = rsPrice.getDouble("price");

                getMake = connection.prepareStatement(makeQuery);
                getMake.setInt(1, bikeID);
                rsMake = getMake.executeQuery();
                rsMake.next();
                make = rsMake.getString("make");

                getType = connection.prepareStatement(typeQuery);
                getType.setInt(1, bikeID);
                rsType = getType.executeQuery();
                rsType.next();
                typeName = rsType.getString("name");

                getPwr = connection.prepareStatement(pwrQuery);
                getPwr.setInt(1, bikeID);
                rsPwr = getPwr.executeQuery();
                rsPwr.next();
                pwrUsg = rsPwr.getDouble("pwr_usg");

                /*getDockID = connection.prepareStatement(dockIDQuery);
                getDockID.setInt(1, bikeID);
                rsDockID = getDockID.executeQuery();
                rsDockID.next();
                dockID = rsDockID.getInt("dock_id");*/

                type = new Type(typeName);
                bike = new Bike(localDate, price, make, type,pwrUsg);
                bike.setBikeId(bikeID);
                bike.setRepairing(isRepairing(bikeID));
                //bike.setDockId(dockID);
                return bike;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - getBike()");
        }finally{
            DBCleanup.closeStatement(getDate);
            DBCleanup.closeStatement(getPrice);
            DBCleanup.closeStatement(getMake);
            DBCleanup.closeStatement(getType);
            DBCleanup.closeStatement(getPwr);

            DBCleanup.closeResultSet(rsDate);
            DBCleanup.closeResultSet(rsPrice);
            DBCleanup.closeResultSet(rsMake);
            DBCleanup.closeResultSet(rsType);
            DBCleanup.closeResultSet(rsPwr);

            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    /**
     * @Author Team 007
     *
     * Changes the values of a bike in the database.
     * Returns true/false.
     *
     * @param bikeID
     * @param regDate
     * @param price
     * @param make
     * @param dockID
     * @param pwrUsg
     * @param typeName
     * @return boolean
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
     * @author Team 007
     * Edits the dock of a bike
     * @param bikeID
     * @param dockID
     * @return
     */
    public boolean editBikeDock(int bikeID, int dockID) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String bikeInsert = "UPDATE bike SET dock_id = ? WHERE bike_id = ? AND active = 1";
        try{
            connection = DBCleanup.getConnection();
            connection.setAutoCommit(false);


            if(bikeExists(bikeID)) {
                preparedStatement = connection.prepareStatement(bikeInsert);
                preparedStatement.setInt(1, dockID);
                preparedStatement.setInt(2, bikeID);


                if (preparedStatement.executeUpdate() != 0) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage() + " - editBikeDock");
        }finally {
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.setAutoCommit(connection);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    /**
     *
     * "Deletes" bikes that does not have a type.
     * Returns true/false.
     *
     * @return boolean
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
     * @Author Team 007
     *
     * "Deletes" a bike from the database by setting the active-bit to 0.
     * Returns true/false.
     *
     * @param bikeID
     * @return boolean
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
     * @Author Team 007
     *
     * Adds a new bike to the database.
     * Returns the bikeID.
     * Returns -1 if the method fails.
     *
     * @param date
     * @param price
     * @param make
     * @param type
     * @param pwrUsg
     * @param repair
     * @return int
     */
    public int addBike(String date, double price, String make, String type, double pwrUsg, boolean repair){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int typeID = TypeModel.typeExists(type);

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
     * @Author Team 007
     *
     * Returns an ArrayList of all bikes that is in the database.
     * Returns null if method fails.
     *
     * @return ArrayList
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
     * @Author Team 007
     *
     * Checks if a given bike is repairing.
     * Returns true/false.
     *
     * @param bikeID
     * @return boolean
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
     * @Author Team 007
     *
     * Checks what value the repair-bit is and changes it.
     * Returns true/false.
     *
     * @param bikeID
     * @return boolean
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
    public static void main(String[] args){
        BikeModel bikeModel = new BikeModel();
        if(bikeModel.isRepairing(68)){
            System.out.println("Hei");
        }
    }
}
