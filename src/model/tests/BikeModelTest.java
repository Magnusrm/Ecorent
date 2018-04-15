package model.tests;
import control.Bike;
import control.Type;
import model.BikeModel;
import model.DBCleanup;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * This is a test class for the class BikeModel
 */
public class BikeModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    BikeModel instance;
    private final int BIKEID = 68;

    @Override
    public boolean equals(Object o){
        if (o == null) { throw new IllegalArgumentException("The object you are comparing cannot be null"); }
        if (!(o instanceof Bike)) {
            return false;
        }

        Bike b = (Bike) o;

        return (((Bike) o).getBikeId() == b.getBikeId() && ((Bike) o).getMake().equals(b.getMake()) &&
                ((Bike) o).getPrice() == b.getPrice() && ((Bike) o).getPowerUsage() == b.getPowerUsage() &&
                ((Bike) o).getBuyDate().equals(b.getBuyDate()) && ((Bike) o).isRepairing() == b.isRepairing());

    }

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
        double price = 1000;
        String make = "DBS";
        String typeName = "Racer";
        double pwrUsg = 0.36;
        boolean repairing = false;

        int expResult = BIKEID; //Must be changed according to the self-incrementing bike_id in the DB
        int result = instance.addBike(date, price, make, typeName, pwrUsg, repairing);

        assertEquals(expResult, result);
    }

    @Test
    public void testGetBike(){
        System.out.println("Testing the method getBike()");

        String date = "2018-03-20";
        LocalDate regDate = LocalDate.parse(date);
        double price = 1000;
        String make = "DBS";
        Type type = new Type("Racer");
        double pwrUsg = 0.36;

        Bike expResult = new Bike(regDate, price, make, type, pwrUsg);
        expResult.setBikeId(BIKEID);
        Bike result = instance.getBike(BIKEID);
        assertEquals(expResult, result);
    }
    @Test
    public void testEditBike(){
        System.out.println("Testing the method editBike()");

        String date = "2018-03-20";
        LocalDate regDate = LocalDate.parse(date);
        double price = 1001;
        String make = "DBS";
        String typeName = "Landevei";
        Type type = new Type(typeName);
        double pwrUsg = 0.36;
        int dockID = 1;

        instance.editBike(BIKEID, date, price, make, dockID, pwrUsg, typeName);

        Bike expResult = new Bike(regDate, price, make, type, pwrUsg);
        expResult.setBikeId(BIKEID);
        Bike result = instance.getBike(BIKEID);
        assertEquals(expResult, result);
    }

    @Test
    public void testChangeRepair(){
        System.out.println("Testing the method changeRepair()");

        instance.changeRepair(BIKEID);

        assertTrue(instance.isRepairing(BIKEID));
    }


    @Test
    public void testDeleteBike(){
        System.out.println("Testing the method deleteBike()");

        instance.deleteBike(BIKEID);

        assertNull(instance.getBike(BIKEID));
    }
}