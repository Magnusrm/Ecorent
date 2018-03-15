package Control;

public class modelUpdateController {


    public modelUpdateController() {

    }
    // TO DATABASE
    public double bikeXToData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public double bikeYToData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public double bikeKmToData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public int bikeTripsToData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public int bikeBatteryToData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    // FROM DATABASE
    public int bikeBatteryFromData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public double bikeKmFromData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public int bikeTripsFromData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public double bikeXFromData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public double bikeYFromData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }
    public double dockPowerUsagetoData(double powerUsage) {
        if (powerUsage < 0) throw new IllegalArgumentException("Power usage cannot be negative.");
        return 0;
    }
}
