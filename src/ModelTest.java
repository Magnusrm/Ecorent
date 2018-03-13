
import Control.*;
import org.junit.*;

class ModelTest {

    Model instance;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        instance = new Model();
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        instance.endConnection();
        instance = null;
    }

    //A bike equals to expResult must be in the database for this test to work
    @org.junit.jupiter.api.Test
    void getBike() {
        System.out.println("getBike");
        int bikeID = 1;
        String date = "2018-03-08";
        double price = 1000;
        String make = "DBS";
        int typeID = 1;
        Bike expResult = new Bike(bikeID, date, price, make, typeID);
        Bike result = instance.getBike(bikeID);
        assertSame(expResult, result);
    }

    //One type with the name "Landevei" must be in the database for this test to work
    //The private method typeExists() is also tested (casing)
    @org.junit.jupiter.api.Test
    void addType() {
        System.out.println("addType");
        String type = "landevei";
        int expResult = -1;
        assertEquals(instance.addType(type), expResult);
    }

    //One bike must be in the database for this test to work
    @org.junit.jupiter.api.Test
    void addBike() {
        System.out.println("addBike");
        String date = "2018-03-08";
        double price = 20.43;
        String make = "DBS";
        int expResult = 2;
        int result = instance.addBike(date, price, make);
        assertEquals(expResult, result);
    }

    //One dock must be in the database for this test to work
    @org.junit.jupiter.api.Test
    void addDock(){
        System.out.println("addDock");
        String name = "Trondheim S";
        double pwrUsg = 30.8;
        double xCord = 1.23;
        double yCord = 21.13;
        int expResult = 2;
        int result = instance.addDock(name, pwrUsg, xCord, yCord);
        assertEquals(expResult, result);
    }

    //A dock equals to expResult must be in the database for this test to work
    @org.junit.jupiter.api.Test
    void getDock(){
        System.out.println("getDock");
        int dockID = 1;
        String name = "Torget";
        double pwrUsg = 25;
        double xCord = 23.1;
        double yCord = 33.2;
        Dock expResult = new Dock(dockID, name, pwrUsg, xCord, yCord);
        Dock result = instance.getDock(name);
        assertSame(expResult, result);
    }

    //Exactly one repair must be in the database for this test to work
    @org.junit.jupiter.api.Test
    void sendRepair(){
        int bikeID = 1;
        String dateSent = "2018-03-12";
        String bDesc = "Flat tire";
        int expResult = 2;
        int result = instance.sendRepair(bikeID, dateSent, bDesc);
        assertEquals(expResult, result);
    }

    //No repair must have repairID = 5 for this test to work
    @org.junit.jupiter.api.Test
    void returnRepair(){
        int repairID = 5;
        String dateReceived = "2018-04-12";
        String aDesc = "Fixed flat tire";
        double price = 200.00;
        assertFalse(instance.returnRepair(repairID, dateReceived, aDesc, price));
    }

    //A repair equal to "expResult" must be in the database for this test to work
    @org.junit.jupiter.api.Test
    void getRepair() {
        int repairID = 1;
        String dateSent = "2018-03-12";
        String beforeDesc = "fdfddffd";
        String dateReceived = "2018-03-13";
        String afterDesc = "dsadas";
        double price = 20;
        Repair expResult = new Repair(repairID, dateSent, beforeDesc, dateReceived, afterDesc);
        Repair result = instance.getRepair(repairID);
        assertSame(expResult, result);
    }

    //A user with the given email must be in the database for this test to work
    @org.junit.jupiter.api.Test
    void userExists() {
        System.out.println("userExists");
        String email = "Sander-nico@hotmail.com";
        boolean result = instance.userExists(email);
        assertFalse(result);
    }

    @org.junit.jupiter.api.Test
    void getHash() {
    }
}