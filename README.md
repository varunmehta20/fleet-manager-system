# Fleet Management System

## **Description**

This is an **Object-Oriented Fleet Management System** written in Java.  
It manages multiple types of vehicles including **Car, Truck, Bus, Airplane, and CargoShip**, providing features such as:  

- Adding and removing vehicles  
- Tracking speed, fuel, mileage, and maintenance  
- Searching by type and ID  
- Sorting by model, speed, and fuel efficiency  
- Displaying unique and sorted vehicle models  
- Finding fastest and slowest vehicles  
- Saving and loading fleet data via file I/O  
- Generating reports  

This project demonstrates **OOP concepts, Interfaces, Exception Handling, Collections Framework, File I/O, Polymorphism, and Encapsulation**.

---

## **Features**

1. Add / Remove vehicles  
2. Track speed, fuel, mileage, and maintenance  
3. Search vehicles by type + ID  
4. Sort vehicles by model, speed, or efficiency  
5. Display unique and alphabetically sorted models  
6. Find fastest and slowest vehicles  
7. Save and load fleet data  
8. Generate fleet reports  

---

## **How to Compile and Run**

### **Compile**
javac Main/Main.jav
### **Run**
java Main.Main

# Fleet Management System

## Description
This project is an Object-Oriented Fleet Management System written in Java.  
It manages multiple types of vehicles (Car, Truck, Bus, Airplane, CargoShip) and supports:

- Adding / Removing vehicles
- Tracking speed, fuel, and passenger capacity
- Performing maintenance
- Generating reports
- Saving and loading fleet data

---

## Collections Used

- `List<Vehicle> fleet = new ArrayList<>()`  
  Stores all vehicles; allows dynamic resizing, insertion, deletion, and fast iteration.

- `Set<String> uniqueModels = new HashSet<>()`  
  Stores unique vehicle models automatically removing duplicates.

- `Set<String> sortedModels = new TreeSet<>()`  
  Maintains alphabetically sorted vehicle models.

- `Map<String, Integer> modelCount = new HashMap<>()`  
  Counts how many vehicles exist for each model; provides fast key-based lookup.

---

## File I/O

**Saving:**  
Uses `PrintWriter` and `FileWriter` to write vehicle info to a file in CSV-like format.

**Loading:**  
Reads file line by line, parses fields, and reconstructs vehicle objects.



=== Fleet Management Menu ===

- Add Vehicle
- Remove Vehicle
- Start Journey
- Refuel All
- Perform Maintenance
- Generate Report
- Save Fleet
- Load Fleet
- Search by Type + ID
- List Vehicles Needing Maintenance
- Display Unique Models
- Display Sorted Models (Alphabetical)
- Sort Fleet by Efficiency
- Sort Fleet by Speed
- Sort Fleet by Model
- Display Fastest Vehicle
- Display Slowest Vehicle
- Display Model Count
- Exit

## Project Structure

FleetManagementSystem/
│
├─ Main/
│ └─ Main.java
│
├─ Vehicle/
│ ├─ Vehicle.java
│ ├─ Car.java
│ ├─ Truck.java
│ ├─ Bus.java
│ ├─ Airplane.java
│ └─ CargoShip.java
│
├─ Fleet/
│ └─ FleetManager.java
│
├─ Interface/
│ └─ VehicleOperations.java
│
└─ Exceptions/
└─ VehicleNotFoundException.java


---

## Author

**Varun** – Java enthusiast | Object-Oriented Programming | Fleet Management Project



