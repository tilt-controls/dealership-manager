package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeaseContract extends Contract {
    private BigDecimal expectedEndingValue;
    private BigDecimal leaseFee;

    public LeaseContract(LocalDate contractDate, String customerName, String customerEmail, Vehicle vehicleSold, BigDecimal expectedEndingValue, BigDecimal leaseFee) {
        super(contractDate, customerName, customerEmail, vehicleSold);
        this.expectedEndingValue = getVehicleSold().getPrice().divide(new BigDecimal("2.00"));
        this.leaseFee = getVehicleSold().getPrice().multiply(new BigDecimal("0.07"));
    }

    @Override
    public BigDecimal getTotalPrice() {
        return getExpectedEndingValue().add(getLeaseFee());
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        return null;
    }

    public BigDecimal getExpectedEndingValue() {
        return expectedEndingValue;
    }

    public void setExpectedEndingValue(BigDecimal expectedEndingValue) {
        this.expectedEndingValue = expectedEndingValue;
    }

    public BigDecimal getLeaseFee() {
        return leaseFee;
    }

    public void setLeaseFee(BigDecimal leaseFee) {
        this.leaseFee = leaseFee;
    }
}
