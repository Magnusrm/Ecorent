package model;
import control.Bike;
import control.Dock;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


public class DockModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    DockModel instance;

    int DOCKID = 10;



    @BeforeEach
    public void before() {
        instance = new DockModel();
        connection = DBCleanup.getConnection();
    }


    @AfterEach
    public void after() {
        DBCleanup.closeConnection(connection);
        instance = null;
    }

    @Test
    public void testGetDock(){
        System.out.println("Testing the method getDock()");

        String name = "testdock";
        double xCord = 63.426405;
        double yCord = 10.393597;

        Dock expResult = new Dock(name, xCord, yCord);
        Dock result = instance.getDock(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testDockNameAvailable(){
        System.out.println("Testing the method dockNameExists()");

        String dockName = "TESTDOCK";

        assertFalse(instance.dockNameAvailable(dockName));
    }

    //The expected result has to match the auto-incremented dock_id in the database
    @Test
    public void testAddDock(){
        System.out.println("Testing the method addDock()");

        String dockName = "testdock2";
        double xCord = 2.1;
        double yCord = 2.2;

        int expResult = DOCKID;
        int result = instance.addDock(dockName, xCord, yCord);
        assertEquals(expResult, result);
    }

    //The dockID has to match the auto-incremented dock_id in the database
    @Test
    public void testEditDock(){
        System.out.println("Testing the method editDock()");

        int dockID = DOCKID;
        String dockName = "testdock2";
        double xCord = 2.0;
        double yCord = 2.0;

        if(instance.editDock(dockID, dockName, xCord, yCord)) {
            Dock expResult = new Dock(dockName, xCord, yCord);
            Dock result = instance.getDock(dockName);
            assertEquals(expResult, result);
        }
    }

    @Test
    public void testDockCoordinatesAvailable(){
        System.out.println("Testing the method dockCoordinatesAvailable()");

        double xCord = 2.0;
        double yCord = 2.0;

        assertFalse(instance.dockCoordinatesAvailable(xCord, yCord));
    }

   
    @Test
    public void testDeleteDock(){
        System.out.println("Testing the method deleteDock()");

        String dockName = "testdock2";

        instance.deleteDock(dockName);

        assertFalse(instance.dockNameAvailable(dockName));
    }
}