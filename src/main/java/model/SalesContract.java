package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesContract extends Contract{
    private BigDecimal salesTaxAmount;
    private BigDecimal recordingFee;
    private boolean isFinanced;

    public SalesContract(LocalDate contractDate, String customerName, String customerEmail, Vehicle vehicleSold,  boolean isFinanced) {
        super(contractDate, customerName, customerEmail, vehicleSold);
        this.salesTaxAmount = vehicleSold.getPrice().multiply(new BigDecimal("0.05"));
        this.recordingFee = new BigDecimal("100");
        this.isFinanced = isFinanced;
    }

    public BigDecimal getProcessingFee() {
        BigDecimal lowerFee = new BigDecimal("295.00");
        BigDecimal higherFee = new BigDecimal("495.00");
        BigDecimal limit = new BigDecimal("10000.00");
        Vehicle vehicle = getVehicleSold();
        if (vehicle.getPrice().compareTo(limit) < 0) {
            return lowerFee;
        } else {
            return higherFee;
        }
    }

    @Override
    public BigDecimal getTotalPrice() {
        Vehicle vehicle = getVehicleSold();
        return vehicle.getPrice().add(salesTaxAmount).add(recordingFee).add(getProcessingFee());
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        BigDecimal limit = new BigDecimal("10000.00");
        BigDecimal lowerRate = new BigDecimal("4.25");
        BigDecimal higherRate = new BigDecimal("5.25");
        if (!isFinanced) {
            return BigDecimal.ZERO;
        }
        if (isFinanced) {
            if (getTotalPrice().compareTo(limit) >= 0) {
                return LoanCalculator.calculateMonthlyPayment(getTotalPrice(),lowerRate, 48);
            } else if (getTotalPrice().compareTo(limit) < 0) {
                return LoanCalculator.calculateMonthlyPayment(getTotalPrice(), higherRate, 24);
            }
        }
    }

    public BigDecimal getSalesTaxAmount() {
        return salesTaxAmount;
    }

    public void setSalesTaxAmount(BigDecimal salesTaxAmount) {
        this.salesTaxAmount = salesTaxAmount;
    }

    public BigDecimal getRecordingFee() {
        return recordingFee;
    }

    public void setRecordingFee(BigDecimal recordingFee) {
        this.recordingFee = recordingFee;
    }

    public boolean isFinanced() {
        return isFinanced;
    }

    public void setFinanced(boolean financed) {
        isFinanced = financed;
    }
}
