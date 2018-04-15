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
        int bikeId = 36;
        instance.setBikeId(bikeId);
        int expResult = 36;
        int result = instance.getBikeId();
        assertEquals(expResult,result);
    }//end method

}//end class
