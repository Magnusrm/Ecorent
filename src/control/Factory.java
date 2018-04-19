/**
 * Factory.java
 * @author Team007
 *
 * This class is an aggregate of Dock.java,Bike.java and Admin.java
 * It both updates and retrieves data from the model classes connected to the database
 * concerned the creation and edit of these objects.
 * The class will provide the view-control classes with data, which is why we add data from the
 * database into private arrays.
 */

package control;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import model.*;

public class Factory {
    private ArrayList<Dock> docks = new ArrayList<>();
    private ArrayList<Bike> bikes = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Type> types = new ArrayList<>();
    private ArrayList<RepairSent> repairsNotReturned = new ArrayList<>();
    private AdminModel adminModel;
    private BikeModel bikeModel;
    private BikeStatsModel bikeStatsModel;
    private BikeStatsModel bsm;
    private DockModel dockModel;
    private DockStatsModel dockStatsModel;
    private RepairModel repairModel;
    private TypeModel typeModel;
    private ArrayList<RepairReturned> repairsCompleted = new ArrayList<>();
    private int MAINDOCK = 0; //the default dock of the bikes.


    public Factory(){
        adminModel = new AdminModel();
        bikeModel = new BikeModel();
        bikeStatsModel = new BikeStatsModel();
        dockModel = new DockModel();
        repairModel = new RepairModel();
        typeModel = new TypeModel();
        dockStatsModel = new DockStatsModel();
    }//end constructor

    //Access methods
    public ArrayList<Dock> getDocks() {return docks;}
    public ArrayList<Bike> getBikes(){return bikes;}
    public ArrayList<Admin> getAdmins(){return admins;}
    public ArrayList<Type> getTypes(){return types;}
    public ArrayList<RepairSent> getRepairsNotReturned(){return repairsNotReturned;}
    public ArrayList<RepairReturned> getRepairsCompleted(){return repairsCompleted;}
    public int getMAINDOCK(){return MAINDOCK;}

    /**
     * Method to get bikes, docks, types, repairs and admins from
     * model classes connected to database.
     * This is used every time the user starts the application
     */
    public void updateSystem(){
        bikes = bikeModel.getAllBikes();
        docks = dockModel.getAllDocks();
        MAINDOCK = docks.get(0).getDockID();
        for(Bike b:bikes)b.setDockId(MAINDOCK);
        for(String name:typeModel.getTypes()){
            Type type = new Type(name);
            types.add(type);
        }//end loop
        fillRepair();
        admins = adminModel.getAllAdmins();
    }//end method

    /**
     * Private method to fill out the repair arrays from
     * database.
     * A duplicate bug seem to occur in repairsNotReturned at times.
     * The reason is at this time (16.04.2018) unknown,
     * but the duplicates get removed with the second for-loop.
     * The method can be updated in the future.
     */
    private void fillRepair(){
        repairsCompleted = repairModel.getRepairsReturned();

        for(int i:repairModel.getRepairIDs()){
            repairsNotReturned.add(repairModel.getRepair(i));
        }//end loop

        //Removing duplicates
        for(int i = 0;i<repairsNotReturned.size();i++){
            for(int j = i+1; j<repairsNotReturned.size();j++){
                if(repairsNotReturned.get(i).getRepair_id() == repairsNotReturned.get(j).getRepair_id()){
                    repairsNotReturned.remove(j);
                }//end condition
            }//end loop
        }//end loop

        for(int i = 0; i<repairsNotReturned.size();i++){ //Registering that bikes are repairing
            if(bikes.get(i).getBikeId() == repairsNotReturned.get(i).getBikeId())bikes.get(i).setRepairing(true);
        }//end loop
    }//end method

    /**
     * Method to add admin. If mainAdmin is true
     * the admin will have access to add and delete
     * admins
     * @param a is an Admin object
     * @return true if added in database
     */
    public boolean addAdmin(Admin a){
        if(a == null) throw new IllegalArgumentException("Error at Factory.java, addAdmin, argument is null");
        for(Admin admin:admins){
            if(a.equals(admin)) return false;
        }//end loop
        admins.add(a);
        return adminModel.addAdmin(a.getEmail(), a.getPassword(),a.isMainAdmin());
    }//end method


