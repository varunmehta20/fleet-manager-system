package Main;

import Exceptions.*;
import Fleet.FleetManager;
import Vehicle.*;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static FleetManager fleetManager = new FleetManager();

    public static void main(String[] args)
            throws InvalidOperationException, OverloadException, InsufficientFuelException, IOException {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Fleet Management Menu ===");
            System.out.println("1. Add Vehicle");
            System.out.println("2. Remove Vehicle");
            System.out.println("3. Start Journey");
            System.out.println("4. Refuel All");
            System.out.println("5. Perform Maintenance");
            System.out.println("6. Generate Report");
            System.out.println("7. Save Fleet");
            System.out.println("8. Load Fleet");
            System.out.println("9. Search by Type + ID");
            System.out.println("10. List Vehicles Needing Maintenance");
            System.out.println("11. Display Unique Models");
            System.out.println("12. Display Sorted Models (Alphabetical)");
            System.out.println("13. Sort Fleet by Efficiency");
            System.out.println("14. Sort Fleet by Speed");
            System.out.println("15. Sort Fleet by Model");
            System.out.println("16. Display Fastest Vehicle");
            System.out.println("17. Display Slowest Vehicle");
            System.out.println("18. Display Model Count");
            System.out.println("19. Exit");

            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> addVehicle(sc);

                case 2 -> {
                    System.out.print("Enter Vehicle Type (Car/Truck/Bus/Airplane/CargoShip): ");
                    String typeInput = sc.nextLine().trim().toLowerCase();
                    System.out.print("Enter Vehicle ID to remove: ");
                    String id = sc.nextLine();
                    try {
                        switch (typeInput) {
                            case "car" -> fleetManager.removeVehicle(id, Car.class);
                            case "truck" -> fleetManager.removeVehicle(id, Truck.class);
                            case "bus" -> fleetManager.removeVehicle(id, Bus.class);
                            case "airplane" -> fleetManager.removeVehicle(id, Airplane.class);
                            case "cargoship" -> fleetManager.removeVehicle(id, CargoShip.class);
                            default -> System.out.println("Invalid type entered!");
                        }
                        System.out.println("Vehicle removed successfully!");
                    } catch (InvalidOperationException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 3 -> {
                    try {
                        System.out.print("Enter distance for journey: ");
                        double distance = sc.nextDouble();
                        sc.nextLine();
                        fleetManager.startAllJourneys(distance);
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid distance. Must be a number.");
                        sc.nextLine();
                    }
                }

                case 4 -> {
                    System.out.print("Enter amount of fuel to add (in liters): ");
                    double amt = sc.nextDouble();
                    try {
                        fleetManager.refuelAll(amt);
                    } catch (InvalidOperationException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 5 -> fleetManager.performMaintenance();

                case 6 -> System.out.println(fleetManager.generateReport());

                case 7 -> {
                    System.out.print("Enter filename to save fleet: ");
                    String saveFile = sc.nextLine();
                    fleetManager.saveToFile(saveFile);
                }

                case 8 -> {
                    System.out.print("Enter filename to load fleet: ");
                    String loadFile = sc.nextLine();
                    fleetManager.loadFromFile(loadFile);
                }

                case 9 -> {
                    System.out.print("Enter vehicle type to search (Car/Truck/Bus/Airplane/CargoShip): ");
                    String typeInput2 = sc.nextLine().trim().toLowerCase();
                    System.out.print("Enter ID also: ");
                    String t2 = sc.nextLine().trim();
                    try {
                        switch (typeInput2) {
                            case "car" -> fleetManager.searchByType(Car.class, t2);
                            case "truck" -> fleetManager.searchByType(Truck.class, t2);
                            case "bus" -> fleetManager.searchByType(Bus.class, t2);
                            case "airplane" -> fleetManager.searchByType(Airplane.class, t2);
                            case "cargoship" -> fleetManager.searchByType(CargoShip.class, t2);
                            default -> System.out.println("Invalid type entered!");
                        }
                    } catch (InvalidOperationException e) {
                        System.out.println(e.getMessage());
                    }
                }

                case 10 -> fleetManager.displayVehiclesNeedingMaintenance();

                case 11 -> fleetManager.displayUniqueModels();

                case 12 -> fleetManager.displaySortedModels();

                case 13 -> fleetManager.sortFleetByEfficiency();

                case 14 -> fleetManager.sortFleetBySpeed();

                case 15 -> fleetManager.sortFleetByModel();

                case 16 -> {
                    Vehicle fast = fleetManager.getFastestVehicle();
                    System.out.println("Fastest Vehicle: " + fast.getModel() + " (" + fast.getMaxSpeed() + " km/h)");
                }

                case 17 -> {
                    Vehicle slow = fleetManager.getSlowestVehicle();
                    System.out.println("Slowest Vehicle: " + slow.getModel() + " (" + slow.getMaxSpeed() + " km/h)");
                }
                case 18 -> {
                    fleetManager.displayModelCounts();
                }

                case 19 -> {
                    running = false;
                    System.out.println("Exiting...");
                }


                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        sc.close();
    }

    private static void addVehicle(Scanner sc) throws InvalidOperationException {
        System.out.print("Enter type of vehicle (Car/Truck/Bus/Airplane/CargoShip): ");
        String type = sc.nextLine();

        if (type.equalsIgnoreCase("car") || type.equalsIgnoreCase("truck") ||
                type.equalsIgnoreCase("bus") || type.equalsIgnoreCase("airplane") ||
                type.equalsIgnoreCase("cargoship")) {

            try {
                System.out.print("Enter ID: ");
                String id = sc.nextLine();
                System.out.print("Enter Model: ");
                String model = sc.nextLine();
                System.out.print("Enter Max Speed: ");
                double maxSpeed = sc.nextDouble();
                System.out.print("Enter Fuel Level: ");
                double fuel = sc.nextDouble();
                sc.nextLine();

                Vehicle v = null;

                switch (type.toLowerCase()) {
                    case "car" -> {
                        System.out.print("Enter number of wheels: ");
                        int wheels = sc.nextInt();
                        if (wheels < 4) throw new InvalidOperationException("Wheels can't be less than 4");

                        System.out.print("Enter passenger capacity: ");
                        int cap = sc.nextInt();
                        if (cap < 0) throw new InvalidOperationException("Capacity can't be negative");
                        sc.nextLine();

                        v = new Car(id, model, maxSpeed, 0.0, wheels);
                        Car c = (Car) v;
                        c.refuel(fuel);
                        c.boardPassengers(cap);
                    }

                    case "truck" -> {
                        System.out.print("Enter number of wheels: ");
                        int twheels = sc.nextInt();
                        if (twheels < 4) throw new InvalidOperationException("Wheels can't be less than 4");
                        System.out.print("Enter cargo capacity: ");
                        double cargCap = sc.nextDouble();
                        if (cargCap < 0) throw new InvalidOperationException("Capacity can't be negative");
                        sc.nextLine();

                        v = new Truck(id, model, maxSpeed, 0.0, twheels);
                        Truck t = (Truck) v;
                        t.refuel(fuel);
                        t.loadCargo(cargCap);
                    }

                    case "bus" -> {
                        System.out.print("Enter number of wheels: ");
                        int bwheels = sc.nextInt();
                        if (bwheels <= 3) throw new InvalidOperationException("Wheels can't be less than 4");

                        System.out.print("Enter passenger capacity: ");
                        int bpassenger = sc.nextInt();
                        if (bpassenger < 0) throw new InvalidOperationException("Passengers can't be negative");

                        System.out.print("Enter cargo capacity: ");
                        double bcargo = sc.nextDouble();
                        if (bcargo < 0) throw new InvalidOperationException("Capacity can't be negative");
                        sc.nextLine();

                        v = new Bus(id, model, maxSpeed, 0.0, bwheels);
                        Bus b = (Bus) v;
                        b.refuel(fuel);
                        b.boardPassengers(bpassenger);
                        b.loadCargo(bcargo);
                    }

                    case "airplane" -> {
                        System.out.print("Max Altitude: ");
                        double span = sc.nextDouble();
                        if (span < 0) throw new InvalidOperationException("Altitude can't be negative");
                        sc.nextLine();

                        v = new Airplane(id, model, maxSpeed, 0.0, span);
                        Airplane a = (Airplane) v;
                        a.refuel(fuel);
                    }

                    case "cargoship" -> {
                        System.out.print("Sail? (true/false): ");
                        boolean sail = sc.nextBoolean();
                        sc.nextLine();

                        v = new CargoShip(id, model, maxSpeed, 0.0, sail);
                        CargoShip cs = (CargoShip) v;
                        if (!sail) {
                            cs.refuel(fuel);
                        }
                    }
                }

                if (v != null) {
                    fleetManager.addVehicle(v);
                    System.out.println(type + " added successfully!");
                }

            } catch (InputMismatchException | InvalidOperationException | OverloadException e) {
                System.out.println("Error: " + e.getMessage());
                sc.nextLine();
            }

        } else {
            throw new InvalidOperationException("No such vehicle type.");
        }
    }
}
