package model;

import control.Dock;

import java.sql.*;
import java.util.ArrayList;

public class DockModel {
    private String driver = "com.mysql.jdbc.Driver";
    private String dbName = "jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true";

    //Returns a given dock from the database
    public Dock getDock(String name){
        Connection connection = null;

        Dock dock = null;

        PreparedStatement getDockID = null;
        PreparedStatement getXCord = null;
        PreparedStatement getYCord = null;

        ResultSet rsDockID = null;
        ResultSet rsXCord = null;
        ResultSet rsYCord = null;

        int dockID;
        double xCord;
        double yCord;

        String dockIDQuery = "SELECT dock_id FROM dock WHERE name = ?";
        String xCordQuery = "SELECT x_cord FROM dock WHERE name = ?";
        String yCordQuery = "SELECT y_cord FROM dock WHERE name = ?";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            getDockID = connection.prepareStatement(dockIDQuery);
            getDockID.setString(1, name);
            rsDockID = getDockID.executeQuery();
            rsDockID.next();
            dockID = rsDockID.getInt("dock_id");

            getXCord = connection.prepareStatement(xCordQuery);
            getXCord.setString(1, name);
            rsXCord = getXCord.executeQuery();
            rsXCord.next();
            xCord = rsXCord.getDouble("x_cord");

            getYCord = connection.prepareCall(yCordQuery);
            getYCord.setString(1, name);
            rsYCord = getYCord.executeQuery();
            rsYCord.next();
            yCord = rsYCord.getDouble("y_cord");

            dock = new Dock(name, /*pwrUsg,*/ xCord, yCord);
            return dock;

        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getDock()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - getDock()");
        }finally {
            DBCleanup.closeResultSet(rsDockID);
            DBCleanup.closeResultSet(rsXCord);
            DBCleanup.closeResultSet(rsYCord);

            DBCleanup.closeStatement(getDockID);
            DBCleanup.closeStatement(getXCord);
            DBCleanup.closeStatement(getYCord);

            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    public boolean editDock(int dockID, String name, double xCord, double yCord)throws  SQLException, ClassNotFoundException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String dockInsert = "UPDATE dock SET name = ?, x_cord = ?, y_cord = ? WHERE dock_id = ?";
        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(dockInsert);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, xCord);
            preparedStatement.setDouble(3, yCord);
            preparedStatement.setInt(4, dockID);

            if(preparedStatement.executeUpdate() != 0){
                return true;
            }else{
                return false;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage() + " - editDock()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - editDock()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }


    public boolean deleteDock(int dockID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteQuery = "DELETE FROM dock WHERE dock_id = ?";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, dockID);
            if(preparedStatement.executeUpdate() != 0){
                return true;
            }else{
                return false;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - deleteDock()");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - deleteDock()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    //Returns an ArrayList of bikeID's that are docked at a certain docking station.
    public ArrayList<Integer> bikesAtDock(int dockID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Integer> bikes = new ArrayList<Integer>();

        String bikesQuery = "SELECT bike_id FROM bike NATURAL JOIN dock WHERE(dock_id = ?)";

        try{
            connection = DriverManager.getConnection(dbName);
            Class.forName(driver);

            preparedStatement = connection.prepareStatement(bikesQuery);
            preparedStatement.setInt(1, dockID);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                bikes.add(resultSet.getInt("bike_id"));
            }
            return bikes;
        }catch (SQLException e){
            System.out.println(e.getMessage() + " - bikesAtDock()");
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage() + " - bikesAtDock()");
        }finally {
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }
}
