package model;
import Control.Bike;
import Control.Type;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BikeModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    BikeModel instance;

    @BeforeAll
    public void before() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - before() in BikeModelTest");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " - before() in BikeModelTest");
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
        instance = new BikeModel();
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testGetBike(){
        System.out.println("Testing the method getBike()");

        int bikeID = 1;
        String date = "2018-03-20";
        LocalDate regDate = LocalDate.parse(date);
        double price = 1000;
        String make = "DBS";
        //int typeID = 1;
        Type type = new Type("Racer");
        int dockID = 1;
        //double pwrUsg = 100;
        //boolean repairing = false;

        Bike expResult = new Bike(regDate, price, make, type, dockID) ;
        Bike result = instance.getBike(bikeID);
        assertEquals(expResult, result);
    }

    @Test
    public void testAddBike(){
        System.out.println("Testing the method addBike()");

        int bikeID = 2;
        String date = "2018-03-20";
        LocalDate regDate = LocalDate.parse(date);
        double price = 1000;
        String make = "DBS";
        String typeName = "Racer";
        Type type = new Type(typeName);
        double pwrUsg = 100;
        boolean repairing = false;
        int dockID = 1;

        instance.addBike(date, price, make, typeName, pwrUsg, repairing);

        Bike expResult = new Bike(regDate, price, make, type, dockID);
        Bike result = instance.getBike(bikeID);
        assertEquals(expResult, result);
    }

    @Test
    public void testEditBike(){
        System.out.println("Testing the method editBike()");

        int bikeID = 2;
        String date = "2018-03-20";
        LocalDate regDate = LocalDate.parse(date);
        double price = 1001;
        String make = "DBS";
        //int typeID = 1;
        String typeName = "Racer";
        Type type = new Type("Racer");
        int dockID = 1;
        double pwrUsg = 100;
        boolean repairing = false;

        instance.editBike(1, date, price, make, pwrUsg, typeName);

        Bike expResult =new Bike(regDate, price, make, type, dockID);
        Bike result = instance.getBike(bikeID);
        assertEquals(expResult, result);
    }

    @Test
    public void testDeleteBike(){
        System.out.println("Testing the method deleteBike()");

        int bikeID = 2;

        instance.deleteBike(bikeID);

        Bike expResult = null;
        Bike result = instance.getBike(bikeID);
    }
}