package control;

import java.time.LocalDate;

/**
 * Class for Bike objects
 *
 * @author Team 007
 */
public class Bike {
    private int bikeId = -1; // this will be updated after the bike is added to the database.
    private final LocalDate buyDate;
    private double price;
    private int dockId;
    private String make;
    private Type type;
    private double powerUsage;
    private boolean repairing;
    private boolean active = true; //whether or not the bike is still in use
    private int battery;

    public Bike(LocalDate buyDate, double price, String make, Type type, double powerUsage) {
        if (buyDate == null) {throw new IllegalArgumentException("Buy date cannot be null.");}
        if (price < 0) {throw new IllegalArgumentException("Price cannot be negative.");}
        if (make == null) {throw new IllegalArgumentException("Make cannot be null.");}
        if (type == null) {throw new IllegalArgumentException("Type cannot be null.");}
        if (powerUsage < 0) {throw new IllegalArgumentException("Power usage cannot be negative.");}
        this.buyDate = buyDate;
        this.price = price;
        this.make = make;
        this.type = type;
        this.powerUsage = powerUsage;
        repairing = false;
        battery = 100;
    }

    public int getBikeId() {
        return bikeId;
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public double getPrice() {
        return price;
    }

    public int getDockId() {
        return dockId;
    }

    public String getMake() {
        return make;
    }

    public Type getType() {
        return type;
    }

    public double getPowerUsage() {
        return powerUsage;
    }

    public boolean isRepairing() {
        return repairing;
    }

    public boolean isActive(){return active;}

    public void setBikeId(int bikeId) {
        this.bikeId = bikeId;
    }

    public void setPrice(double price) {
        if (price < 0) {throw new IllegalArgumentException("Price cannot be negative.");}
        this.price = price;
    }

    public void setMake(String make) {
        if (make == null) {throw new IllegalArgumentException("Make cannot be null.");}
        this.make = make;
    }

    public void setType(Type type) {
        if (type == null) {throw new IllegalArgumentException("Type cannot be null.");}
        this.type = type;
    }
    public void setPowerUsage(double powerUsage) {
        if (powerUsage < 0) {throw new IllegalArgumentException("Power usage cannot be negative.");}
        this.powerUsage = powerUsage;
    }

    public void setRepairing(boolean repairing) {
        this.repairing = repairing;
    }

    /**
     * Method to deactivate bike (means it is deleted,
     * still has stats). If param is true then it will deactivate.
     * @param deactivate
     */
    public void deactivate(boolean deactivate){active = !deactivate;}

    public void setDockId(int dockId) {
        if (dockId < 0) {throw new IllegalArgumentException("This dock ID is not used in the system.");}
        this.dockId = dockId;
    }


    /**
     * Method to update the battery percent.
     * Subtracts the battery with the argument.
     * @param percent
     * @return int
     */
    public int updateBatteryPercent(int percent) {
        battery -= percent;
        return battery;
    }//end method

    public String toString() {
        String r = "";
        if (repairing) {
            r = "YES";
        } else {
            r = "NO";
        }
        return "Bike ID: " + bikeId + "\n Buy date: " + buyDate + "\n Price: " + price + "\n Dock status: " + dockId
                + "\n Make: " + make + "\n Type: " + type.getName() + "\n Repairing: " + r;
    }

    @Override
    public boolean equals(Object o){
        if (o == null) { throw new IllegalArgumentException("The object you are comparing cannot be null"); }
        if (!(o instanceof Bike)) { throw new IllegalArgumentException("The object you are comparing must be an instance of Bike"); }

        Bike b = (Bike) o;

        return (((Bike) o).getBikeId() == b.getBikeId() && ((Bike) o).getMake().equals(b.getMake()) &&
                ((Bike) o).getPrice() == b.getPrice() && ((Bike) o).getPowerUsage() == b.getPowerUsage() &&
                ((Bike) o).getBuyDate().equals(b.getBuyDate()) && ((Bike) o).isRepairing() == b.isRepairing());

    }

}
