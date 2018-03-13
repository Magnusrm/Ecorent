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

public class Factory {
    private ArrayList<Dock> docks;
    private ArrayList<Bike> bikes;
    private ArrayList<Admin> admins;
    private ArrayList<Type> types;
    // private Model model;

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

    public boolean addBike(Bike b){
        //b.setbikeID(model.getBikeIdMax())
        if(b == null ) return false;
        return true;
    }//end method

    public boolean addTypes(Type t){
        if(t == null)return false;
        for(Type type:types){
            if(t.equals(type))return false;
        }//end loop
        types.add(t);
        //model.addType(t);
        return true;
    }//end method

}//end class
