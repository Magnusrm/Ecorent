package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * A cleanup class to handle the creation and closing of connection.
 * Also closes ResultSets and PreparedStatements.
 */
public class DBCleanup {


    /**
     * Uses the property-file to establish connection to the database.
     *
     * @return connection       the connection to the database.
     */
    public static Connection getConnection(){

        ResourceBundle rb = ResourceBundle.getBundle("resources.DBProp");

        try{

            String driver = rb.getString("jdbc.driver");
            if (driver != null) {
                Class.forName(driver);
            }

            String url = rb.getString("jdbc.url");
            String username = rb.getString("jdbc.username");
            String password = rb.getString("jdbc.password");

            return DriverManager.getConnection(url, username, password);
        }catch(ClassNotFoundException | SQLException e){
            System.out.println(e.getMessage() + " - getConnection()");
        }
        return null;
    }

    /**
     * @Author Team 007
     *
     * Closes the ResultSet that is given as parameter.
     *
     * @param rs        the ResultSet that is to be closed.
     */
    public static void closeResultSet(ResultSet rs){
        try{
            if(rs != null){
                rs.close();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + "closeResultSet()");
        }
    }

    /**
     * @Author Team 007
     *
     * Closes the PreparedStatement that is given as parameter.
     *
     * @param st        the PreparedStatement that is to be closed.
     */
    public static void closeStatement(PreparedStatement st){
        try {
            if (st != null) {
                st.close();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + "closeStatement()");
        }
    }

    /**
     * @Author Team 007
     *
     * Closes the connection that is given as parameter.
     *
     * @param con       the connection that is to be closed.
     */
    public static void closeConnection(Connection con){
        try{
            if(con != null){
                con.close();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + "closeConnection()");
        }
    }

    /**
     * @Author Team 007
     *
     * If AutoCommit is turned off, this method turns it back on.
     *
     * @param con       the connection where the AutoCommit is set to false.
     */
    public static void setAutoCommit(Connection con){
        try{
            if(con != null && !con.getAutoCommit()){
                con.setAutoCommit(true);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + "setAutoCommit");
        }
    }
}
