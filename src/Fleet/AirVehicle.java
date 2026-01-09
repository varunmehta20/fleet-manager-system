package Fleet;
import Exceptions.*;
import Vehicle.*;

public abstract class AirVehicle extends Vehicle {
    private double maxAltitude;

    public AirVehicle(String id, String model, double maxSpeed, double currentMileage, double maxAltitude) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage);
        if (maxAltitude <= 0) {
            throw new InvalidOperationException("Max altitude must be positive");
        }
        this.maxAltitude = maxAltitude;
    }

    @Override
    public double estimateJourneyTime(double distance) throws InvalidOperationException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance cannot be negative");
        }
        double baseTime = distance / super.getMaxSpeed();
        return baseTime * 0.95;
    }

    @Override
    public abstract void move(double distance) throws InvalidOperationException, InsufficientFuelException;

    @Override
    public abstract double calculateFuelEfficiency();

    public double getMaxAltitude() {
        return maxAltitude;
    }
}

