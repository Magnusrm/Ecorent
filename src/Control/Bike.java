package Control;

import java.time.LocalDate;

public class Bike {
    private final int bikeId;
    private final LocalDate buyDate;
    private int price;
    private int dockId;
    private String make;
    private Type type;
    private final double powerUsage;
    private boolean repairing;
 // test
    public Bike(LocalDate buyDate, int price, String make, Type type, double powerUsage) {
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
        this.price = price;
    }

    public void setDockId(int dockId) {
        this.dockId = dockId;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setType(Type type) {
        this.type = type;
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
        if ((o instanceof Bike)) {
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
