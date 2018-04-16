package model;

import control.Dock;

import javax.print.DocFlavor;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * The class that handles saving, deleting and editing of docks to the database.
 */
public class DockModel {

    /**
     * Returns a dock object from the database.
     *
     * @param name          the name of the dock that is to be retrieved from the database.
     * @return dock         a dock object with data corresponding to the name of the dock.
     * @return null         if the method fails.
     */
    public Dock getDock(String name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Dock dock;

        int dockID;
        double xCord;
        double yCord;

        String dockIDQuery = "SELECT dock_id, x_cord, y_cord FROM dock WHERE name = ?";

        try{
            connection = DBCleanup.getConnection();

            if(!dockNameAvailable(name)) {
                preparedStatement = connection.prepareStatement(dockIDQuery);
                preparedStatement.setString(1, name);
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    dockID = resultSet.getInt("dock_id");
                    xCord = resultSet.getDouble("x_cord");
                    yCord = resultSet.getDouble("y_cord");
                    dock = new Dock(name, xCord, yCord);
                    dock.setDockID(dockID);
                    return dock;
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getDock()");
        }finally {
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    /**
     * Checks if coordinates are taken, since we can't have two docks stationed at the same place.
     *
     * @param xCord     the x_cord that is to be checked.
     * @param yCord     the y_cord that is to be checked
     * @return true     if the coordinates are not taken.
     * @return false    if the coordinates are taken.
     */
    public boolean dockCoordinatesAvailable(double xCord, double yCord){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String cordsQuery = "SELECT x_cord, y_cord FROM dock WHERE x_cord = ? AND y_cord = ?";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(cordsQuery);
            preparedStatement.setDouble(1, xCord);
            preparedStatement.setDouble(2, yCord);
            resultSet = preparedStatement.executeQuery();
            return !resultSet.next();

        }catch(SQLException e){
            System.out.println(e.getMessage() + " - dockCoordinatesExists");
        }
        return false;
    }

    /**
     * Checks if a dock name is available or taken in the database.
     *
     * @param name          the name that is to be checked.
     * @return true         if the name is available.
     * @return false        if the name isn't available.
     */
    public boolean dockNameAvailable(String name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String nameQuery = "SELECT name FROM dock WHERE LOWER(name = ?)";
        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(nameQuery);
            preparedStatement.setString(1, name.toLowerCase());
            resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - dockNameExists()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    /**

     * Private method to check if a dock_id is available or taken.
     *
     * @param dockID        the dock_id that is to be checked.
     * @return true         if the dock_id is available.
     * @return false        if the dock_id isn't available.
     */
    private boolean dockIDAvailable(int dockID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String IDQuery = "SELECT dock_id FROM dock WHERE dock_id = ?";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(IDQuery);
            preparedStatement.setInt(1, dockID);
            resultSet = preparedStatement.executeQuery();
            return !resultSet.next();
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - dockIDExists()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    /**

     * Adds a new dock to the database.
     *
     * @param name          the name of the dock.
     * @param xCord         the x_cord of the dock.
     * @param yCord         the y_cord of the dock.
     * @return dockID       the dock_id that is set by auto increment in the database.
     */
    public int addDock(String name, double xCord, double yCord){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String dockInsert = "INSERT INTO dock(dock_id, name, x_cord, y_cord) VALUES(DEFAULT, ?, ?, ?)";
        String maxQuery = "SELECT MAX(dock_id) FROM dock";

        try{
            connection = DBCleanup.getConnection();
            connection.setAutoCommit(false);

            if(dockNameAvailable(name) && dockCoordinatesAvailable(xCord, yCord)) {
                preparedStatement = connection.prepareStatement(dockInsert);
                preparedStatement.setString(1, name);
                preparedStatement.setDouble(2, xCord);
                preparedStatement.setDouble(3, yCord);
                if (preparedStatement.executeUpdate() != 0) {
                    preparedStatement = connection.prepareStatement(maxQuery);
                    resultSet = preparedStatement.executeQuery();
                    connection.commit();
                    resultSet.next();
                    return resultSet.getInt("MAX(dock_id)");
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - addDock()");
        }finally{
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    /**
     * Edits a dock that already is saved to the database.
     *
     * @param dockID        the dock_id of the dock that is to be edited.
     * @param name          the changed name of the dock.
     * @param xCord         the changed x_cord of the dock.
     * @param yCord         the changed y_cord of the dock.
     * @return true         if the dock is successfully edited.
     * @return false        if the method fails.
     */
    public boolean editDock(int dockID, String name, double xCord, double yCord){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String dockInsert = "UPDATE dock SET name = ?, x_cord = ?, y_cord = ? WHERE dock_id = ?";
        try{
            connection = DBCleanup.getConnection();

            if(!dockIDAvailable(dockID)) {
                preparedStatement = connection.prepareStatement(dockInsert);
                preparedStatement.setString(1, name);
                preparedStatement.setDouble(2, xCord);
                preparedStatement.setDouble(3, yCord);
                preparedStatement.setInt(4, dockID);

                return preparedStatement.executeUpdate() != 0;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage() + " - editDock()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    /**
     * Returns the dockID of the dock that a bike is docked at.
     *
     * @param bikeID        the bike_id that is supposed to be checked if is docked.
     * @return dockID       the dock_id of the dock that the bike is docked at.
     */
    public int getDockID(int bikeID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String IDQuery = "SELECT dock_id FROM bike WHERE bike_id = ?";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(IDQuery);
            preparedStatement.setInt(1, bikeID);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("dock_id");
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getDockID()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    /**
     * Deletes a dock from the database.
     *
     * @param name          the name of the dock that is to be deleted.
     * @return true         if the dock is successfully deleted.
     * @return false        if the method fails.
     */
    public boolean deleteDock(String name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteQuery = "DELETE FROM dock WHERE name = ?";

        try{
            connection = DBCleanup.getConnection();

            if(!dockNameAvailable(name)) {
                preparedStatement = connection.prepareStatement(deleteQuery);
                preparedStatement.setString(1, name);
                return preparedStatement.executeUpdate() != 0;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - deleteDock()");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }


    /**
     * Returns an ArrayList of all bikes' bikeID's that is docked at a given dock.
\     *
     * @param name          the name of the dock that is to be checked.
     * @return bikes        an ArrayList of all bike_id's that is docked at that given dock.
     * @return null         if the method fails.
     */
    public ArrayList<Integer> bikesAtDock(String name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Integer> bikes = new ArrayList<Integer>();

        String bikesQuery = "SELECT bike_id FROM bike NATURAL JOIN dock WHERE(dock.name = ?) AND bike.active = 1";

        try{
            connection = DBCleanup.getConnection();

            if(!dockNameAvailable(name)) {
                preparedStatement = connection.prepareStatement(bikesQuery);
                preparedStatement.setString(1, name);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    bikes.add(resultSet.getInt("bike_id"));
                }
                return bikes;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage() + " - bikesAtDock()");
        }finally {
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    /**
     * Returns an ArrayList of all dock Objects that are in the database.
     *
     * @return allDocks         an ArrayList of all dock-objects that are saved in the database.
     * @return null             if the method fails, or there are no docks.
     */
    public ArrayList<Dock> getAllDocks(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Dock> allDocks = new ArrayList<Dock>();

        String docksQuery = "SELECT name FROM dock";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(docksQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                allDocks.add(getDock(resultSet.getString("name")));
            }
            return allDocks;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getAllDocks()");;
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }
}
