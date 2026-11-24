package com.discpro.discpro.strategy;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component("STANDARD")
public class StandardDiscount implements DiscountStrategy {

    @Override
    public BigDecimal calculateDiscount(LocalDate expiryDate) {
        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
        if (daysLeft < 0) return BigDecimal.ONE;
        if (daysLeft <= 1) return new BigDecimal("0.60");
        if (daysLeft <= 7) return new BigDecimal("0.30");
        return BigDecimal.ZERO;
    }

    @Override
    public String getStrategyName() {
        return "Standard Grocery Schedule";
    }
}
