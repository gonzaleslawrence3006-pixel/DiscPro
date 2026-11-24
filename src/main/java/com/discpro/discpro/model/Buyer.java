package com.discpro.discpro.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "buyers")
public class Buyer extends User {

    private String neighborhood;

    @ManyToMany
    @JoinTable(
        name = "buyer_saved_products",
        joinColumns = @JoinColumn(name = "buyer_id"),
        inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> savedProducts = new HashSet<>();

    public Buyer() {}

    public Buyer(String name, String email, String password, String neighborhood) {
        super(name, email, password);
        this.neighborhood = neighborhood;
    }

    @Override
    public String getRole() { return "BUYER"; }

    public void saveProduct(Product product) { this.savedProducts.add(product); }
    public void removeSavedProduct(Product product) { this.savedProducts.remove(product); }

    public String getNeighborhood() { return neighborhood; }
    public void setNeighborhood(String neighborhood) { this.neighborhood = neighborhood; }
    public Set<Product> getSavedProducts() { return savedProducts; }
}
