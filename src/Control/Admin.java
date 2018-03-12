package Control;

public class Admin {
    private String email;
    private String password;
    private boolean mainAdmin;

    public Admin(String email, String password, boolean mainAdmin) {
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
        if ((o instanceof Admin)) {
            return false;
        }

        Admin a = (Admin) o;

        return (this.email == a.getEmail());
        /*
        if (this.email == a.getEmail()) {
            return true;
        }
        return false;
        */
    }
}
