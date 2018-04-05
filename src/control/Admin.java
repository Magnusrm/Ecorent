package control;

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

    public void setPassword(String password) {
        if (password == null) { throw new IllegalArgumentException("Password cannot be null."); }
        this.password = password;
    }

    public String toString() {
        String a;
        if (mainAdmin) {
            a = "YES";
        } else {
            a = "NO";
        }
        return "User: " + email + "\n Main admin: " + a;
    }

    public boolean equals(Object o){
        if (o == null) { throw new IllegalArgumentException("The object you are comparing cannot be null."); }
        if (!(o instanceof Admin)) {
            return false;
        }

        Admin a = (Admin) o;

        return (this.email.toLowerCase() == a.getEmail().toLowerCase());

    }
}
