package model;

import control.Dock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * The class that handles saving and retrieving statistics about the docking stations
 */
public class DockStatsModel {

    /**
     * Returns the total power usage of a dock
     * @param dockID            the dock_id of the dock that is searched for.
     * @return total_pwr_usg    the power usage of the dock.
     * @return -1               if the method fails.
     */
    public double getTotalPowerUsage(int dockID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DockModel dm = new DockModel();

        String usageQuery = "SELECT total_pwr_usg FROM dock_stats NATURAL JOIN dock WHERE active = 1 AND dock_id = ?";

        try{
            connection = DBCleanup.getConnection();
            if(!dm.dockIDAvailable(dockID)) {
                preparedStatement = connection.prepareStatement(usageQuery);
                preparedStatement.setInt(1, dockID);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return resultSet.getDouble("total_pwr_usg");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getTotalPowerUsage()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    public int getCheckouts(int dockID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        DockModel dm = new DockModel();

        String checkoutsQuery = "SELECT checkouts FROM dock_stats NATURAL JOIN dock WHERE active = 1 AND dock_id = ?";

        try{
            connection = DBCleanup.getConnection();
            if(!dm.dockIDAvailable(dockID)){
                preparedStatement = connection.prepareStatement(checkoutsQuery);
                preparedStatement.setInt(1, dockID);
                resultSet = preparedStatement.executeQuery();
                resultSet.next();
                return resultSet.getInt("checkouts");
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getCheckouts()");
        }finally{
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return -1;
    }

    /*public boolean updateDockStats(int dockID){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String powerInsert = "INSERT INTO dock_stats(time, dock_id, total_pwr_usg, checkouts) VALUES " +
                "(?, ?"
    }*/
}