    /**
     * Method to add a bike.
     * @param b is a Bike object
     * @return boolean
     */
    public boolean addBike(Bike b){
        if(b == null ) return false;
        b.setDockId(docks.get(0).getDockID());
        bikes.add(b);
        String date = b.getBuyDate().toString();
        double price = b.getPrice();
        String make = b.getMake();
        String type = b.getType().getName();
        int dockID = b.getDockId();
        double pwrUsage = b.getPowerUsage();
        b.setDockId(MAINDOCK);
        int bikeID = bikeModel.addBike(date,price,make,type,pwrUsage,false);
        b.setBikeId(bikeID);
        bikeModel.setDockID(bikeID, MAINDOCK);
        LocalDateTime ldt = LocalDateTime.now();
        String time = ("" + ldt + "").replaceAll("T", " ");
        time = time.substring(0, time.length() - 4);
        bsm.updateStats(time, bikeID, 100, docks.get(MAINDOCK).getxCoordinates(), docks.get(MAINDOCK).getyCoordinates(), 0, 0);
        return true;
    }//end method

    /**
     * Method to add types
     * Takes a Type object and retrieves
     * the information the database needs.
     * @param t is an object of Type.java
     * @return true if operation is successful.
     */
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

    /**
     * Method to add docks.
     * Takes an object of Dock and
     * retrieves the information the
     * dock needs.
     * @param d is an object of Dock.java
     * @return boolean true if operation is successful
     */
    public boolean addDock(Dock d){
        if(d == null)throw new IllegalArgumentException("Error at Factory.java, addDock, argument is null");
        docks.add(d);
        String name = d.getName();
        double x = d.getxCoordinates();
        double y = d.getyCoordinates();
        d.setDockID(dockModel.addDock(name,x,y));
        if(d.getDockID() != -1)return true;
        else return false;
    }//end method

    /**
     * Method to add a repair.
     * Takes object in as argument and retrieves
     * the information model class needs.
     * @param r is an object of RepairSent.java
     * @return boolean true if operation is successful.
     */
    public boolean repairSent(RepairSent r){
        if(r == null) throw new IllegalArgumentException("The repair object is not created");
        int bikeID = r.getBikeId();
        String beforeDescription = r.getBeforeDesc();
        String dateSent = r.getDateSent().toString();
        r.setRepairId(repairModel.sendRepair(bikeID,dateSent,beforeDescription));
        bikeModel.changeRepair(bikeID);
        repairsNotReturned.add(r);
        if(r.getRepair_id() != -1){
            for(Bike b: bikes){
                if(b.getBikeId() == bikeID)b.setRepairing(true);
            }//end loop
            return true;
        }else return false;
    }//end method

    /**
     * Method to receive a repair.
     * Takes object in as argument and
     * retrieves what model classes need.
     * @param r is an object of RepairReturned.java
     * @return boolean true if operation is successful.
     */
    public boolean repairReturned(RepairReturned r){
        if(r == null)throw new IllegalArgumentException("Repair object is not created!");
        int repairId = 0;
        for(Repair r1: repairsNotReturned){
            if(r1.getBikeId() == r.getBikeId())repairId = r1.getRepair_id();
        }//end loop
        if(repairId != 0) {
            String date = r.getDateReceived().toString();
            String desc = r.getAfterDesc();
            double price = r.getPrice();
            bikeModel.changeRepair(r.getBikeId());
            repairsCompleted.add(r);
            //for(RepairSent r1:repairsNotReturned){if(r1.getRepair_id()==repairId)repairsNotReturned.remove(r1);}
            return (repairModel.returnRepair(repairId, date, desc, price));
        }else return false;
    }//end method

    /**
     * Method to delete bikes.
     * Searches for the bike using the bikeID
     * @param bikeId is an int identifying the bike.
     * @return boolean true if the operation is successful.
     */
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

    /**
     * Method to delete docks
     * Uses the ID to find and delete the bike.
     * @param dockId is an int identifying the dock
     * @return boolean true if the operation is successful.
     */
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

    /**
     * Method to delete admin.
     * Uses the email to find and delete admin
     * This operation is only possible to perform
     * as a main admin.
     * @param email is a String that identifies an Admin
     * @return boolean true if the operation is successful.
     */
    public boolean deleteAdmin(String email) {
        for (Admin anAdmin : admins) {
            if (email.equals(anAdmin.getEmail())) {
                adminModel.deleteAdmin(email);
                admins.remove(anAdmin);
                return true;
            }//end if
        }//end loop
        return false;
    }//end method

    //Method to change

    /**
     * Method to edit bikes.
     * Uses the bikeId to find the given bike.
     * Then it replaces the bike object with the
     * one coming in as argument.
     * @param bikeId is an int that identifies a bike
     * @param newBike is a new bike object that replaces
     *                the given bike.
     * @return boolean true if the operation is successful.
     */
    public boolean editBike(int bikeId, Bike newBike){
        if(bikeId == 0 || bikeId<0) throw new IllegalArgumentException("No bike ID is zero or negative");
        if(newBike == null) throw new IllegalArgumentException("Error at Factory.java,editBike, argument is null");
        for(int i = 0; i<bikes.size(); i++){
            if(bikes.get(i).getBikeId() == bikeId){
                newBike.setBikeId(bikeId);
                int dockID = dockModel.getDockID(bikeId);
                newBike.setDockId(dockID);
                bikes.set(i,newBike);
                String regDate = newBike.getBuyDate().toString();
                double price = newBike.getPrice();
                String make = newBike.getMake();
                double pwrUsage = newBike.getPowerUsage();
                String typeName = newBike.getType().getName();
                return bikeModel.editBike(bikeId,regDate,price,make,dockID,pwrUsage,typeName);
            }//end if
        }//end loop
        if(newBike.getBikeId() == -1)throw new IllegalArgumentException("The bike ID given does not exist");
        return false;
    }//end method

