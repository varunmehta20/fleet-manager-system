package Vehicle;
import Fleet.*;
import Interface.*;
import Exceptions.*;

public class CargoShip extends WaterVehicle implements CargoCarrier, Maintainable, FuelConsumable {
    private final double cargoCapacity = 50000;
    private double currentCargo;
    private boolean maintenanceNeeded;
    private double fuelLevel;
    private double lastMaintenanceMileage = 0.0;

    public CargoShip(String id,String model,double MaxSpeed,double currentMileage,boolean  hasSail) throws InvalidOperationException {
        super(id, model, MaxSpeed, currentMileage, hasSail);
        this.currentCargo = 0.0;
        this.maintenanceNeeded = false;
        this.fuelLevel = 0.0;

    }


    @Override
    public void move(double distance) throws InvalidOperationException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance cannot be negative.");
        }

        if (!getHasSail()) {
            try {
                consumeFuel(distance);
            } catch (InsufficientFuelException e) {
                throw new InvalidOperationException("Cannot sail: " + e.getMessage());
            }
        }

        addMileage(distance);
        System.out.println("Cargo ship sailed " + distance + " km with cargo.");
    }

    @Override
    public double calculateFuelEfficiency() {

        if (getHasSail()) {
            return 0.0;
        } else {
            return 4.0;
        }

    }


    @Override
    public void loadCargo(double weight) throws OverloadException, InvalidOperationException {
        if (weight <= 0) throw new InvalidOperationException("Cargo weight must be positive.");
        if (currentCargo + weight > cargoCapacity) {
            throw new OverloadException("Cargo capacity exceeded!");
        }
        currentCargo += weight;
    }


    @Override
    public void unloadCargo(double weight) throws InvalidOperationException {
        if (weight <= 0) throw new InvalidOperationException("Cargo weight must be positive.");
        if (weight > currentCargo) {
            throw new InvalidOperationException("Cannot unload more than current cargo.");
        }
        currentCargo -= weight;
    }


    @Override
    public double getCargoCapacity() {
        return cargoCapacity;
    }

    @Override
    public double getCurrentCargo() {
        return currentCargo;
    }

    @Override
    public void scheduleMaintenance() {
        maintenanceNeeded = true;
    }

    @Override
    public boolean needsMaintenance() {
        return (getCurrentMileage() - lastMaintenanceMileage) > 10000 || maintenanceNeeded;
    }

    @Override
    public void performMaintenance() {
        maintenanceNeeded = false;
        lastMaintenanceMileage = getCurrentMileage();
        System.out.println("Vehicle.Vehicle.CargoShip maintenance performed. Ready for service!");
    }


    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if (getHasSail()) {
            throw new InvalidOperationException("This ship uses a sail, cannot refuel.");
        }
        if (amount <= 0) {
            throw new InvalidOperationException("Fuel amount must be positive.");
        }
        fuelLevel += amount;
    }

    @Override
    public double getFuelLevel() {
        return fuelLevel;
    }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException {
        if (getHasSail()) {
            return 0.0;
        }
        double efficiency = calculateFuelEfficiency();
        double required = distance / efficiency;
        if (fuelLevel < required) {
            throw new InsufficientFuelException("Not enough fuel for the journey.");
        }
        fuelLevel -= required;
        return required;
    }
    public double getLastMaintenanceMileage(){
        return lastMaintenanceMileage;
    }
}
