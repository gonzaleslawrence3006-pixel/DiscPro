package com.discpro.discpro.strategy;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DiscountStrategy {
    BigDecimal calculateDiscount(LocalDate expiryDate);
    String getStrategyName();
}