package control.test;

import control.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        t = new Type("Landevei");
        b = new Bike(LocalDate.now(), 3000, "DBS", t, 0.36);
        b.setBikeId(666);
        d = new Dock("Test", 3.3,6.6);
        r = new Repair("2018-04-16", "Test", 666);
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
        System.out.println("Testing method to update system from database");
        instance.updateSystem();

    }//end method



}//end class
