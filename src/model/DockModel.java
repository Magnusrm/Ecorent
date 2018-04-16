package model;

import control.Dock;

import javax.print.DocFlavor;
import java.sql.*;
import java.util.ArrayList;

public class DockModel {

    /**
     * @Author Team 007
     *
     * Returns a dock object from the database.
     *
     * @param name
     * @return Object
     */
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
            connection = DBCleanup.getConnection();

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

            dock = new Dock(name, xCord, yCord);
            dock.setDockID(dockID);
            return dock;

        }catch(SQLException e){
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
     * @Author Team 007
     *
     * Checks by name if a dock exists in the database.
     * Returns true/false.
     *
     * @param name
     * @return boolean
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
     * @Author Team 007
     *
     * Private method to check by dockID if a dock exists.
     * Returns true/false.
     *
     * @param dockID
     * @return boolean
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
     * @Author Team 007
     *
     * Adds a new dock to the database.
     * Returns the dockID that is set in the database.
     * Returns -1 if the dock already is in the database, or if the method failed.
     *
     * @param name
     * @param xCord
     * @param yCord
     * @return int
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
     * @Author Team 007
     *
     * Edits a dock that already is saved to the database.
     * Returns true/false.
     *
     * @param dockID
     * @param name
     * @param xCord
     * @param yCord
     * @return boolean
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
     * @Author Team 007
     *
     * Returns the dockID of the dock that a bike is docked at.
     * Returns -1 if the bike is not docked.
     *
     * @param bikeID
     * @return int
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
     * @Author Team 007
     *
     * Deletes a dock from the database.
     * Returns true/false.
     *
     * @param name
     * @return boolean
     */
    public boolean deleteDock(String name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String deleteQuery = "DELETE FROM dock WHERE name = ?";

        try{
            connection = DBCleanup.getConnection();

            if(dockNameAvailable(name)) {
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
     * @Author Team 007
     *
     * Returns an ArrayList of all bikes' bikeID's that is docked at a given dock.
     * Returns null if method fails.
     *
     * @param name
     * @return ArrayList
     */
    public ArrayList<Integer> bikesAtDock(String name){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<Integer> bikes = new ArrayList<Integer>();

        String bikesQuery = "SELECT bike_id FROM bike NATURAL JOIN dock WHERE(dock.name = ?)";

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
     * @Author Team 007
     *
     * Returns an ArrayList of all dock Objects that are in the database.
     * Returns null if method fails.
     *
     * @return ArrayList
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
