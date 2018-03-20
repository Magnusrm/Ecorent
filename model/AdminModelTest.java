package model;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    AdminModel instance;

    @BeforeAll
    public void before() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true");
            Class.forName("com.mysql.jdbc.Driver");
        }catch(SQLException e){
            System.out.println(e.getMessage() + " - before() in AdminModelTest");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage() + " - before() in AdminModelTest");
        }
    }

    @AfterAll
    public void after(){
        try{
            connection.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @BeforeEach
    public void setUp(){
        instance = new AdminModel();
    }

    @AfterEach
    public void tearDown(){
        instance = null;
    }

    @Test
    public void testAdminExists(){
        System.out.println("Testing the method adminExists()");
        System.out.println("The test admin must be in the database for this test to work");

        String email = "testadmin@test.test";
        boolean expResult = true;
        boolean result = instance.adminExists(email);
        assertEquals(expResult, result);
    }

    @Test
    public void testAddAdmin(){
        System.out.println("Testing the method addAdmin()");

        String email = "testadmin1@test.test";
        String salt = "hello";
        String hash = "people";
        boolean priviliged = false;

        instance.addAdmin(email, salt, hash, priviliged);

        boolean expResult = true;
        boolean result = instance.adminExists(email);

        assertEquals(expResult, result);
    }

    @Test
    public void testDeleteAdmin(){
        System.out.println("Testing the method deleteAdmin()");

        String email = "testadmin1@test.test"; //Same as above so addAdmin must work for this to work

        instance.deleteAdmin(email);

        boolean expResult = false;
        boolean result = instance.adminExists(email);

        assertEquals(expResult, result);
    }

    @Test
    public void testGetSalt(){
        System.out.println("Testing the method getSalt()");

        String email = "testadmin@test.test";

        String expResult = "testsalt";
        String result = instance.getSalt(email);

        assertEquals(expResult, result);
    }

    @Test
    public void testGetHash(){
        System.out.println("Testing the method getHash()");

        String email = "testadmin@test.test";

        String expResult = "testhash";
        String result = instance.getHash(email);

        assertEquals(expResult, result);
    }

}
