package model;

import Control.Bike;
import Control.Type;

import java.sql.*;
import java.time.LocalDate;

public class BikeModel {
    private String driver = "com.mysql.jdbc.Driver";
    private String dbName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true";


    //Method that retrieves a bike from the database as an object
    public Bike getBike(int bikeID)  {

        Connection connection = null;

        Type type;
        Bike bike;

        PreparedStatement getDate = null;
        PreparedStatement getPrice = null;
        PreparedStatement getMake = null;
        PreparedStatement getType = null;
        PreparedStatement getPwr = null;

        String dateQuery = "SELECT reg_date FROM bike WHERE bike_id = ?";
        String priceQuery = "SELECT price FROM bike WHERE bike_id = ?";
        String makeQuery = "SELECT make FROM bike WHERE bike_id = ?";
        String typeQuery = "SELECT name FROM type WHERE type_id IN(SELECT type_id FROM bike WHERE bike_id = ?";
        String pwrQuery = "SELECT pwr_usg FROM bike WHERE bike_id = ?";

        ResultSet rsDate = null;
        ResultSet rsPrice = null;
        ResultSet rsMake = null;
        ResultSet rsType = null;
        ResultSet rsPwr = null;

        String regDate;
        LocalDate localDate;
        double price;
        String make;
        String typeName;
        double pwrUsg;

        try {
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

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

            type = new Type(typeName);
            bike = new Bike(localDate, price, make, type, pwrUsg);

            return bike;
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - getBike()");
        } catch(ClassNotFoundException e){
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

    //Updates the values of a given bike
    public boolean editBike(int bikeID, String regDate, double price, String make, double pwrUsg, String typeName) {
        int typeID = TypeModel.typeExists(typeName);
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String bikeInsert = "UPDATE bike SET reg_date = ?, price = ?, make = ? ,pwr_usg = ?, type_id = ? " +
                "WHERE bike_id = ?;";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(bikeInsert);
            preparedStatement.setString(1, regDate);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, make);
            preparedStatement.setDouble(4, pwrUsg);
            preparedStatement.setDouble(5, typeID);
            preparedStatement.setInt(6, bikeID);

            if(preparedStatement.executeUpdate(bikeInsert) != 0){
                connection.commit();
                return true;
            }else{
                DBCleanup.rollback(connection);
                return false;
            }
        }catch(SQLException e) {
            System.out.println(e.getMessage() + " - editBike()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - editBike()");
        }finally {
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.setAutoCommit(connection);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    public boolean deleteBike(int bikeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteUpdate = "DELETE FROM bike WHERE bike.bike_id = ?";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(deleteUpdate);
            preparedStatement.setInt(1, bikeID);

            if(preparedStatement.executeUpdate() != 0){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - deleteBike()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - deleteBike()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    //Adds a new bike to the database
    public int addBike(String date, double price, String make, String type, double pwrUsg, boolean repairing){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        int typeID = TypeModel.typeExists(type);

        String bikeInsert = "INSERT INTO bike(bike_id, reg_date, price, make, type_id, pwr_usg, repairing) VALUES " +
                "(DEFAULT, ?, ?, ?, ?, ?, ?);";
        String maxBikeID = "SELECT MAX(bike_id) from bike";

        byte rep = 1;
        byte notRep = 0;

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);
            connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(bikeInsert);
            preparedStatement.setString(1, date);
            preparedStatement.setDouble(2, price);
            preparedStatement.setString(3, make);
            preparedStatement.setInt(4, typeID);
            preparedStatement.setDouble(5, pwrUsg);
            if(repairing){
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
                DBCleanup.rollback(connection);
                return -1;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - addBike()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - addBike()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.setAutoCommit(connection);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }
}
