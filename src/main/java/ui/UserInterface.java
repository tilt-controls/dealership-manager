package ui;

import data.ContractDataManager;
import data.DealershipFileManager;
import model.Contract;
import model.Dealership;
import model.SalesContract;
import model.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.*;

public class UserInterface {
    private Dealership dealership;
    static Scanner scanner = new Scanner(System.in);
    private DealershipFileManager fileManager = new DealershipFileManager();

    public UserInterface() {
    }

    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();
    }

    private void displayVehicles(ArrayList<Vehicle> inventory) {
        for (Vehicle vehicle : inventory) {
            System.out.println("\n------------------------------");
            System.out.println(vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel());
            System.out.printf("Price: $%,.2f\n", vehicle.getPrice());
            System.out.println("Color: " + vehicle.getColor());
            System.out.printf("Mileage: %,d\n", vehicle.getOdometer());
            System.out.println("VIN: " + vehicle.getVin());
            System.out.println("------------------------------");
        }
        if (inventory.size() > 0) {
            System.out.println("Cars found: " + inventory.size() + "!");
        } else {
            System.out.println("No cars found!");
        }
    }

    public void display() {
        init();
        String command;
        do {
            System.out.println();
            System.out.println("=== Home ===");
            System.out.println("P) Search by Price");
            System.out.println("M) Search by Make/Model");
            System.out.println("Y) Search by Year");
            System.out.println("C) Search by Color");
            System.out.println("O) Search by Mileage");
            System.out.println("T) Search by Vehicle Type");
            System.out.println("A) Display All Vehicles");
            System.out.println("S) Custom Search");
            System.out.println("N) Add a Vehicle");
            System.out.println("R) Remove a Vehicle");
            System.out.println("E) Sale or Lease Car");
            System.out.println("X) Exit");
            System.out.print("Enter a command: ");
            command = scanner.nextLine().trim().toUpperCase();

            switch (command) {
                case "P" -> processGetByPriceRequest();

                case "M" -> processGetByMakeModelRequest();

                case "Y" -> processGetByYearRequest();

                case "C" -> processGetByColorRequest();

                case "O" -> processGetByMileageRequest();

                case "T" -> processGetByVehicleTypeRequest();

                case "A" -> processGetAllVehiclesRequest();

                case "S" -> processGetByCustomFilter();

                case "N" -> processAddVehicleRequest();

                case "R" -> processRemoveVehicleRequest();

                case "E" -> processContractRequest();

                case "X" -> System.out.println("Good Bye!");

                default -> System.out.println("Unknown Command! Try again.");
            }
        } while(!command.equals("X"));
    }

    public void processGetByCustomFilter() {
        System.out.println("You may enter field or leave blank for all questions!");
        System.out.println("Enter make: ");
        String make = scanner.nextLine().trim();
        if (make.isEmpty()) {
            make = null;
        }

        BigDecimal max = null;
        System.out.println("Enter maximum price: ");
        String maxInput = scanner.nextLine().trim();
        if (maxInput.isEmpty()) {
            maxInput=null;
        } else {
            max = new BigDecimal(maxInput);
            max = max.setScale(2, RoundingMode.HALF_UP);
        }

        System.out.println("Enter color: ");
        String color = scanner.nextLine().trim();
        if (color.isEmpty()) {
            color = null;
        }
        ArrayList<Vehicle> matchedVehicles = dealership.getVehiclesByCustomFilter(make, max, color);
        displayVehicles(matchedVehicles);
        pressEnterToContinue();
    }

    public void processContractRequest() {

        ContractDataManager cdm = new ContractDataManager();
        int vin = promptForInt("Please enter the VIN: ");
        ArrayList<Vehicle> matchedVehicles = dealership.getVehiclesByVin(vin);
        if (matchedVehicles.isEmpty()) {
            System.out.println("No matching vehicles found!");
        } else if (matchedVehicles.size() > 1) {
            displayVehicles(matchedVehicles);
            return;
        }
        Vehicle matchedVehicle = matchedVehicles.get(0);
        String name = promptForString("Enter Name: ");
        String email = promptForString("Enter Email: ");
        String contractType = promptForString("""
                Enter contract type!
                Type sale or lease: """);
        if (contractType.equalsIgnoreCase("sale")) {
            SalesContract contract = processSaleRequest(name, email, matchedVehicle);
            cdm.saveContract(contract);
            displaySaleContract(contract);
        }
        

    }
    public SalesContract processSaleRequest(String name, String email, Vehicle vehicle) {
        System.out.println("""
                Would you like to finance the car?
                Enter true for yes or false for no: """);
        boolean isFinanced = scanner.nextBoolean();
        return new SalesContract(LocalDate.now(), name, email, vehicle, isFinanced);
    }

    public void displaySaleContract(SalesContract contract) {
        if (contract.isFinanced()) {
            System.out.println("Monthly payment: " + contract.getMonthlyPayment());
            System.out.println("Total price: " + contract.getTotalPrice());
        } else {
            System.out.println("Total price: " + contract.getTotalPrice());
        }
    }



    public void processGetByPriceRequest() {
        BigDecimal min = promptForBigDecimal("Please enter minimum price for a vehicle: ");
        BigDecimal max = promptForBigDecimal("Please enter maximum price for a vehicle: ");
        ArrayList<Vehicle> matchedVehicles = dealership.getVehiclesByPrice(min, max);
        displayVehicles(matchedVehicles);
        pressEnterToContinue();
        }

    public void processGetByMakeModelRequest() {
        String make = promptForString("Enter vehicle make: ");
        String model = promptForString("Enter vehicle model: ");
        ArrayList<Vehicle> matchedVehicles = dealership.getVehiclesByMakeModel(make, model);
        displayVehicles(matchedVehicles);
        pressEnterToContinue();
    }

    public void processGetByYearRequest() {
        int min = promptForInt("Please enter minimum year for a vehicle: ");
        int max = promptForInt("Please enter maximum year for a vehicle: ");
        ArrayList<Vehicle> matchedVehicles = dealership.getVehiclesByYear(min, max);
        displayVehicles(matchedVehicles);
        pressEnterToContinue();
    }

    public void processGetByColorRequest() {
        String color = promptForString("Enter vehicle color: ");
        ArrayList<Vehicle> matchedVehicles = dealership.getVehiclesByColor(color);
        displayVehicles(matchedVehicles);
        pressEnterToContinue();
    }

    public void processGetByMileageRequest() {
        int min = promptForInt("Please enter minimum mileage for a vehicle: ");
        int max = promptForInt("Please enter maximum mileage for a vehicle: ");
        ArrayList<Vehicle> matchedVehicles = dealership.getVehiclesByMileage(min, max);
        displayVehicles(matchedVehicles);
        pressEnterToContinue();
    }

    public void processGetByVehicleTypeRequest() {
        String vehicleType = promptForString("Vehicle Type: ");
        ArrayList<Vehicle> matchedVehicles = dealership.getVehiclesByType(vehicleType);
        displayVehicles(matchedVehicles);
        pressEnterToContinue();
    }

    public void processGetAllVehiclesRequest() {
        displayVehicles(dealership.getAllVehicles());
    }

    public void processAddVehicleRequest() {
        int vin = promptForInt("Enter VIN: ");
        int year = promptForInt("Enter year: ");
        String make = promptForString("Enter make: ");
        String model = promptForString("Enter model: ");
        String vehicleType = promptForString("Enter vehicle type: ");
        String color = promptForString("Enter color: ");
        int odometer = promptForInt("Enter odometer: ");
        BigDecimal price = promptForBigDecimal("Enter price: ");
        Vehicle vehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);
        System.out.println("Adding " + vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel() + "!");
        pressEnterToContinue();
        dealership.addVehicle(vehicle);
        fileManager.saveDealership(dealership);
    }

    public void processRemoveVehicleRequest() {
        int vin = promptForInt("Enter VIN to remove vehicle: ");
        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (vehicle.getVin() == vin) {
                dealership.removeVehicle(vehicle);
                System.out.println("Vehicle has been removed!");
                pressEnterToContinue();
                break;
            }
        }
        fileManager.saveDealership(dealership);
    }

    public static void pressEnterToContinue() {
        System.out.println();
        System.out.print("Press Enter to go back to the menu...");
        scanner.nextLine();
    }
    public static BigDecimal promptForBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return new BigDecimal(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
    public static int promptForInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }
    public static String promptForString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("This can't be blank.");
            } else if (input.contains("|")) {
                System.out.println("Please don't use the | character.");
            } else {
                return input;
            }
        }
    }
    }
