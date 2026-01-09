package Vehicle;
import Fleet.*;
import Interface.*;

import Exceptions.*;


public class Truck extends LandVehicle implements FuelConsumable, CargoCarrier, Maintainable {
    private double fuelLevel = 0.0;
    private final double cargoCapacity = 5000.0; // kg
    private double currentCargo = 0.0;
    private boolean maintenanceNeeded = false;
    private double lastMaintenanceMileage = 0.0; // track mileage at last maintenance

    public Truck(String id, String model, double maxSpeed, double currentMileage, int numWheels)
            throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage, numWheels);
    }

    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance cannot be negative.");
        }
        consumeFuel(distance);
        addMileage(distance);
        System.out.println("Hauling cargo distance: " + distance+"fuel consumed"+consumeFuel(distance));
    }

    @Override
    public double calculateFuelEfficiency() {
        double baseEfficiency = 8.0;

        if (currentCargo > 0.5 * cargoCapacity) {
            baseEfficiency *= 0.9;
        }
        return baseEfficiency;
    }


    @Override
    public void refuel(double amount) throws InvalidOperationException {
        if (amount <= 0) throw new InvalidOperationException("Fuel amount must be positive.");
        fuelLevel += amount;
    }

    @Override
    public double getFuelLevel() {
        return fuelLevel;
    }

    @Override
    public double consumeFuel(double distance) throws InsufficientFuelException {
        double efficiency = calculateFuelEfficiency();
        double required = distance / efficiency;
        if (fuelLevel < required) {
            throw new InsufficientFuelException("Not enough fuel for the journey.");
        }
        fuelLevel -= required;
        return required;
    }


    @Override
    public void loadCargo(double weight) throws OverloadException, InvalidOperationException {
        if (weight <= 0) {
            throw new InvalidOperationException("Cargo weight must be positive.");
        }
        if (currentCargo + weight > cargoCapacity) {
            throw new OverloadException("Cargo capacity exceeded!");
        }
        currentCargo += weight;
    }

    @Override
    public void unloadCargo(double weight) throws InvalidOperationException {
        if (weight <= 0) {
            throw new InvalidOperationException("Cargo weight must be positive.");
        }
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
        System.out.println("Vehicle.Vehicle.Truck maintenance performed. Ready to haul!");
    }
    public double getLastMaintenanceMileage(){
        return lastMaintenanceMileage;
    }
}
