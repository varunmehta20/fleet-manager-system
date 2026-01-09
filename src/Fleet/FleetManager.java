package Fleet;

import Exceptions.*;
import Interface.*;
import Vehicle.*;
import java.util.*;
import java.io.*;

public class FleetManager {


    private List<Vehicle> fleet = new ArrayList<>();
    private Set<String> uniqueModels = new HashSet<>();
    private Set<String> sortedModels = new TreeSet<>();
    private Map<String, Integer> modelCount = new HashMap<>();




    public void addVehicle(Vehicle v) throws InvalidOperationException {
        for (Vehicle existing : fleet) {
            if (existing.getId().equals(v.getId()) &&
                    existing.getClass().equals(v.getClass())) {
                throw new InvalidOperationException(
                        "Vehicle with id " + v.getId() + " and type " + v.getClass().getSimpleName() + " already exists."
                );
            }
        }
        fleet.add(v);
        uniqueModels.add(v.getModel());
        sortedModels.add(v.getModel());
        modelCount.put(v.getModel(), modelCount.getOrDefault(v.getModel(), 0) + 1);

    }

    public void removeVehicle(String id, Class<?> type) throws InvalidOperationException {
        boolean removed = false;
        Iterator<Vehicle> iterator = fleet.iterator();
        while (iterator.hasNext()) {
            Vehicle v = iterator.next();
            if (v.getId().equals(id) && v.getClass().equals(type)) {
                iterator.remove();
                uniqueModels.remove(v.getModel());
                sortedModels.remove(v.getModel());
                modelCount.put(v.getModel(), modelCount.get(v.getModel()) - 1);
                if (modelCount.get(v.getModel()) == 0) modelCount.remove(v.getModel());

                removed = true;
                break;
            }
        }
        if (!removed) {
            throw new InvalidOperationException("Vehicle with id " + id + " and type " + type.getSimpleName() + " not found.");
        }
    }
    public void displayModelCounts() {
        System.out.println("Model Count:");
        for (String model : modelCount.keySet()) {
            System.out.println(model + " -> " + modelCount.get(model));
        }
    }


    public void displayUniqueModels() {
        System.out.println("Unique models in the fleet:");
        for (String model : uniqueModels) {
            System.out.println(" - " + model);
        }
    }

    public void displaySortedModels() {
        System.out.println("Models in alphabetical order:");
        for (String model : sortedModels) {
            System.out.println(" - " + model);
        }
    }
    public void displayFleet() {
        if (fleet.isEmpty()) {
            System.out.println("Fleet is empty.");
            return;
        }

        System.out.println("\n=== Fleet Vehicles ===");
        for (Vehicle v : fleet) {
            System.out.println(
                    v.getClass().getSimpleName() + " | ID: " + v.getId() +
                            " | Model: " + v.getModel() +
                            " | Speed: " + v.getMaxSpeed() + " km/h" +
                            " | Mileage: " + v.getCurrentMileage() +
                            " | Efficiency: " + v.calculateFuelEfficiency()
            );
        }
    }



    public void sortFleetByEfficiency() {
        fleet.sort(Comparator.comparingDouble(Vehicle::calculateFuelEfficiency).reversed());
        System.out.println("Fleet sorted by fuel efficiency (descending).");
        displayFleet();
    }

    public void sortFleetBySpeed() {
        fleet.sort(Comparator.comparingDouble(Vehicle::getMaxSpeed).reversed());
        System.out.println("Fleet sorted by top speed (descending).");
        displayFleet();
    }

    public void sortFleetByModel() {
        fleet.sort(Comparator.comparing(Vehicle::getModel));
        System.out.println("Fleet sorted alphabetically by model name.");
        displayFleet();

    }

    public Vehicle getFastestVehicle() {
        return Collections.max(fleet, Comparator.comparingDouble(Vehicle::getMaxSpeed));
    }

    public Vehicle getSlowestVehicle() {
        return Collections.min(fleet, Comparator.comparingDouble(Vehicle::getMaxSpeed));
    }

    public void refuelAll(double amount) throws InvalidOperationException {
        if (amount <= 0) throw new InvalidOperationException("Refuel amount must be positive!");
        for (Vehicle v : fleet) {
            if (v instanceof FuelConsumable fc) {
                fc.refuel(amount);
            }
        }
        System.out.println("All fuel-based vehicles refueled by " + amount + " liters.");
    }

