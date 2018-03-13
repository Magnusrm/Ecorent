package bike;

public class BikeModelController {


    public BikeModelController() {

    }
    // TO DATABASE
    public double xToData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public double yToData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public double kmToData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public int tripsToData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public int batteryToData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    // FROM DATABASE
    public int batteryFromData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public double kmFromData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public int tripsFromData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public double xFromData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }

    public double yFromData(int bikeId) {
        if (bikeId < 0) { throw new IllegalArgumentException("Bike ID must be positive"); }
        return 0;
    }
}
