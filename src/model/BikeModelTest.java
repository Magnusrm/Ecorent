package model;
import control.Bike;
import control.Type;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BikeModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    BikeModel instance;

    @BeforeEach
    public void before() {
        instance = new BikeModel();
        connection = DBCleanup.getConnection();
    }


    @AfterEach
    public void after() {
        DBCleanup.closeConnection(connection);
        instance = null;
    }

    @Test
    public void testAddBike(){
        System.out.println("Testing the method addBike()");

        String date = "2018-03-20";
        LocalDate regDate = LocalDate.parse(date);
        double price = 1000;
        String make = "DBS";
        String typeName = "Racer";
        Type type = new Type(typeName);
        double pwrUsg = 100;
        boolean repairing = false;
        int dockID = 1;

        int expResult = 55; //Must be changed according to the self-incrementing bike_id in the DB
        int result = instance.addBike(date, price, make, typeName, dockID, pwrUsg, repairing);

        assertEquals(expResult, result);
    }

    @Test
    public void testGetBike(){
        System.out.println("Testing the method getBike()");

        int bikeID = 55; //Must match the expResult above
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
    public void testEditBike(){
        System.out.println("Testing the method editBike()");

        int bikeID = 55; //This must also match the expResult from addBike()
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

        instance.editBike(1, date, price, make, dockID, pwrUsg, typeName);

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