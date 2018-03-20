package model;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BikeModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    AdminModel instance;

    @BeforeAll
    public void before() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true");
            Class.forName("com.mysql.jdbc.Driver");
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - before() in BikeModelTest");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - before() in BikeModelTest");
        }
    }

    @AfterAll
    public void after(){
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @BeforeEach
    public void setUp(){
        instance = new AdminModel();
    }

    @AfterEach
    public void tearDown(){
        instance = null;
    }