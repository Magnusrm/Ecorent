package model;

import control.Dock;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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


    public double getTotalPowerUsageOfSystem(){

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        double sum = 0;

        String query = "SELECT dock_id, MAX(total_pwr_usg) FROM dock_stats GROUP BY dock_id";

        try {
            connection = DBCleanup.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                sum += resultSet.getDouble("MAX(total_pwr_usg)");
            }
            return sum;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getTotalPowerUsageOfSystem");
        }finally {
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return sum;
    }





    /**
     * Returns the total number of checkouts at a given dock
     * @param dockID            the dock_id of the dock that is to be checked.
     * @return checkouts        the number of checkouts.
     * @return -1               if the method fails.
     */
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

    /**
     * Inserts a new row of statistics to the database.
     * @param dockID            the dock_id of the dock.
     * @param time              the current time.
     * @param pwrSinceLast      the power used since last (the last minute).
     * @param checkouts         the number of checkouts since last (the last minute).
     * @return true             if the stats are successfully added.
     * @return false            if the method fails.
     */
    public boolean updateDockStats(int dockID, String time, double pwrSinceLast, int checkouts){
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        double currentPower = getTotalPowerUsage(dockID);
        int currentCheckouts = getCheckouts(dockID);

        String powerInsert = "INSERT INTO dock_stats(time, dock_id, total_pwr_usg, checkouts) VALUES " +
                "(?, ?, ?, ?)";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(powerInsert);
            preparedStatement.setString(1, time);
            preparedStatement.setInt(2, dockID);
            preparedStatement.setDouble(3, (currentPower + pwrSinceLast));
            preparedStatement.setInt(4, (currentCheckouts + checkouts));

            return preparedStatement.executeUpdate() != 0;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - updateDockStats()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return false;
    }

    public double[] getWeeklyMaxPowerUsage(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        double[] days = new double[7];

        String day1 = "SELECT MAX(total_pwr_usg), dock_id FROM dock_stats WHERE time >= CURRENT_DATE GROUP BY dock_id";
        String day2 = "SELECT MAX(total_pwr_usg), dock_id FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 1 DAY) " +
                "AND (CURRENT_DATE) GROUP BY dock_id";
        String day3 = "SELECT MAX(total_pwr_usg), dock_id FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 2 DAY) " +
                "AND (CURRENT_DATE - INTERVAL 1 DAY) GROUP BY dock_id";
        String day4 = "SELECT MAX(total_pwr_usg), dock_id FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 3 DAY) " +
                "AND (CURRENT_DATE - INTERVAL 2 DAY) GROUP BY dock_id";
        String day5 = "SELECT MAX(total_pwr_usg), dock_id FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 4 DAY) " +
                "AND (CURRENT_DATE - INTERVAL 3 DAY) GROUP BY dock_id";
        String day6 = "SELECT MAX(total_pwr_usg), dock_id FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 5 DAY) " +
                "AND (CURRENT_DATE - INTERVAL 4 DAY) GROUP BY dock_id";
        String day7 = "SELECT MAX(total_pwr_usg), dock_id FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 6 DAY) " +
                "AND (CURRENT_DATE - INTERVAL 5 DAY) GROUP BY dock_id";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(day1);
            resultSet = preparedStatement.executeQuery();
            double day1Counter = 0;
            while(resultSet.next()){
                day1Counter += resultSet.getDouble("MAX(total_pwr_usg)");
            }
            days[0] = day1Counter;

            preparedStatement = connection.prepareStatement(day2);
            resultSet = preparedStatement.executeQuery();
            double day2Counter = 0;
            while(resultSet.next()){
                day2Counter += resultSet.getDouble("MAX(total_pwr_usg)");
            }
            days[1] = day2Counter;


            preparedStatement = connection.prepareStatement(day3);
            resultSet = preparedStatement.executeQuery();
            double day3Counter = 0;
            while(resultSet.next()){
                day3Counter += resultSet.getDouble("MAX(total_pwr_usg)");
            }
            days[2] = day3Counter;

            preparedStatement = connection.prepareStatement(day4);
            resultSet = preparedStatement.executeQuery();
            double day4Counter = 0;
            while(resultSet.next()){
                day4Counter += resultSet.getDouble("MAX(total_pwr_usg)");
            }
            days[3] = day4Counter;

            preparedStatement = connection.prepareStatement(day5);
            resultSet = preparedStatement.executeQuery();
            double day5Counter = 0;
            while(resultSet.next()){
                day5Counter += resultSet.getDouble("MAX(total_pwr_usg)");
            }
            days[4] = day5Counter;

            preparedStatement = connection.prepareStatement(day6);
            resultSet = preparedStatement.executeQuery();
            double day6Counter = 0;
            while(resultSet.next()){
                day6Counter += resultSet.getDouble("MAX(total_pwr_usg)");
            }
            days[5] = day6Counter;

            preparedStatement = connection.prepareStatement(day7);
            resultSet = preparedStatement.executeQuery();
            double day7Counter = 0;
            while(resultSet.next()){
                day7Counter += resultSet.getDouble("MAX(total_pwr_usg)");
            }
            days[6] = day7Counter;

            return days;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getWeekyPowerUsage()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    public double[] getDailyPowerUsage(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        double[] days = new double[7];

        String day1 = "SELECT dock_id, (MAX(total_pwr_usg) - MIN(total_pwr_usg)) " +
                "AS sum FROM dock_stats WHERE time >= CURRENT_DATE GROUP BY dock_id";
        String day2 = "SELECT dock_id, (MAX(total_pwr_usg) - MIN(total_pwr_usg)) " +
                "AS sum FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 1 DAY) " +
                "AND CURRENT_DATE GROUP BY dock_id";
        String day3 = "SELECT dock_id, (MAX(total_pwr_usg) - MIN(total_pwr_usg)) " +
                "AS sum FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 2 DAY) " +
                "AND (CURRENT_DATE - INTERVAL 1 DAY) GROUP BY dock_id";
        String day4 = "SELECT dock_id, (MAX(total_pwr_usg) - MIN(total_pwr_usg)) " +
                "AS sum FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 3 DAY) " +
                "AND (CURRENT_DATE - INTERVAL 2 DAY) GROUP BY dock_id";
        String day5 = "SELECT dock_id, (MAX(total_pwr_usg) - MIN(total_pwr_usg)) " +
                "AS sum FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 4 DAY) " +
                "AND (CURRENT_DATE - INTERVAL 3 DAY) GROUP BY dock_id";
        String day6 = "SELECT dock_id, (MAX(total_pwr_usg) - MIN(total_pwr_usg)) " +
                "AS sum FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 5 DAY) " +
                "AND (CURRENT_DATE - INTERVAL 4 DAY) GROUP BY dock_id";
        String day7 = "SELECT dock_id, (MAX(total_pwr_usg) - MIN(total_pwr_usg)) " +
                "AS sum FROM dock_stats WHERE time BETWEEN (CURRENT_DATE - INTERVAL 6 DAY) " +
                "AND (CURRENT_DATE - INTERVAL 5 DAY) GROUP BY dock_id";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(day1);
            resultSet = preparedStatement.executeQuery();
            double day1Counter = 0;
            while(resultSet.next()){
                day1Counter += resultSet.getDouble("sum");
            }
            days[0] = day1Counter;

            preparedStatement = connection.prepareStatement(day2);
            resultSet = preparedStatement.executeQuery();
            double day2Counter = 0;
            while(resultSet.next()){
                day2Counter += resultSet.getDouble("sum");
            }
            days[1] = day2Counter;

            preparedStatement = connection.prepareStatement(day3);
            resultSet = preparedStatement.executeQuery();
            double day3Counter = 0;
            while(resultSet.next()){
                day3Counter += resultSet.getDouble("sum");
            }
            days[2] = day3Counter;

            preparedStatement = connection.prepareStatement(day4);
            resultSet = preparedStatement.executeQuery();
            double day4Counter = 0;
            while(resultSet.next()){
                day4Counter += resultSet.getDouble("sum");
            }
            days[3] = day4Counter;

            preparedStatement = connection.prepareStatement(day5);
            resultSet = preparedStatement.executeQuery();
            double day5Counter = 0;
            while(resultSet.next()){
                day5Counter += resultSet.getDouble("sum");
            }
            days[4] = day5Counter;

            preparedStatement = connection.prepareStatement(day6);
            resultSet = preparedStatement.executeQuery();
            double day6Counter = 0;
            while(resultSet.next()){
                day6Counter += resultSet.getDouble("sum");
            }
            days[5] = day6Counter;

            preparedStatement = connection.prepareStatement(day7);
            resultSet = preparedStatement.executeQuery();
            double day7Counter = 0;
            while(resultSet.next()){
                day7Counter += resultSet.getDouble("sum");
            }
            days[6] = day7Counter;

            return days;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getDailyPowerUsage()");
        }finally{
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }

    public ArrayList<int[]> getMaxCheckouts(){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        ArrayList<int[]> checkouts = new ArrayList<>();

        String getCheckouts ="SELECT dock_id, MAX(checkouts) FROM dock_stats GROUP BY dock_id";

        try{
            connection = DBCleanup.getConnection();

            preparedStatement = connection.prepareStatement(getCheckouts);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int[] row = new int[2];
                row[0] = resultSet.getInt("dock_id");
                row[1] = resultSet.getInt("MAX(checkouts)");
                checkouts.add(row);
            }
            return checkouts;
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - getMaxCheckouts()");
        }finally{
            DBCleanup.closeResultSet(resultSet);
            DBCleanup.closeStatement(preparedStatement);
            DBCleanup.closeConnection(connection);
        }
        return null;
    }
}
