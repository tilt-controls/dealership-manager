package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeaseContract extends Contract {

    public LeaseContract(LocalDate contractDate, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(contractDate, customerName, customerEmail, vehicleSold);
    }

    @Override
    public BigDecimal getTotalPrice() {
        return getExpectedEndingValue().add(getLeaseFee());
    }

    @Override
    public BigDecimal getMonthlyPayment() {
        BigDecimal interestRate = new BigDecimal("4.00");
        return LoanCalculator.calculateMonthlyPayment(getTotalPrice(), interestRate, 36);
    }

    public BigDecimal getExpectedEndingValue() {
        return getVehicleSold().getPrice().divide(new BigDecimal("2.00")).setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    public BigDecimal getLeaseFee() {
        return getVehicleSold().getPrice().multiply(new BigDecimal("0.07")).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
