package control.test;

import control.*;
import model.DBCleanup;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FactoryTest {
    private Factory instance;
    private Admin a;
    private Bike b;
    private Dock d;
    private Repair r;
    private Type t;

    @BeforeEach
    void before(){
        instance = new Factory();
        a = new Admin("ilia.rad.saadat@hotmail.com", Password.hashPassword("Test"), true);
        t = new Type("test");
        b = new Bike(LocalDate.parse("2018-04-10"), 3000, "DBS", t, 3200);
        b.setBikeId(54);
        d = new Dock("Junit 1.2", 67,67);
        d.setDockID(13);
        r = new RepairSent("2018-04-16", "Test", 54);
        instance.updateSystem();
    }//end method

    @AfterEach
    void after(){
        instance = null;
        a = null;
        b = null;
        t = null;
        d = null;
        r = null;
    }//end method

    @Test
    void testUpdateSystem(){
        System.out.println("Testing method to update system from database. Only fetching one array");
        Bike expResult = b;
        Bike result = null;
        for(Bike b1:instance.getBikes())if(b1.equals(b))result = b1;
        assertEquals(expResult,result);
    }//end method

    @Test
    void testAddAdmin(){
        System.out.println("Testing method to add admin");
        boolean result =  instance.addAdmin(a);
        assertEquals(true,result);
    }//end method

    @Test
    void testAddBike(){
        System.out.println("Testing method to add bike");
        boolean result = instance.addBike(b);
        assertEquals(true,result);
    }//end method

    @Test
    void testAddType(){
        System.out.println("Testing method to add type");
        boolean result = instance.addType(new Type("Junit"));
        assertEquals(true,result);
    }//end method

    @Test
    void testAddDocks(){
        System.out.println("Testing method to add docks");
        boolean result = instance.addDock(d);
        assertEquals(true,result);
    }//end method

    @Test
    void testAddRepair(){
        System.out.println("Testing method to add repair");
        RepairSent r1 = (RepairSent)r;
        boolean result = instance.repairSent(r1);
        assertEquals(true,result);
    }//end method

    @Test
    void testCompleteRepair(){
       System.out.println("Testing method to complete repair");
       r = new RepairReturned("2018-04-17", "Finishing Junit-test",0.3,54);
       RepairReturned r1 = (RepairReturned)r;
       boolean result = instance.repairReturned(r1);
       assertEquals(true,result);
    }//end method

    @Test
    void testDeleteBike(){
        System.out.println("Testing method to delete bike");
        boolean result = instance.delBike(75);
        assertEquals(true,result);
    }//end method

    @Test
    void testDeleteDock(){
        System.out.println("Testing method to delete dock");
        boolean result = instance.delDock(13);
        assertEquals(true,result);
    }//end method

    @Test
    void testDeleteAdmin(){
        System.out.println("Testing method to delete dock");
        boolean result = instance.deleteAdmin("testadmin1@test.test");
        assertEquals(true,result);
    }//end method

    @Test
    void testEditBike(){
        System.out.println("Testing method to edit bikes");
        Bike b = new Bike(LocalDate.now(),3000, "DBS", t, 3200);
        b.setDockId(1);
        boolean result = instance.editBike(74,b);
        assertEquals(true,result);
    }//end method

}//end class
