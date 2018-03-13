/*
* Dock.java
* @Team007
*
* Class to define docks.
* It has methods change the name of the dock.
* It also allows the client to choose and change the position of the dock.
 */

package Control;

public class Dock {
    private final int dockID = 0; //This will be fetched in the constructor using the model class's methods
    private String name;
    private double xCoordinates;
    private double yCoordinates;
    private double powerUsage; //This is the dock's complete power usage combined

    public Dock(String name, double x, double y){
        if(name == null)throw new IllegalArgumentException("Your dock must have a name!");
        if(x<0 || y<0)throw new IllegalArgumentException("Your coordinates can't be negative numbers");
        //Remember to fetch dockID from model
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

    //Equals method.
    //Created to avoid indifference between lower and upper case characters
    @Override
    public boolean equals(Object b){
        if(b == null)throw new IllegalArgumentException("Error in Dock.java, equals, argument is null");
        if(!(b instanceof Dock))throw new IllegalArgumentException("Object must be instance of Dock.java");
        Dock a = (Dock)b;
        if(dockID == a.getDockID())return true;
        else return false;
    }//end method

    @Override
    public String toString(){
        return "Dock name: " + name + "\nPower usage at the moment is " + powerUsage +
                "\nDock coordinates: " + xCoordinates + ", " + yCoordinates;
    }//end method

}//end class