    /**
     * Method to edit docks.
     * Takes the dock name and finds the given dock.
     * Then it replaces it with the given Dock object.
     * @param dockName is an object of String.java
     * @param d is an object of Dock.java
     * @return boolean true if operation is successful.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean editDocks(String dockName, Dock d)throws SQLException,ClassNotFoundException{
        if(dockName == null)throw new IllegalArgumentException("Dock Id cannot be negative or zero");
        for(int i = 0; i<docks.size();i++){
            if(docks.get(i).getName().equals(dockName)){
                int dockId = dockModel.getDock(dockName).getDockID();
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

    /**
     * Method to edit types.
     * Takes in an original Type object and finds the given type.
     * Then it replaces the type with the new Type object.
     * @param typeOriginal is an object of Type.java
     * @param typeEdit is an object of Type.java
     * @return boolean true if operation is successful.
     */
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

    /**
     * Method to delete types.
     * Takes a Type object.
     * If the type doesn't exist it will throw exceptions.
     * If the type exists it will remove it.
     * @param type is an object of Type.java
     * @return boolean true if operation is successful.
     */
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
    }//end method

    //Method to delete all bikes without a type
    public boolean deleteAllBikes(){
        for(int i = 0; i<bikes.size();i++){
            if(bikes.get(i).getType() == null)bikes.remove(i);
        }
        return bikeModel.deleteBikesWhereTypeIsNULL();
    }//end

    //Method to get all bikes docked at a given dock
    public int[] dockedBikes(String dockName){
        if(dockModel.bikesAtDock(dockName) != null) {
            ArrayList<Integer> docked = dockModel.bikesAtDock(dockName);
            if (docked.size() != 0) {
                int[] dockedBikes = new int[docked.size()];
                for (int i = 0; i < dockedBikes.length; i++) {
                    dockedBikes[i] = docked.get(i);
                }//end loop
                return dockedBikes;
            } else {
                int[] noBikes = new int[0];
                return noBikes;
            }//end condition
        }else {
            int[] noBikes = new int[0];
            return noBikes;
        }//end condition
    }//end method

    /**
     * Method to get power usage from a given dock.
     * It uses the dockedBikes(dockName) method to find all bikes docked at the given dock name.
     * It then adds their power usage together and returns the value.
     */
    public double powerUsage(String dockName){
        return dockModel.getPowerAtDock(dockName);
    }//end method


    /**
     *
     * Returns the Total Power Usage Of System.
     *
     * @return
     */
    public double getTotalPowerUsageOfSystem(){
        return dockStatsModel.getTotalPowerUsageOfSystem();
    }

    /**
     *
     * Returns a double describing the expenses of all repairs combined.
     *
     * @return
     */
    public double getRepairExpenses(){
        return repairModel.getPriceOfAllRepairs();
    }

    /**
     *
     * Returns a double describing the expenses of all bike purchases.
     *
     * @return
     */
    public double getBikePurchaseExpenses(){
        return bikeModel.getPriceOfAllBikes();
    }

    /**
     *
     * Returns a double describing the expenses of the total power usage;
     *
     * @return
     */
    public double getPowerExpenses(){
        double price = 0.53;
        return getTotalPowerUsageOfSystem() * price;
    }


    /**
     *
     * Returns a double describing the income of bike rentals
     *
     * @return
     */
    public double getRentIncome(){
        double price = 100; // sets price to rent each bke
        return bikeStatsModel.getTotalTrips() * price;
    }


    /**
     *
     * Returns a double describing the net income.
     *
     * @return
     */
    public double getNetIncome(){
        double sum = 0;

        sum += getRentIncome();
        sum -= getPowerExpenses();
        sum -= getRepairExpenses();
        sum -= getBikePurchaseExpenses();

        return sum;
    }

    public double getTotalDistance(){
        return bikeStatsModel.getTotalDistance();
    }

    public int getTotalTrips(){
        return bikeStatsModel.getTotalTrips();
    };

    public double getAvgKmPerTrip(){
        return (getTotalDistance())/(bikeStatsModel.getTotalTrips());
    }

}//end class
