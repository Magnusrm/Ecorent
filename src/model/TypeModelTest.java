package model;
import control.Dock;
import control.Type;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;


public class TypeModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    TypeModel instance;

    @BeforeAll
    public void before() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://mysql.stud.iie.ntnu.no:3306/sandern?user=sandern&password=TUyEYWPb&useSSL=false&autoReconnect=true");
            Class.forName("com.mysql.jdbc.Driver");
        } catch (SQLException e) {
            System.out.println(e.getMessage() + " - before() in TypeModelTest");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage() + " - before() in TypeModelTest");
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
        instance = new TypeModel();
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testTypeExists(){
        System.out.println("Testing the method typeExists()");

        String name = "racer";

        int expResult = 1;
        int result = instance.typeExists(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testTypeExists2(){
        System.out.println("Testing the method typeExists() again");

        String name = "tandem";

        int expResult = -1;
        int result = instance.typeExists(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testAddType(){
        System.out.println("Testing the method addType()");

        String name = "racer";

        int expResult = -1;
        int result = instance.addType(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testEditType(){
        System.out.println("Testing the method editType()");

        int typeID = 1;
        String name = "test";

        instance.editType(typeID, name);

        int expResult = 1;
        int result = instance.typeExists("test");
        assertEquals(expResult, result);
    }

    @Test
    public void testDeleteType(){
        System.out.println("Testing the method deleteType()");

        String name = "hiTest";

        instance.deleteType(name);

        int expResult = -1;
        int result = instance.typeExists(name);
        assertEquals(expResult, result);
    }
}