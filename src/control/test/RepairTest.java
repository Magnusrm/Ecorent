package control.test;

import control.*;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * RepairTest.java
 * @author Team007
 *
 * Simple Junit test for Repair.java
 */
public class RepairTest {
    private RepairReturned instance;
    private int repair_id;
    private String date_sent;
    private String before_desc;
    private String date_received;
    private String after_desc;
    private double price;
    private int bike_id;

    @BeforeEach
    void before(){
        repair_id = 4;
        date_sent = "2018-04-14";
        before_desc = "Test start";
        date_received = "2018-04-15";
        after_desc = "Test end";
        price = 4000;
        bike_id = 56;
        instance = new RepairReturned(date_sent,before_desc,date_received,after_desc,price,bike_id);
    }//end method

    @AfterEach
    void after(){
        repair_id = 0;
        date_sent = null;
        before_desc = null;
        date_received = null;
        after_desc = null;
        price = 0;
        bike_id = 0;
        instance = null;
    }//end method

    @Test
    void testSetRId(){
        System.out.println("Testing method to set Repair ID");
        instance.setRepairId(repair_id);
        int result = instance.getRepair_id();
        assertEquals(repair_id,result);
    }//end method

    @Test
    void testSetPrice(){
        System.out.println("Testing method to set price");
        double expResult = 99;
        instance.setPrice(expResult);
        double result = instance.getPrice();
        assertEquals(expResult,result);
    }//end method

    @Test
    void testSetAfterDesc(){
        System.out.println("Testing method to set after description");
        String expResult = "Testiiiing";
        instance.setAfterDesc(expResult);
        String result = instance.getAfterDesc();
        assertEquals(expResult,result);
    }//end method

    @Test
    void testSetBeforeDesc(){
        System.out.println("Testing method to set before description");
        String expResult = "Testiiing";
        instance.setBeforeDesc(expResult);
        String result = instance.getBeforeDesc();
        assertEquals(expResult,result);
    }//end method

    @Test
    void testSetBikeId(){
        System.out.println("Testing method to set bike id");
        int expResult = 61;
        instance.setBikeId(expResult);
        int result = instance.getBikeId();
        assertEquals(expResult,result);
    }//end method

    @Test
    void testSetDateSent(){
        System.out.println("Testing method to set date sent");
        String in = "2018-04-12";
        instance.setDateSent(in);
        LocalDate expResult = LocalDate.parse(in);
        LocalDate result = instance.getDateSent();
        assertEquals(expResult,result);
    }//end method

    @Test
    void testSetDateReceived(){
        System.out.println("Testing method to set date received");
        String in = "2018-04-12";
        instance.setDateReceived(in);
        LocalDate expResult = LocalDate.parse(in);
        LocalDate result = instance.getDateReceived();
        assertEquals(expResult,result);
    }//end method

    @Test
    void testToDate(){
        System.out.println("Testing LocalDate parser");
        String in = "2018-04-12";
        LocalDate expResult = LocalDate.parse(in);
        LocalDate result = instance.toDate(in);
        assertEquals(expResult,result);
    }//end method

    @Test
    void testEquals(){
        RepairReturned r = new RepairReturned(date_sent,before_desc,date_received,after_desc,price,bike_id);
        boolean result = instance.equals(r);
        assertEquals(true,result);
    }//end method

}//end class
