package model.tests;
import control.Dock;
import control.Type;
import model.DBCleanup;
import model.TypeModel;
import org.junit.jupiter.api.*;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Team 007
 *
 * @version 1.0
 *
 * This is a test class for the class TypeModel.
 */
public class TypeModelTest {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    TypeModel instance;


    @BeforeEach
    public void before() {
        instance = new TypeModel();
        connection = DBCleanup.getConnection();
    }


    @AfterEach
    public void after() {
        DBCleanup.closeConnection(connection);
        instance = null;
    }


    @Test
    public void testTypeExists(){
        System.out.println("Testing the method typeExists()");

        String name = "test";

        int expResult = 1; //Make sure that this is the same as the ID in the DB
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

        String name = "racer"; //Make sure this type already exists in the database

        int expResult = -1;
        int result = instance.addType(name);
        assertEquals(expResult, result);
    }

    @Test
    public void testAddType2(){
        System.out.println("Testing the method AddType2");

        String name = "TestType"; //Make sure this type already exists in the database

        int expResult = 63; //This need to be changed to the expected ID of the repair
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
    public void testDeleteType1(){
        System.out.println("Testing the method deleteType2()");

        String name = "TestType";

        instance.deleteType(name);

        int expResult = -1;
        int result = instance.typeExists(name);
        assertEquals(expResult, result);
    }
}