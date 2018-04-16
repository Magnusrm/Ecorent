
package control;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * AdminTest.java
 * @author Team007
 *
 * Simple Junit test to quality check Admin.java
 */
public class AdminTest {
    private Admin instance;

    @BeforeEach
    void before(){
        instance = new Admin("ilia.rad.saadat@hotmail.com","test",true);
    }//end method

    @AfterEach
    void after(){
        instance = null;
    }//end method

    @Test
    void testSetPassword(){
        System.out.println("Testing the set password method");
        String newPassword = "testSuccess";
        instance.setPassword(newPassword);
        boolean expResult = true;
        boolean result = Password.check(newPassword,instance.getPassword());
        assertEquals(expResult,result);
    }//end method

    @Test
    void testEqualsMethod(){
        System.out.println("Testing the equals-method");
        String email = "ilia.rad.saadat@hotmail.com";
        String password = "TestingEquals";
        Admin admin = new Admin(email,password,false);
        boolean expResult = true;
        boolean result = instance.equals(admin);
    }//end method

}//end class
