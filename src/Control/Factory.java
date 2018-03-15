/*
* Factory.java
* @Team007
*
* This class is an aggregate of Dock.java,Bike.java and Admin.java
* It both updates and retrieves data from the model classes connected to the database
* concerned the creation and edit of these objects.
* The class will provide the view control classes with data.
 */

package Control;

import java.util.*;
import Model.*;

public class Factory {
    private ArrayList<Dock> docks = new ArrayList<Dock>();
    private ArrayList<Bike> bikes = new ArrayList<Bike>();
    private ArrayList<Admin> admins = new ArrayList<Admin>();
    private ArrayList<Type> types = new ArrayList<Type>();
    private Model model = new Model();

    public Factory(){}//default constructor

    //Access methods
    public ArrayList<Dock> getDocks() {return docks;}
    public ArrayList<Bike> getBikes(){return bikes;}
    public ArrayList<Admin> getAdmins(){return admins;}
    public ArrayList<Type> getTypes(){return types;}

    //Method to get bikes, docks and admins from
    //model classes connected to database.
    //This is used every time the user starts the application
    public void updateSystem(){
        //fetch bikes and add in array list
        //fetch docks and add in array list
        //fetch admins and add in array list
        //fetch types and add in array list
    }//end method


    public boolean addAdmin(Admin a){
        if(a == null) throw new IllegalArgumentException("Error at Factory.java, addAdmin, argument is null");
        for(Admin admin:admins){
            if(a.equals(admin)) return false;
        }//end loop
        admins.add(a);
        //model.addAdmin(a);
        return true;
    }//end method

    public boolean deleteAdmin(Admin a) {
        for (Admin anAdmin : admins) {
            if (a.equals(anAdmin)) {
                admins.remove(anAdmin);
                return true;
            }
        }
        return false;
    }

    public boolean addBike(Bike b){
        if(b == null ) return false;
        bikes.add(b);
        String date = b.getBuyDate().toString();
        double price = b.getPrice();
       String make = b.getMake();
       String type = b.getType().getName();
       //b.setBikeId(model.addBike(date,price,make,type));
       return true;
    }//end method

    public boolean addType(Type t){
        if(t == null)return false;
        for(Type type:types){
            if(t.equals(type))return false;
        }//end loop
        types.add(t);
        String name = t.getName();
        model.addType(name);
        return true;
    }//end method

    public boolean editType(Type type) {
        for (int i = 0; i > types.size(); i++) {
            if (types.get(i).equals(type)) {
                types.set(i, type);
                model.addType(type.getName());
                model.deleteType(type.getName());
                return true;
            }
        }
        return false;
    }
    public boolean deleteType(Type type) {
        for (int i = 0; i > types.size(); i++) {
            if (types.get(i).equals(type)) {
                types.remove(i);
                model.deleteType(type.getName());
                return true;
            }
        }
        return false;
    }

}//end class
