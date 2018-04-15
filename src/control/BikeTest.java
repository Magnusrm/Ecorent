package control;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * BikeTest.java
 * @author Team007
 *
 * Simple Junit test for Bike.java
 */
public class BikeTest {
    private Bike instance;
    private LocalDate date;
    private double price;
    private String make;
    private Type type;
    private double pwr;

    @BeforeEach
    void before(){
        date = LocalDate.now();
        price = 2000;
        make = "DBS";
        type = new Type("Landevei");
        pwr = 0.36;
        instance = new Bike(date,price,make,type,pwr);
    }//end method

    @AfterEach
    void after(){
        date = null;
        price = 0;
        make = null;
        type = null;
        pwr = 0;
        instance = null;
    }//end method

    @Test
    void testSetBikeId(){
        System.out.println("Testing method to set bike id");
        int bikeId = 36;
        instance.setBikeId(bikeId);
        int expResult = 36;
        int result = instance.getBikeId();
        assertEquals(expResult,result);
    }//end method

    @Test
    void testSetPrice(){
        System.out.println("Testing method to set price");
        double price = 3000;
        instance.setPrice(price);
        double result = instance.getPrice();
        assertEquals(price,result);
    }//end method

    @Test
    void testSetMake(){
        System.out.println("Testing method to set make");
        String make = "notDbs";
        instance.setMake(make);
        String result = instance.getMake();
        assertEquals(make,result);
    }//end method

    @Test
    void testSetType(){
        System.out.println("Testing method to set type");
        Type type = new Type("Test");
        instance.setType(type);
        Type result = instance.getType();
        assertEquals(type,result);
    }//end method

    @Test
    void testSetRepairing(){
        System.out.println("Testing method to set repairing");
        instance.setRepairing(true);
        boolean result = instance.isRepairing();
        assertEquals(true,result);
    }//end method

    @Test
    void testDeactivate(){
        System.out.println("Testing method to deactivate a bike");
        instance.deactivate(true);
        assertEquals(false,instance.isActive());
    }//end method

    @Test
    void testUpdateBatteryPercent(){
        System.out.println("Testing method to update battery percent");
        int expResult = 30;
        int result = instance.updateBatteryPercent(70);
        assertEquals(expResult,result);
    }//end method

    @Test
    void testEquals(){
        System.out.println("Testing equals-method");
        Bike b = new Bike(date,price,make,type,pwr);
        boolean result = instance.equals(b);
        assertEquals(true,result);
    }//end method
}//end class
