package com.discpro.discpro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
public class Seller extends User {

    @NotBlank(message = "Store name is required")
    private String storeName;

    @NotBlank(message = "Address is required")
    private String address;

    private Double latitude;
    private Double longitude;
    private String discountPolicyType = "STANDARD";

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public Seller() {}

    public Seller(String name, String email, String password, String storeName,
                  String address, Double latitude, Double longitude, String discountPolicyType) {
        super(name, email, password);
        this.storeName = storeName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.discountPolicyType = discountPolicyType;
    }

    @Override
    public String getRole() { return "SELLER"; }

    public String getStoreName() { return storeName; }
    public void setStoreName(String storeName) { this.storeName = storeName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getDiscountPolicyType() { return discountPolicyType; }
    public void setDiscountPolicyType(String discountPolicyType) { this.discountPolicyType = discountPolicyType; }
    public List<Product> getProducts() { return products; }
}