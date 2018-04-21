package model.tests;
import model.DBCleanup;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.*;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * This is a test class for the class DBCleanup
 */
public class DBCleanupTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    DBCleanup instance;


    @BeforeEach
    public void before() {
        instance = new DBCleanup();
        connection = DBCleanup.getConnection();
    }


    @AfterEach
    public void after() {
        DBCleanup.closeConnection(connection);
        instance = null;
    }



    @Test
    public void testCloseResultSet(){
        System.out.println("Testing the method closeResultSet()");

        String query = "SELECT * FROM bike";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            DBCleanup.closeResultSet(resultSet);
            assertTrue(resultSet.isClosed());
        }catch (SQLException e){
            System.out.println(e.getMessage() + " - testCloseResultSet");
        }
    }

    @Test
    public void testCloseStatement(){
        System.out.println("Testing the method closeStatement()");

        String SQL = "SELECT * FROM bike";
        try{
            preparedStatement = connection.prepareStatement(SQL);
            DBCleanup.closeStatement(preparedStatement);
            assertTrue(preparedStatement.isClosed());
        }catch (SQLException e){
            System.out.println(e.getMessage() + " - testCloseStatement");
        }
    }

    @Test
    public void testCloseConnection(){
        System.out.println("Testing the method closeConnection()");

        try{

            DBCleanup.closeConnection(connection);
            assertTrue(connection.isClosed());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testSetAutoCommit(){
        System.out.println("Testing the method setAutoCommit");

        try{
            connection = DriverManager.getConnection("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true");
            connection.setAutoCommit(false);
            DBCleanup.setAutoCommit(connection);
            boolean result = connection.getAutoCommit();
            assertTrue(result);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
