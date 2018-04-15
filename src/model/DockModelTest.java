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


    @Override
    public boolean equals(Object o){
        if (o == null) { throw new IllegalArgumentException("The object you are comparing cannot be null"); }
        if (!(o instanceof Dock)) {
            return false;
        }

        Dock b = (Dock) o;

        return (((Dock) o).getName().equals(b.getDockID()) && ((Dock) o).getxCoordinates() == (b.getxCoordinates()) &&
                ((Dock) o).getyCoordinates() == b.getyCoordinates());
    }


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
    public void testDockNameExists(){
        System.out.println("Testing the method dockNameExists()");

        String dockName = "testdock";

        assertTrue(instance.dockNameExists(dockName));
    }

    //The expected result has to match the auto-incremented dock_id in the database
    @Test
    public void testAddDock(){
        System.out.println("Testing the method addDock()");

        String dockName = "testdock2";
        double xCord = 2.1;
        double yCord = 2.2;

        int expResult = 8;
        int result = instance.addDock(dockName, xCord, yCord);
        assertEquals(expResult, result);
    }

    //The dockID has to match the auto-incremented dock_id in the database
    @Test
    public void testEditDock(){
        System.out.println("Testing the method editDock()");

        int dockID = 8;
        String dockName = "testdock2";
        double xCord = 2.0;
        double yCord = 2.0;

        instance.editDock(dockID, dockName, xCord, yCord);

        Dock expResult = new Dock(dockName, xCord, yCord);
        Dock result = instance.getDock(dockName);
        assertEquals(expResult, result);
    }

    @Test
    public void testDeleteDock(){
        System.out.println("Testing the method deleteDock()");

        String dockName = "testdock2";

        instance.deleteDock(dockName);

        assertFalse(instance.dockNameExists(dockName));
    }
}