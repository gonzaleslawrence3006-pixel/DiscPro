package com.discpro.discpro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Factory barcode is required")
    @Pattern(regexp = "^[0-9]{8,14}$", message = "Barcode must be 8 to 14 digits")
    private String barcode;

    @NotBlank(message = "Product name is required")
    private String name;

    private String category;

    @NotNull(message = "Original price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than zero")
    private BigDecimal originalPrice;

    private BigDecimal discountedPrice;
    private BigDecimal discountRate = BigDecimal.ZERO;

    @NotNull(message = "Expiry date is required")
    @FutureOrPresent(message = "Cannot list already expired food")
    private LocalDate expiryDate;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "seller_id", nullable = false)
private Seller seller;

    public Product() {}

    public void setBarcode(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) {
            throw new IllegalArgumentException("Only factory-sealed barcoded goods are permitted.");
        }
        this.barcode = barcode.trim();
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        if (originalPrice == null || originalPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Original price must be positive.");
        }
        this.originalPrice = originalPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public void setExpiryDate(LocalDate expiryDate) {
        if (expiryDate == null || expiryDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Expiry date cannot be in the past.");
        }
        this.expiryDate = expiryDate;
    }

    public void applyDiscountRate(BigDecimal rate) {
        if (rate.compareTo(BigDecimal.ZERO) < 0 || rate.compareTo(BigDecimal.ONE) > 0) {
            throw new IllegalArgumentException("Discount rate must be between 0.0 and 1.0");
        }
        this.discountRate = rate;
        BigDecimal multiplier = BigDecimal.ONE.subtract(rate);
        this.discountedPrice = this.originalPrice.multiply(multiplier).setScale(2, RoundingMode.HALF_UP);
    }

    public int getDiscountPercentageDisplay() {
        return discountRate.multiply(new BigDecimal("100")).intValue();
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(this.expiryDate);
    }

    public Long getId() { return id; }
    public String getBarcode() { return barcode; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public BigDecimal getOriginalPrice() { return originalPrice; }
    public BigDecimal getDiscountedPrice() { return discountedPrice; }
    public BigDecimal getDiscountRate() { return discountRate; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public Seller getSeller() { return seller; }
    public void setSeller(Seller seller) { this.seller = seller; }
}