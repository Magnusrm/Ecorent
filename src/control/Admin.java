package control;

/**
 * Admin.java
 * Class for Admin objects
 *
 * @author Team 007
 */
public class Admin {
    private String email;
    private String password;
    private boolean mainAdmin;


    public Admin(String email, String password, boolean mainAdmin) {
        if (email == null) { throw new IllegalArgumentException("Email cannot be null."); }
        if (password == null) { throw new IllegalArgumentException("Password cannot be null."); }
        this.email = email;
        this.password = password;
        this.mainAdmin = mainAdmin;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isMainAdmin() {
        return mainAdmin;
    }

    /**
     * Method which sets the password using the Password class and its hashing algorithm
     *
     *@param password is the String input the user sets in the program
     */
    public void setPassword(String password) {
        if (password == null) { throw new IllegalArgumentException("Password cannot be null."); }
        String hashed = Password.hashPassword(password);
        this.password = hashed;
    }

    /**
     * Override of the toString()-method to display the admin user
     * @return The admins email and whether or not they are a main admin
     */
    @Override
    public String toString() {
        String a;
        if (mainAdmin) {
            a = "YES";
        } else {
            a = "NO";
        }
        return "User: " + email + "\n Main admin: " + a;
    }

    /**
     * Makes the equals method of this class to only check the email parameter of the object, overrides the standard equals method.
     * @param o an object of the class
     * @return boolean     if the parameters compared are equal
     */
    @Override
    public boolean equals(Object o){
        if (o == null) { throw new IllegalArgumentException("The object you are comparing cannot be null."); }
        if (!(o instanceof Admin)) {
            return false;
        }

        Admin a = (Admin) o;

        return (this.email.equals(a.getEmail()));

    }
}
