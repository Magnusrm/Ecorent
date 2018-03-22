package model;
import control.Dock;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


public class DockModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    DockModel instance;

    @BeforeAll
    public void before() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - before() in DockModelTest");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " - before() in DockModelTest");
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
        instance = new DockModel();
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testGetDock(){
        System.out.println("Testing the method getDock()");

        String name = "testdock";
        double xCord = 20.1;
        double yCord = 20.1;

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

    @Test
    public void testAddDock(){
        System.out.println("Testing the method addDock()");

        String dockName = "testdock2";
        double xCord = 2.1;
        double yCord = 2.2;

        int expResult = 2;
        int result = instance.addDock(dockName, xCord, yCord);
        assertEquals(expResult, result);
    }

    @Test
    public void testEditDock(){
        System.out.println("Testing the method editDock()");

        int dockID = 2;
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