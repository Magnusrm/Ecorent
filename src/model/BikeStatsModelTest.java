package model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BikeStatsModelTest {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    BikeStatsModel instance;

    @BeforeEach
    public void before() {
        instance = new BikeStatsModel();
        connection = DBCleanup.getConnection();
    }


    @AfterEach
    public void after() {
        DBCleanup.closeConnection(connection);
        instance = null;
    }


    /*
     * This test has to be altered to fit the stats that are in the database.
     * The time has to be within a minute of the present time.
     */
    @Test
    public void testGetRecentCoordinates(){
        double[] coordinates = {54, 63.1, 10.1};

        double expResult = coordinates[0] + coordinates[1] + coordinates[2];
        double [] getCoordinates = instance.getRecentCoordinates().get(0);
        double result = getCoordinates[0] + getCoordinates[1] + getCoordinates[2];

        assertEquals(expResult, result);
    }

    /*
     * The bikeID and tripNr in the database has to correspond with the input data in the test.
     */
    @Test
    public void testGetTripNr(){
        int bikeID = 54;

        int expResult = 1;

        int result = instance.getTripNr(bikeID);

        assertEquals(expResult, result);
    }

    /*
     * This test has to be altered to fit the stats that are in the database.
     * The time has to be within a minute of the present time.
     */
    @Test
    public void testGetChargLvl(){
        int bikeID = 54;

        int expResult = 100;

        int result = instance.getChargLvl(bikeID);

        assertEquals(expResult, result);
    }
}
