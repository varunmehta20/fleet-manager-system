package Fleet;
import Exceptions.InvalidOperationException;
import Exceptions.*;
import Vehicle.Vehicle;
public abstract class WaterVehicle extends Vehicle {
    private final boolean hasSail;

    public WaterVehicle(String id, String model, double maxSpeed, double currentMileage, boolean hasSail) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage);
        this.hasSail = hasSail;
    }

    @Override
    public double estimateJourneyTime(double distance) throws InvalidOperationException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance cannot be negative");
        }
        double baseTime = distance / super.getMaxSpeed();
        return baseTime * 1.15;
    }

    @Override
    public abstract void move(double distance) throws InvalidOperationException, InsufficientFuelException;

    @Override
    public abstract double calculateFuelEfficiency();

    public boolean getHasSail() {
        return hasSail;
    }
}
