package model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class LoanCalculator {

    // Returns the monthly payment on a loan, rounded to cents.
    // amount:            how much is borrowed, like 18237.15
    // yearlyRatePercent: the yearly interest rate as a percent, like 4.0 for 4.0%
    // months:            how many monthly payments, like 36
    public static BigDecimal calculateMonthlyPayment(BigDecimal amount, BigDecimal yearlyRatePercent, int months) {
        BigDecimal monthlyRate = yearlyRatePercent.divide(new BigDecimal("1200"), 20, RoundingMode.HALF_UP);
        BigDecimal growth = BigDecimal.ONE.add(monthlyRate).pow(months);

        return amount.multiply(monthlyRate)
                .multiply(growth)
                .divide(growth.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
    }
}
