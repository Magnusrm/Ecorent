
package loginAdm;

import control.Admin;
/**
 * CurrentAdmin.java
 * @author Team 007
 * @version 1.0
 *
 * This class uses the Singleton pattern to store the user logged in to the system.
 * An instance of this class stores an Admin-object.
 */
public class CurrentAdmin {
    private Admin admin;
    private static CurrentAdmin currentAdmin;

    /**
     * Private constructor which means only the class is able to create a CurrentAdmin-object.
     * By doing this you can assure only creating one instance of the CurrentAdmin-class in the program.
     */
    private CurrentAdmin(){
    }

    /**
     * Method which returns an instance of the class and if that does not exists, it creates one.
     * The method follows the Singleton pattern
     * @return CurrentAdmin object
     */
    public static CurrentAdmin getInstance(){
        if (currentAdmin == null){
            currentAdmin = new CurrentAdmin();
        }
        return currentAdmin;
    }

    public void setAdmin(Admin admin){
        this.admin = admin;
    }
    public Admin getAdmin(){
        return admin;
    }


}
