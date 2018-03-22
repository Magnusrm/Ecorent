package login;

public class LoginBean {
    private String email;
    private String password;
    private boolean privileged;

    public LoginBean(String email, String password){
        this.email = email;
        this.password = password;
        this.privileged = false;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean getPrivileged() {
        return privileged;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPrivileged(boolean privileged) {
        this.privileged = privileged;
    }
}


