package model;
import control.Bike;
import control.Repair;
import control.Type;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RepairModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    RepairModel instance;

    @BeforeAll
    public void before() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - before() in RepairModelTest");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " - before() in RepairModelTest");
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
        instance = new RepairModel();
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testSendRepair(){
        System.out.println("Testing the method sendRepair()");

        int bikeID = 1;
        String dateSent = "2018-03-22";
        String description = "Broken wheel";

        int expResult = 1;
        int result = instance.sendRepair(bikeID, dateSent, description);
        assertEquals(expResult, result);
    }

    @Test
    public void testReturnRepair(){
        System.out.println("Testing the method returnRepair()");

        int repairID = 1;
        String dateReceived = "2018-03-23";
        String rDescription = "Fixed wheel";
        double price = 2000;


        assertTrue(instance.returnRepair(repairID, dateReceived, rDescription, price));
    }

    @Test
    public void testGetRepair(){
        System.out.println("Testing the method getRepair()");

        int repairID = 1;
        String dateSent = "2018-03-22";
        String description = "Broken wheel";
        String dateReceived = "2018-03-23";
        String rDescription = "Fixed wheel";
        double price = 2000;
        int bikeID = 1;


        Repair expResult = new Repair(dateSent, description, dateReceived, rDescription, price, bikeID);
        expResult.setRepairId(repairID);
        Repair result = instance.getRepair(repairID);
        assertEquals(expResult, result);
    }
}
