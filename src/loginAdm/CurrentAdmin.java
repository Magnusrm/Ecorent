package loginAdm;

import control.Admin;

public class CurrentAdmin {
    private Admin admin;
    private static CurrentAdmin currentAdmin;

    private CurrentAdmin(){
    }

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
