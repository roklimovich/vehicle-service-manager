package pl.pja.edu.s27619;

import pl.pja.edu.s27619.service.VehicleManager;
import pl.pja.edu.s27619.vehicle.*;
import pl.pja.edu.s27619.vehicle.component.Engine;
import pl.pja.edu.s27619.vehicle.component.EngineType;
import pl.pja.edu.s27619.vehicle.repair.ServiceRecord;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // generate CAR objects
        Engine bmwEngine = new Engine(EngineType.PETROL, 723);
        Car bmw = new Car(VehicleType.CAR, "BMW", "540i", bmwEngine, 5);
        bmw.setSensors("ConnectedDrive"); // multi-valued attribute
        bmw.setSensors("Autopilot"); // multi-valued attribute

        // generate ServiceRecords
        ServiceRecord engineRepair = new ServiceRecord(LocalDate.of(2025, 4, 15),
                "Engine replacement", 1560.0, bmw);
        ServiceRecord oilChange = new ServiceRecord(LocalDate.of(2025, 3, 12),
                "Oil replacement", 240.55, bmw);

        System.out.println("Qualified association test:");
        bmw.getServiceRecordByDate(LocalDate.of(2025, 4, 15))
                .ifPresent(System.out::println);
        bmw.getServiceRecordByDate(LocalDate.of(2025, 3, 12))
                .ifPresent(System.out::println);

        System.out.println("\nCheck reverse connection (ServiceRecord → Vehicle):");
        System.out.println("Vehicle ID from service record: " + engineRepair.getVehicle().getUniqueId());


        System.out.println("\nRemoving one ServiceRecord:");
        bmw.removeServiceRecord(LocalDate.of(2025, 3, 12)); // delete oil replacement
        bmw.displayServiceHistory(); // should be only one service record


        System.out.println("\nRe-adding removed ServiceRecord:");
        bmw.addServiceRecord(oilChange); // add manually service record
        bmw.displayServiceHistory(); // should be here two service records again


        // generate AIRPLANE objects
        Airplane airplane = new Airplane(VehicleType.AIRPLANE, "Boeing", "777",
                new Engine(EngineType.DIESEL, 777), 120);
        airplane.setColor("Gray"); // optional attribute

        // generate TRAIN objects with overloaded constructor
        Train train = new Train(VehicleType.TRAIN, "PKP Intercity", "Stadler", "Gray",
                new Engine(EngineType.ELECTRICITY, 560),
                7);
        train.setSensors("Automatic Increasing Speed"); // multi-valued attribute

        // generate SHIP objects
        Ship ship = new Ship(VehicleType.SHIP, "PJATK Submarine", "YACHT 7778",
                new Engine(EngineType.DIESEL, 800), 3);
        ship.setColor("White"); // optional attribute

        // Extent
        HashMap<String, Vehicle> vehicleList = VehicleManager.getRegisteredVehicles();
        System.out.println("Get registered vehicles:");
        printBasicInfo(vehicleList);

        // Extent-persistency - sve registered vehicles to the file
        VehicleManager.saveRegisteredVehiclesToFile();

        // Removing vehicle from extent
        VehicleManager.deleteVehicle("VehIclE-1");

        System.out.println("Get registered vehicles after removing:");
        printBasicInfo(vehicleList);

        // Extent-persistency - load registered vehicles from file
        VehicleManager.loadRegisteredVehiclesFromFile();
        vehicleList = VehicleManager.getRegisteredVehicles();

        System.out.println("Get registered vehicles from file:");
        printBasicInfo(vehicleList);

        System.out.println();

        // display detailed vehicle info, using Override method in subclasses displayInfo()
        for (Vehicle vehicle : vehicleList.values()) {
            vehicle.displayInfo();
        }

        // Derived attribute
        System.out.println("New unique ID: " + bmw.generateUniqueId());

    }

    public static void printBasicInfo(HashMap<String, Vehicle> vehicleList) {
        if (!vehicleList.isEmpty()) {
            for (Vehicle vehicle : vehicleList.values()) {
                System.out.print("{");
                System.out.print(vehicle.getBasicVehicleInfo());
                System.out.print("} \n");
            }
        } else {
            System.out.println("Vehicle list is empty");
        }
    }
}
