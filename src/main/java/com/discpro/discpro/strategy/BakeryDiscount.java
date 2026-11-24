package com.discpro.discpro.strategy;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component("BAKERY")
public class BakeryDiscount implements DiscountStrategy {

    @Override
    public BigDecimal calculateDiscount(LocalDate expiryDate) {
        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
        if (daysLeft < 0) return BigDecimal.ONE;
        if (daysLeft == 0) return new BigDecimal("0.75");
        if (daysLeft <= 2) return new BigDecimal("0.50");
        if (daysLeft <= 4) return new BigDecimal("0.25");
        return BigDecimal.ZERO;
    }

    @Override
    public String getStrategyName() {
        return "Fast-Moving Bakery Schedule";
    }
}
