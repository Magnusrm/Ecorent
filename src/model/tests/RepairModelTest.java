package model.tests;
import control.Bike;
import control.*;
import control.Type;
import model.DBCleanup;
import model.RepairModel;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * This is a test class for the class RepairModel
 */
public class RepairModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    RepairModel instance;

    private int max = 13; //Change this to (the highest repair ID in the DB + 1)
    private int bikeId = 69; //ID of the test bike
    //Information about the test repair
    private String dateSent = "2018-04-15";
    private String beforeDescription = "Broken handle";
    private String dateReceived = "2018-04-15";
    private String afterDescription = "Fixed handle";
    double price = 2000;



    @BeforeEach
    public void before() {
        instance = new RepairModel();
        connection = DBCleanup.getConnection();
    }


    @AfterEach
    public void after() {
        DBCleanup.closeConnection(connection);
        instance = null;
    }

    @Test
    public void testSendRepair(){
        System.out.println("Testing the method sendRepair()");

        int bikeID = bikeId;
        String date = dateSent;
        String description = beforeDescription;

        int expResult = max; //This needs to be the next number in
        int result = instance.sendRepair(bikeID, date, description);
        assertEquals(expResult, result);
    }

    @Test
    public void testReturnRepair(){
        System.out.println("Testing the method returnRepair()");

        int repairID = max;
        String date = dateReceived;
        String description = afterDescription;
        double pr = price;


        assertTrue(instance.returnRepair(repairID, date, description, pr));
    }

    @Test
    public void testGetRepair(){
        System.out.println("Testing the method getRepair()");

        int repairID = max;
        String dateS = dateSent;
        String descriptionBefore = beforeDescription;
        String dateR = dateReceived;
        String descriptionAfter = afterDescription;
        double pr = price;
        int bikeID = bikeId;


        RepairSent expResult = new RepairSent(dateS,descriptionBefore,bikeID);
        expResult.setRepairId(repairID);
        RepairSent result = instance.getRepair(repairID);
        assertEquals(expResult, result);
    }
}
