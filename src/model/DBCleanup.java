package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.ResourceBundle;

public class DBCleanup {

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

    public static void closeResultSet(ResultSet rs){
        try{
            if(rs != null){
                rs.close();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + "closeResultSet()");
        }
    }

    public static void closeStatement(PreparedStatement st){
        try {
            if (st != null) {
                st.close();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + "closeStatement()");
        }
    }

    public static void closeConnection(Connection con){
        try{
            if(con != null){
                con.close();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + "closeConnection()");
        }
    }


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
