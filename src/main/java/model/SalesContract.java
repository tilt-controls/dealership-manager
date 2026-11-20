package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesContract extends Contract{
    private BigDecimal salesTaxAmount;
    private BigDecimal recordingFee = new BigDecimal("100.00");
    private boolean isFinanced;

    public SalesContract(LocalDate contractDate, String customerName, String customerEmail, Vehicle vehicleSold,  boolean isFinanced) {
        super(contractDate, customerName, customerEmail, vehicleSold);
        this.isFinanced = isFinanced;
    }

    public BigDecimal getProcessingFee() {
        BigDecimal lowerFee = new BigDecimal("295.00");
        BigDecimal higherFee = new BigDecimal("495.00");
        BigDecimal limit = new BigDecimal("10000.00");
        if (getVehicleSold().getPrice().compareTo(limit) < 0) {
            return lowerFee;
        } else {
            return higherFee;
        }
    }

    @Override
    public BigDecimal getTotalPrice() {
        return getVehicleSold().getPrice().add(getSalesTaxAmount()).add(recordingFee).add(getProcessingFee());
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        BigDecimal limit = new BigDecimal("10000.00");
        BigDecimal lowerRate = new BigDecimal("4.25");
        BigDecimal higherRate = new BigDecimal("5.25");
        if (!isFinanced) {
            return BigDecimal.ZERO;
        }
        if (getTotalPrice().compareTo(limit) >= 0) {
            return LoanCalculator.calculateMonthlyPayment(getTotalPrice(),lowerRate, 48);
        } else {
            return LoanCalculator.calculateMonthlyPayment(getTotalPrice(), higherRate, 24);
        }
    }

    public BigDecimal getSalesTaxAmount() {
        return getVehicleSold().getPrice().multiply(new BigDecimal("0.05")).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getRecordingFee() {
        return recordingFee;
    }

    public boolean isFinanced() {
        return isFinanced;
    }
}
