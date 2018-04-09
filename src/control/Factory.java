/*
* Factory.java
* @Team007
*
* This class is an aggregate of Dock.java,Bike.java and Admin.java
* It both updates and retrieves data from the model classes connected to the database
* concerned the creation and edit of these objects.
* The class will provide the view-control classes with data, which is why we add data into private arrays.
 */

package control;

import java.sql.SQLException;
import java.util.*;
import model.*;

public class Factory {
    private ArrayList<Dock> docks = new ArrayList<Dock>();
    private ArrayList<Bike> bikes = new ArrayList<Bike>();
    private ArrayList<Admin> admins = new ArrayList<Admin>();
    private ArrayList<Type> types = new ArrayList<Type>();
    private AdminModel adminModel;
    private BikeModel bikeModel;
    private DockModel dockModel;
    private RepairModel repairModel;
    private TypeModel typeModel;
    private String isLoggedIn;

    public Factory(){
        adminModel = new AdminModel();
        bikeModel = new BikeModel();
        dockModel = new DockModel();
        repairModel = new RepairModel();
        typeModel = new TypeModel();
    }//end constructor

    //Access methods
    public ArrayList<Dock> getDocks() {return docks;}
    public ArrayList<Bike> getBikes(){return bikes;}
    public ArrayList<Admin> getAdmins(){return admins;}
    public ArrayList<Type> getTypes(){return types;}
    public String getIsLoggedIn(){return isLoggedIn;}

    //Method to get bikes, docks and admins from
    //model classes connected to database.
    //This is used every time the user starts the application
    public void updateSystem(){
       bikes = bikeModel.getAllBikes();
       docks = dockModel.getAllDocks();
       for(String name:typeModel.getTypes()){
           Type type = new Type(name);
           types.add(type);
       }//end loop
       admins = adminModel.getAllAdmins();
    }//end method

    //Method to add admin. If mainAdmin is true
    //the admin will have access to add and delete
    //other admins
    public boolean addAdmin(Admin a, boolean mainAdmin){
        if(a == null) throw new IllegalArgumentException("Error at Factory.java, addAdmin, argument is null");
        for(Admin admin:admins){
            if(a.equals(admin)) return false;
        }//end loop
        admins.add(a);
        return adminModel.addAdmin(a.getEmail(), a.getPassword(),mainAdmin);
    }//end method


    //Method to add bikes to database
    public boolean addBike(Bike b){
        if(b == null ) return false;
        bikes.add(b);
       String date = b.getBuyDate().toString();
       double price = b.getPrice();
       String make = b.getMake();
       String type = b.getType().getName();
       double pwrUsage = b.getPowerUsage();
       b.setBikeId(bikeModel.addBike(date,price,make,type,pwrUsage,false));
       return true;
    }//end method

    //Method to add types
    public boolean addType(Type t){
        if(t == null)return false;
        for(Type type:types){
            if(t.equals(type))return false;
        }//end loop
        types.add(t);
        String name = t.getName();
        if(typeModel.addType(name) != -1)return true;
        else return false;
    }//end method

    //Method to add docks
    public boolean addDock(Dock d){
        if(d == null)throw new IllegalArgumentException("Error at Factory.java, addDock, argument is null");
        for(Dock dock : docks){
            if(d.equals(dock))return false;
        }//end loop
        docks.add(d);
        String name = d.getName();
        double x = d.getxCoordinates();
        double y = d.getyCoordinates();
        d.setDockID(dockModel.addDock(name,x,y));
        if(d.getDockID() != -1)return true;
        else return false;
    }//end method

    //Method to delete bikes
    public boolean delBike(int bikeId){
        if(bikeId == 0 || bikeId<0)throw new IllegalArgumentException("No bike ID is zero or negative");
        for(int i = 0; i<bikes.size();i++){
            if(bikes.get(i).getBikeId() == bikeId){
                bikes.remove(i);
                return bikeModel.deleteBike(bikeId);
            }//end if
        }//end loop
        return false;
    }//end method

    //Method to delete docks
    public boolean delDock(int dockId){
        if(dockId == 0 || dockId <0)throw new IllegalArgumentException("No dock ID is zero or negative");
        for(int i = 0;i<docks.size();i++){
            if(docks.get(i).getDockID() == dockId){
                dockModel.deleteDock(docks.get(i).getName());
                docks.remove(i);
                return true;
            }//end if
        }//end loop
        return false;
    }//end method

    public boolean deleteAdmin(Admin a) {
        for (Admin anAdmin : admins) {
            if (a.equals(anAdmin)) {
                adminModel.deleteAdmin(anAdmin.getEmail());
                admins.remove(anAdmin);
                return true;
            }//end if
        }//end loop
        return false;
    }//end method

    //Method to edit bikes
    public boolean editBike(int bikeId, Bike newBike){
        if(bikeId == 0 || bikeId<0) throw new IllegalArgumentException("No bike ID is zero or negative");
        if(newBike == null) throw new IllegalArgumentException("Error at Factory.java,editBike, argument is null");
        for(int i = 0; i<bikes.size(); i++){
            if(bikes.get(i).getBikeId() == bikeId){
                newBike.setBikeId(bikeId);
                bikes.set(i,newBike);
                String regDate = newBike.getBuyDate().toString();
                double price = newBike.getPrice();
                String make = newBike.getMake();
                double pwrUsage = newBike.getPowerUsage();
                String typeName = newBike.getType().toString();
                return bikeModel.editBike(bikeId,regDate,price,make,pwrUsage,typeName);
            }//end if
        }//end loop
        if(newBike.getBikeId() == -1)throw new IllegalArgumentException("The bike ID given does not exist");
        return false;
    }//end method

    //Method to edit docks
    public boolean editDocks (int dockId, Dock d)throws SQLException,ClassNotFoundException{
        if(dockId<0 ||dockId==0)throw new IllegalArgumentException("Dock Id cannot be negative or zero");
        for(int i = 0; i<docks.size();i++){
            if(docks.get(i).getDockID() == dockId){
                d.setDockID(dockId);
                docks.set(i,d);
                String name = d.getName();
                double x = d.getxCoordinates();
                double y = d.getyCoordinates();
                return dockModel.editDock(dockId,name,x,y);
            }//end if
        }//end loop
        if(d.getDockID() == -1)throw new IllegalArgumentException("The dock ID given does not exist");
        return false;
    }//end method

    //Method to edit types
    public boolean editType(Type typeOriginal, Type typeEdit) {
        if(typeEdit == null||typeEdit.getName().length() == 0)throw new IllegalArgumentException("No input");
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).equals(typeOriginal)) {
                int j = TypeModel.typeExists(typeOriginal.getName());
                types.set(i, typeEdit);
                return typeModel.editType(j,typeEdit.getName());
            }//end if
        }//end loop
        if(TypeModel.typeExists(typeOriginal.getName())==-1)throw new IllegalArgumentException("The type does not exist");
        return false;
    }//end method

    //Method to delete types
    public boolean deleteType(Type type) {
        if(type == null||type.getName().length() == 0)throw new IllegalArgumentException("No input");
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).equals(type)){
                types.remove(i);
                return typeModel.deleteType(type.getName());
            }//end if
        }//end loop
        if(TypeModel.typeExists(type.getName()) == -1)throw new IllegalArgumentException("The type does not exist");
        return false;
    }

    //Method to get an user's password
    public String password(String email){
        for(Admin a:admins){
            if(a.getEmail().toLowerCase().equals(email.toLowerCase())){
                isLoggedIn = email;
                return a.getPassword();
            }//end if
        }//end loop
        return null;
    }//end method


}//end class
