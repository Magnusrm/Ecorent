package model;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.*;


public class DBCleanupTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    DBCleanup instance;

    @BeforeAll
    public void before() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - before() in DBCleanupTest");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " - before() in DBCleanupTest");
        }
    }

    @AfterAll
    public void after() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @BeforeEach
    public void setUp() {
        instance = new DBCleanup();
    }

    @AfterEach
    public void tearDown() {
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
            System.out.println(e.getMessage());
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
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testCloseConnection(){
        System.out.println("Testing the method closeConnection()");

        try{
            connection = DriverManager.getConnection("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true");
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
