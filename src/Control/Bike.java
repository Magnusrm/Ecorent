package Control;

import java.time.LocalDate;

public class Bike {
    private final int bikeId;
    private final LocalDate buyDate;
    private int price;
    private int dockId;
    private String make;
    private Type type;
    private double powerUsage;
    private boolean repairing;
 // test
    public Bike(LocalDate buyDate, int price, String make, Type type, double powerUsage) {
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

        this.bikeId = 0;
    }

    public int getBikeId() {
        return bikeId;
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public int getPrice() {
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

    public void setPrice(int price) {
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

    public boolean dock() {
        return false;
    }

    public int updateBatteryPercent(int percent) {
        return 0;
    }

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

    public boolean equals(Object o){
        if (o == null) { throw new IllegalArgumentException("The object you are comparing cannot be null"); }
        if (!(o instanceof Bike)) {
            return false;
        }

        Bike b = (Bike) o;

        return (this.bikeId == b.getBikeId());
        /*
        if (this.bikeId == b.getBikeId()) {
            return true;
        }
        return false;
        */
    }

}
