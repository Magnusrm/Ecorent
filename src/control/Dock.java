/*
* Dock.java
* @Team007
*
* Class to define docks.
* It has methods change the name of the dock.
* It also allows the client to choose and change the position of the dock.
 */

package control;

/**
 * Class for Dock objects
 */
public class Dock {
    private int dockID = -1; //This will be fetched from the database
    private String name;
    private double xCoordinates;
    private double yCoordinates;
    private double powerUsage; //This is the dock's complete power usage combined

    public Dock(String name, double x, double y){
        if(name == null)throw new IllegalArgumentException("Your dock must have a name!");
        if(x<0 || y<0)throw new IllegalArgumentException("Your coordinates can't be negative numbers");
        this.name = name;
        xCoordinates = x;
        yCoordinates = y;
    }//end constructor

    //Access methods
    public String getName(){return name;}
    public int getDockID(){return dockID;}
    public double getxCoordinates(){return xCoordinates;}
    public double getyCoordinates(){return yCoordinates;}
    public double getPowerUsage() {return powerUsage;}

    public void setName(String newName){
        if(newName == null)throw new IllegalArgumentException("Error in Dock.java, setName, argument is null");
        name = newName;
    }//end method

    public void setPosition(double x,double y){
        xCoordinates = x;
        yCoordinates = y;
    }//end method

    public void setDockID(int id){
        dockID = id;
    }//end method

    //Equals-method.
    /**
     * Override of the standard equals()-method to compare objects of the class. Checks if name and position are the same.
     * @param o
     * @return true     if the parameters compared are equal
     * @return false    if the parameters compared are not equal
     * @author Team 007
     */
    @Override
    public boolean equals(Object o){
        if (o == null) { throw new IllegalArgumentException("The object you are comparing cannot be null"); }
        if (!(o instanceof Dock)) { throw new IllegalArgumentException("The object you are comparing must be an instance of Dock");}

        Dock b = (Dock) o;

        return (((Dock) o).getName().equals(b.getName()) && ((Dock) o).getxCoordinates() == (b.getxCoordinates()) &&
                ((Dock) o).getyCoordinates() == b.getyCoordinates());
    }

    /**
     * Override of the standard toString()-method to list a docks information.
     * @return String outprint of the dock information
     * @author Team 007
     */
    @Override
    public String toString(){
        return "Dock name: " + name + "\nPower usage at the moment is " + powerUsage +
                "\nDock coordinates: " + xCoordinates + ", " + yCoordinates;
    }//end method

}//end class


/* DOCK POSISJONER
63.42-43
10.38-40

 */
