package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBCleanup {

    public static Connection getConnection(){
        File file = new File("C:/Users/Sander/eclipse-workspace//Project task/src/DBProps");

        try(FileInputStream fileInputStream = new FileInputStream(file)) {

            Properties properties = new Properties();
            properties.load(fileInputStream);

            String driver = properties.getProperty("jdbc.driver");
            if (driver != null) {
                Class.forName(driver);
            }

            String url = properties.getProperty("jdbc.url");
            String username = properties.getProperty("jdbc.username");
            String password = properties.getProperty("jdbc.password");

            return DriverManager.getConnection(url, username, password);
        }catch(IOException | SQLException | ClassNotFoundException e){
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