    public void startAllJourneys(double distance) {
        for (Vehicle v : fleet) {
            try {
                v.move(distance);
            } catch (InsufficientFuelException e) {
                System.out.println(v.getModel() + " has insufficient fuel.");
            } catch (InvalidOperationException e) {
                System.out.println(v.getModel() + " cannot move: " + e.getMessage());
            }
        }
    }

    public void performMaintenance() {
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable m) {
                m.performMaintenance();
            }
        }
        System.out.println("Maintenance performed on all applicable vehicles.");
    }


    public void searchByType(Class<?> type, String id) throws InvalidOperationException {
        boolean found = false;
        for (Vehicle v : fleet) {
            if (type.isInstance(v) && v.getId().equals(id)) {
                System.out.println("Vehicle found:");
                System.out.println("Model: " + v.getModel());
                System.out.println("Mileage: " + v.getCurrentMileage());
                System.out.println("Efficiency: " + v.calculateFuelEfficiency());
                System.out.println("Max Speed: " + v.getMaxSpeed());
                found = true;
                break;
            }
        }
        if (!found) throw new InvalidOperationException("No vehicle with that ID and type found.");
    }

    public String generateReport() {
        StringBuilder report = new StringBuilder("=== Fleet Summary ===\n");
        report.append("Total vehicles: ").append(fleet.size()).append("\n");

        double totalEfficiency = 0;
        double totalMileage = 0;
        int maintenanceNeeded = 0;

        Map<String, Integer> typeCount = new HashMap<>();
        for (Vehicle v : fleet) {
            totalEfficiency += v.calculateFuelEfficiency();
            totalMileage += v.getCurrentMileage();
            typeCount.merge(v.getClass().getSimpleName(), 1, Integer::sum);

            if (v instanceof Maintainable m && m.needsMaintenance()) {
                maintenanceNeeded++;
            }
        }

        for (String type : typeCount.keySet()) {
            report.append(type).append(": ").append(typeCount.get(type)).append("\n");
        }

        if (!fleet.isEmpty()) {
            report.append("Average fuel efficiency: ").append(totalEfficiency / fleet.size()).append(" km/l\n");
        }
        report.append("Total distance covered: ").append(totalMileage).append(" km\n");
        report.append("Vehicles needing maintenance: ").append(maintenanceNeeded).append("\n");

        if (!fleet.isEmpty()) {
            Vehicle fast = getFastestVehicle();
            Vehicle slow = getSlowestVehicle();
            report.append("Fastest vehicle: ").append(fast.getModel()).append(" (").append(fast.getMaxSpeed()).append(" km/h)\n");
            report.append("Slowest vehicle: ").append(slow.getModel()).append(" (").append(slow.getMaxSpeed()).append(" km/h)\n");
        }

        return report.toString();
    }

    public void displayVehiclesNeedingMaintenance() {
        for (Vehicle v : fleet) {
            if (v instanceof Maintainable m && m.needsMaintenance()) {
                System.out.println("ID: " + v.getId());
                System.out.println("Model: " + v.getModel());
                System.out.println("Mileage: " + v.getCurrentMileage());
                System.out.println("Max speed: " + v.getMaxSpeed());
                System.out.println();
            }
        }
    }

    public void saveToFile(String filename) {
        try (PrintWriter PW = new PrintWriter(new FileWriter(filename))) {
            for (Vehicle object : fleet) {
                String line = "";
                if (object instanceof Car obj1) {
                    line = object.getClass().getSimpleName() + "," +
                            obj1.getId() + "," +
                            obj1.getModel() + "," +
                            obj1.getMaxSpeed() + "," +
                            obj1.getCurrentMileage() + "," +
                            obj1.getNumWheels() + "," +
                            obj1.getFuelLevel() + "," +
                            obj1.getPassengerCapacity() + "," +
                            obj1.getCurrentPassengers();

                } else if (object instanceof Truck obj2) {
                    line = object.getClass().getSimpleName() + "," +
                            obj2.getId() + "," +
                            obj2.getModel() + "," +
                            obj2.getMaxSpeed() + "," +
                            obj2.getCurrentMileage() + "," +
                            obj2.getNumWheels() + "," +
                            obj2.getFuelLevel() + "," +
                            obj2.getCargoCapacity() + "," +
                            obj2.getCurrentCargo();

                } else if (object instanceof Bus obj3) {
                    line = object.getClass().getSimpleName() + "," +
                            obj3.getId() + "," +
                            obj3.getModel() + "," +
                            obj3.getMaxSpeed() + "," +
                            obj3.getCurrentMileage() + "," +
                            obj3.getNumWheels() + "," +
                            obj3.getFuelLevel() + "," +
                            obj3.getPassengerCapacity() + "," +
                            obj3.getCurrentPassengers() + "," +
                            obj3.getCargoCapacity() + "," +
                            obj3.getCurrentCargo();

                } else if (object instanceof Airplane obj4) {
                    line = object.getClass().getSimpleName() + "," +
                            obj4.getId() + "," +
                            obj4.getModel() + "," +
                            obj4.getMaxSpeed() + "," +
                            obj4.getCurrentMileage() + "," +
                            obj4.getMaxAltitude() + "," +
                            obj4.getFuelLevel() + "," +
                            obj4.getPassengerCapacity() + "," +
                            obj4.getCurrentPassengers() + "," +
                            obj4.getCargoCapacity() + "," +
                            obj4.getCurrentCargo();

                } else if (object instanceof CargoShip obj5) {
                    line = object.getClass().getSimpleName() + "," +
                            obj5.getId() + "," +
                            obj5.getModel() + "," +
                            obj5.getMaxSpeed() + "," +
                            obj5.getCurrentMileage() + "," +
                            obj5.getHasSail() + "," +
                            obj5.getFuelLevel() + "," +
                            obj5.getCargoCapacity() + "," +
                            obj5.getCurrentCargo();
                }
                PW.println(line);
            }
            System.out.println(filename + " has been saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    public void loadFromFile(String filename)
            throws InvalidOperationException, InsufficientFuelException, OverloadException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                String type = parts[0];
                Vehicle v = null;

                switch (type) {
                    case "Car" -> {
                        v = new Car(parts[1], parts[2],
                                Double.parseDouble(parts[3]),
                                Double.parseDouble(parts[4]),
                                Integer.parseInt(parts[5]));
                        Car c = (Car) v;
                        c.refuel(Double.parseDouble(parts[6]));
                        try {
                            c.boardPassengers(Integer.parseInt(parts[8]));
                        } catch (OverloadException e) {
                            System.out.println("Overloaded Car while loading, skipping extra passengers");
                        }
                    }
                    case "Truck" -> {
                        v = new Truck(parts[1], parts[2],
                                Double.parseDouble(parts[3]),
                                Double.parseDouble(parts[4]),
                                Integer.parseInt(parts[5]));
                        Truck t = (Truck) v;
                        t.refuel(Double.parseDouble(parts[6]));
                        t.loadCargo(Double.parseDouble(parts[8]));
                    }
                    case "Bus" -> {
                        v = new Bus(parts[1], parts[2],
                                Double.parseDouble(parts[3]),
                                Double.parseDouble(parts[4]),
                                Integer.parseInt(parts[5]));
                        Bus b = (Bus) v;
                        b.refuel(Double.parseDouble(parts[6]));
                        try {
                            b.boardPassengers(Integer.parseInt(parts[8]));
                        } catch (OverloadException e) {
                            System.out.println("Overloaded Bus while loading, skipping extra passengers");
                        }
                        b.loadCargo(Double.parseDouble(parts[10]));
                    }
                    case "Airplane" -> {
                        v = new Airplane(parts[1], parts[2],
                                Double.parseDouble(parts[3]),
                                Double.parseDouble(parts[4]),
                                Double.parseDouble(parts[5]));
                        Airplane a = (Airplane) v;
                        a.refuel(Double.parseDouble(parts[6]));
                        try {
                            a.boardPassengers(Integer.parseInt(parts[8]));
                        } catch (OverloadException e) {
                            System.out.println("Overloaded Airplane while loading, skipping extra passengers");
                        }
                        a.loadCargo(Double.parseDouble(parts[10]));
                    }
                    case "CargoShip" -> {
                        v = new CargoShip(parts[1], parts[2],
                                Double.parseDouble(parts[3]),
                                Double.parseDouble(parts[4]),
                                Boolean.parseBoolean(parts[5]));
                        CargoShip cs = (CargoShip) v;
                        cs.refuel(Double.parseDouble(parts[6]));
                        cs.loadCargo(Double.parseDouble(parts[8]));
                    }
                }

                if (v != null) {
                    fleet.add(v);
                    uniqueModels.add(v.getModel());
                    sortedModels.add(v.getModel());
                }
            }

            System.out.println("Fleet loaded successfully from " + filename);

        } catch (FileNotFoundException e) {
            System.out.println("Could not find the file: " + filename);
        } catch (IOException e) {
            System.out.println("Problem reading the file: " + e.getMessage());
        }
    }
}
