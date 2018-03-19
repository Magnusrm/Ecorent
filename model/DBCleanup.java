package model;

import java.sql.*;

public class DBCleanup {

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

    public static void rollback(Connection con){
        try{
            if(con != null){
                con.rollback();
            }
        }catch(SQLException e){
            System.out.println(e.getMessage() + "rollback()");
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
