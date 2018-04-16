package control;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * DockTest.java
 * @author Team007
 *
 * Simple Junit test for Dock.java
 */

public class DockTest {
    private Dock instance;
    private String name;
    private double x;
    private double y;

    @BeforeEach
    void before(){
        name = "Test";
        x = 3.3;
        y = 6.6;
        instance = new Dock(name,x,y);
    }//end method

    @AfterEach
    void after(){
        name = null;
        x = 0;
        y = 0;
        instance = null;
    }//end method

    @Test
    void testSetName(){
        System.out.println("Testing method to set name");
        String expResult = "Testing";
        instance.setName(expResult);
        String result = instance.getName();
        assertEquals(expResult,result);
    }//end method

    @Test
    void testSetPosition(){
        System.out.println("Testing method to set position");
        double expResult = 6.3;
        instance.setPosition(expResult,y);
        double result = instance.getxCoordinates();
        assertEquals(expResult,result);
    }//end method

    @Test
    void testSetDockID(){
        System.out.println("Testing method to set dock id");
        int expResult = 4;
        instance.setDockID(expResult);
        int result = instance.getDockID();
        assertEquals(expResult,result);
    }//end method

    @Test
    void testEquals(){
        System.out.println("Testing equals-method");
        Dock dock = new Dock(name,x,y);
        boolean result = instance.equals(dock);
        assertEquals(true,result);
    }//end method

}//end class
