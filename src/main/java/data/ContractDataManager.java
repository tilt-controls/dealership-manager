package data;

import model.Contract;
import model.SalesContract;
import model.Vehicle;
import model.LeaseContract;


import java.io.*;
import java.time.format.DateTimeFormatter;


public class ContractDataManager{

    public void saveContract(Contract contract) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("contracts.csv", true))) {
            if (contract instanceof SalesContract salesContract) {
                writer.write("SALE|");
            } else {
                writer.write("LEASE|");
            }
            writer.write(contract.getContractDate().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "|" +
                    contract.getCustomerName() + "|" +
                    contract.getCustomerEmail() + "|" + contract.getVehicleSold().getVin() + "|" + contract.getVehicleSold().getYear() + "|" + contract.getVehicleSold().getMake() + "|" + contract.getVehicleSold().getModel() + "|" + contract.getVehicleSold().getVehicleType()
                    + "|" + contract.getVehicleSold().getColor() + "|" + contract.getVehicleSold().getOdometer() + "|" + contract.getVehicleSold().getPrice() + "|");
            if (contract instanceof SalesContract salesContract) {
                writer.write(salesContract.getSalesTaxAmount() + "|" + salesContract.getRecordingFee()  + "|" + salesContract.getProcessingFee() + "|" + salesContract.getTotalPrice()
                        + "|" + salesContract.isFinanced() + "|" + salesContract.getMonthlyPayment() + "\n");
            } else if  (contract instanceof LeaseContract leaseContract) {
                writer.write(leaseContract.getExpectedEndingValue() + "|" + leaseContract.getLeaseFee() + "|" + leaseContract.getTotalPrice() + "|" + leaseContract.getMonthlyPayment() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
