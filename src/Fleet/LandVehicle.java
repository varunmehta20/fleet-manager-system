package Fleet;
import Exceptions.InsufficientFuelException;
import Exceptions.InvalidOperationException;
import Vehicle.Vehicle;
public abstract class LandVehicle extends Vehicle {
    private int numWheels;

    public LandVehicle(String id, String model, double maxSpeed, double currentMileage, int numWheels) throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage);
        if (numWheels <= 0) {
            throw new InvalidOperationException("Number of wheels must be positive");
        }
        this.numWheels = numWheels;
    }

    @Override
    public double estimateJourneyTime(double distance) throws InvalidOperationException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance cannot be negative");
        }
        double baseTime = distance / super.getMaxSpeed();
        return baseTime * 1.1;
    }

    @Override
    public abstract void move(double distance) throws InvalidOperationException, InsufficientFuelException;

    @Override
    public abstract double calculateFuelEfficiency();

    public int getNumWheels() {
        return numWheels;
    }
}
