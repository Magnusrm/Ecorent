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


    //Method to add bikes to database
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

    //Method to add types
    public boolean addType(Type t){
        if(t == null)return false;
        for(Type type:types){
            if(t.equals(type))return false;
        }//end loop
        types.add(t);
        String name = t.getName();
        if(model.addType(name) != -1)return true;
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
        d.setDockID(model.addDock(name,x,y));
        if(d.getDockID() != -1)return true;
        else return false;
    }//end method

    //Method to delete bikes
    public boolean delBike(int bikeId){
        if(bikeId == 0 || bikeId<0)throw new IllegalArgumentException("No bike ID is zero or negative");
        for(int i = 0; i<bikes.size();i++){
            if(bikes.get(i).getBikeId() == bikeId){
                bikes.remove(i);
                //model.delBike(bikeId);
                return true;
            }//end if
        }//end loop
        return false;
    }//end method

    //Method to delete docks
    public boolean delDock(int dockId){
        if(dockId == 0 || dockId <0)throw new IllegalArgumentException("No dock ID is zero or negative");
        for(int i = 0;i<docks.size();i++){
            if(docks.get(i).getDockID() == dockId){
                docks.remove(i);
                //model.delDock(dockId);
                return true;
            }//end if
        }//end loop
        return false;
    }//end method

    public boolean deleteAdmin(Admin a) {
        for (Admin anAdmin : admins) {
            if (a.equals(anAdmin)) {
                //model.deleteAdmin(anAdmin);
                admins.remove(anAdmin);
                return true;
            }
        }
        return false;
    }

    //Method to edit bikes
    public boolean editBike(int bikeId, Bike newBike){
        if(bikeId == 0 || bikeId<0) throw new IllegalArgumentException("No bike ID is zero or negative");
        if(newBike == null) throw new IllegalArgumentException("Error at Factory.java,editBike, argument is null");
        for(int i = 0; i<bikes.size(); i++){
            if(bikes.get(i).getBikeId() == bikeId){
                newBike.setBikeId(bikeId);
                bikes.set(i,newBike);
                //model.editBike(newBike);
                return true;
            }//end if
        }//end loop
        if(newBike.getBikeId() == -1)throw new IllegalArgumentException("The bike ID given does not exist");
        return false;
    }//end method

    //Method to edit docks
    public boolean editDocks(int dockId, Dock d){
        if(dockId<0 ||dockId==0)throw new IllegalArgumentException("Dock Id cannot be negative or zero");
        for(int i = 0; i<docks.size();i++){
            if(docks.get(i).getDockID() == dockId){
                d.setDockID(dockId);
                docks.set(i,d);
                //model.editDock(d);
                return true;
            }//end if
        }//end loop
        if(d.getDockID() == -1)throw new IllegalArgumentException("The dock ID given does not exist");
        return false;
    }//end method

    public boolean editType(Type type) {
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).equals(type)) {
                types.set(i, type);
                model.addType(type.getName());
               // model.deleteType(type.getName());
                return true;
            }
        }
        return false;
    }
    public boolean deleteType(Type type) {
        for (int i = 0; i < types.size(); i++) {
            if (types.get(i).equals(type)) {
                types.remove(i);
                //model.deleteType(type.getName());
                return true;
            }
        }
        return false;
    }

    //Test
    public static void main(String[] args){
        Factory factory = new Factory();
        Type type = new Type("Landevei");
        factory.addType(type);
    }//end main

}//end class
