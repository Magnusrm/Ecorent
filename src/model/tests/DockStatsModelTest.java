package model.tests;

import model.DBCleanup;
import model.DockStatsModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * The class that tests DockStatsModel
 */
public class DockStatsModelTest {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    DockStatsModel instance;

    @BeforeEach
    public void before() {
        instance = new DockStatsModel();
        connection = DBCleanup.getConnection();
    }


    @AfterEach
    public void after() {
        DBCleanup.closeConnection(connection);
        instance = null;
    }

    /*
     * This test has to be altered to fit the expected result from the database.
     */
    @Test
    public void testGetTotalPowerUsage(){

        double expResult = 4;

        double result = instance.getTotalPowerUsage(1);

        assertEquals(expResult, result);
    }

    /*
     * This test has to be altered to fit the expected result from the database.
     */
    @Test
    public void testGetCheckouts(){

        double expResult = 0;

        double result = instance.getCheckouts(1);

        assertEquals(expResult, result);
    }

}
