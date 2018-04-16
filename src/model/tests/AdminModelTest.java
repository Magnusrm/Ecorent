package model.tests;
import control.Admin;
import control.Bike;
import model.AdminModel;
import model.DBCleanup;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * This is a test class for the class AdminModel
 */
public class AdminModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    AdminModel instance;

    @BeforeEach
    public void before() {
        instance = new AdminModel();
        connection = DBCleanup.getConnection();
    }


    @AfterEach
    public void after() {
        DBCleanup.closeConnection(connection);
        instance = null;
    }


    @Test
    public void testAdminExists() {
        System.out.println("Testing the method adminExists()");

        String email = "testadmin@test.test";
        boolean expResult = true;
        boolean result = instance.adminExists(email);
        assertEquals(expResult, result);
    }

    @Test
    public void testAddAdmin() {
        System.out.println("Testing the method addAdmin()");

        String email = "testadmin1@test.test";
        String hash = "people";
        boolean priviliged = false;

        instance.addAdmin(email, hash, priviliged);

        boolean expResult = true;
        boolean result = instance.adminExists(email);

        assertEquals(expResult, result);
    }

    @Test
    public void testDeleteAdmin() {
        System.out.println("Testing the method deleteAdmin()");

        String email = "testadmin1@test.test"; //Same as above so addAdmin must work for this to work

        instance.deleteAdmin(email);

        boolean expResult = false;
        boolean result = instance.adminExists(email);

        assertEquals(expResult, result);
    }

    @Test
    public void testGetAdmin() {
        System.out.println("Testing the method getAdmin()");

        String email = "testadmin@test.test";

        Admin result = instance.getAdmin(email);
        assertNotNull(result);
    }

    @Test
    public void testGetHash() {
        System.out.println("Testing the method getHash()");

        String email = "testadmin@test.test";

        String expResult = "testhash";
        String result = instance.getHash(email);

        assertEquals(expResult, result);
    }

    @Test
    public void testIsPriviliged() {
        System.out.println("Testing the method IsPriviliged()");

        String email = "testadmin@test.test";

        assertFalse(instance.isPriviliged(email));
    }
}


