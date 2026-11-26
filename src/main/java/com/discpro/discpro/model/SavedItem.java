package com.discpro.discpro.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "buyer_saved_items",
       uniqueConstraints = @UniqueConstraint(columnNames = {"buyer_id", "product_id"}))
public class SavedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int reservedQuantity;

    public SavedItem() {}

    public SavedItem(Buyer buyer, Product product, int reservedQuantity) {
        this.buyer = buyer;
        this.product = product;
        this.reservedQuantity = reservedQuantity;
    }

    public void incrementQuantity() {
        this.reservedQuantity++;
    }

    public void decrementQuantity() {
        if (this.reservedQuantity > 0) {
            this.reservedQuantity--;
        }
    }

    public BigDecimal getSubtotal() {
        return product.getDiscountedPrice().multiply(BigDecimal.valueOf(reservedQuantity));
    }

    public Long getId() { return id; }
    public Buyer getBuyer() { return buyer; }
    public Product getProduct() { return product; }
    public int getReservedQuantity() { return reservedQuantity; }
    public void setReservedQuantity(int reservedQuantity) { this.reservedQuantity = reservedQuantity; }
}