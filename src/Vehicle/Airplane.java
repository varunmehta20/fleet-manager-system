package Vehicle;
import Fleet.*;
import Interface.*;

import Exceptions.*;

public class Airplane extends AirVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable {
    private double fuelLevel;


    private final int passengerCapacity = 200;
    private int currentPassengers;


    private final double cargoCapacity = 10000;
    private double currentCargo;


    private boolean maintenanceNeeded;
    private double lastMaintenanceMileage = 0.0;

    public Airplane(String id, String model, double maxSpeed, double currentMileage, double maxAltitude)
            throws InvalidOperationException {
        super(id, model, maxSpeed, currentMileage, maxAltitude);
        this.fuelLevel = 0.0;
        this.currentPassengers = 0;
        this.currentCargo = 0.0;
        this.maintenanceNeeded = false;
    }

    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        if (distance < 0) {
            throw new InvalidOperationException("Distance cannot be negative.");
        }
        consumeFuel(distance);
        addMileage(distance);
        System.out.println("Flying at " + getMaxSpeed() + " km/h..."+"fuel consumed"+consumeFuel(distance));
    }

    @Override
    public double calculateFuelEfficiency() {
        double efficiency = 5.0;

        return efficiency;
    }


    @Override
    public void refuel(double litres) throws InvalidOperationException {
        if (litres <= 0) throw new InvalidOperationException("Fuel amount must be positive.");
        fuelLevel += litres;
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
    public void boardPassengers(int count) throws OverloadException, InvalidOperationException {
        if (count <= 0) throw new InvalidOperationException("Passenger count must be positive.");
        if (currentPassengers + count > passengerCapacity) {
            throw new OverloadException("Passenger capacity exceeded!");
        }
        currentPassengers += count;
    }

    @Override
    public void disembarkPassengers(int count) throws InvalidOperationException {
        if (count <= 0) throw new InvalidOperationException("Passenger count must be positive.");
        if (count > currentPassengers) {
            throw new InvalidOperationException("Cannot disembark more passengers than present.");
        }
        currentPassengers -= count;
    }

    @Override
    public int getPassengerCapacity() {
        return passengerCapacity;
    }

    @Override
    public int getCurrentPassengers() {
        return currentPassengers;
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
        System.out.println("Vehicle.Vehicle.Airplane maintenance performed. Ready for service!");
    }
    public double getLastMaintenanceMileage(){
        return lastMaintenanceMileage;
    }
}
