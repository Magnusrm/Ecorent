package control;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * TypeTest.java
 * @author Team007
 *
 * Simple Junit test for Type.java
 */

public class TypeTest {
    private Type instance;
    private String name;

    @BeforeEach
    void before(){
        name = "Test";
        instance = new Type(name);
    }//end method

    @AfterEach
    void after(){
        name = null;
        instance = null;
    }//end method

    @Test
    void testSetName(){
        System.out.println("Testing method to set name");
        String expResult = "testing";
        instance.setName(expResult);
        String result = instance.getName();
        assertEquals(expResult,result);
    }//end method

    @Test
    void testEquals(){
        System.out.println("Testing equals-method");
        Type type = new Type(name);
        boolean result = instance.equals(type);
        assertEquals(true,result);
    }//end method
}//end class
