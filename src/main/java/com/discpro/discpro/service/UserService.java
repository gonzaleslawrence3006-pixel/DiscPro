package com.discpro.discpro.service;

import com.discpro.discpro.model.Buyer;
import com.discpro.discpro.model.Product;
import com.discpro.discpro.model.Seller;
import com.discpro.discpro.repository.BuyerRepository;
import com.discpro.discpro.repository.ProductRepository;
import com.discpro.discpro.repository.SellerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    public UserService(BuyerRepository buyerRepository,
                       SellerRepository sellerRepository,
                       ProductRepository productRepository) {
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
    }

    public Buyer authenticateBuyer(String identifier, String password) {
        if (identifier == null || identifier.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("User ID/Email and password are required.");
        }

        Optional<Buyer> buyerOpt;
        String trimmed = identifier.trim();

        if (trimmed.matches("\\d+")) {
            buyerOpt = buyerRepository.findById(Long.parseLong(trimmed));
        } else {
            buyerOpt = buyerRepository.findAll().stream()
                    .filter(b -> b.getEmail().equalsIgnoreCase(trimmed))
                    .findFirst();
        }

        Buyer buyer = buyerOpt.orElseThrow(() ->
                new IllegalArgumentException("No Buyer account found for that User ID or Email."));

        if (!buyer.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password.");
        }
        return buyer;
    }

    @Transactional
    public Buyer registerBuyer(String name, String email, String password, String neighborhood) {
        boolean emailExists = buyerRepository.findAll().stream()
                .anyMatch(b -> b.getEmail().equalsIgnoreCase(email.trim()));
        if (emailExists) {
            throw new IllegalArgumentException("An account with this email already exists.");
        }
        Buyer newBuyer = new Buyer(name.trim(), email.trim(), password, neighborhood.trim());
        return buyerRepository.save(newBuyer);
    }

    public Buyer getBuyer(Long id) {
        return buyerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Buyer not found"));
    }

    public Seller getSeller(Long id) {
        return sellerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));
    }

    public List<Seller> findStores(String addressQuery) {
        if (addressQuery == null || addressQuery.isBlank()) {
            return sellerRepository.findAll();
        }
        return sellerRepository.findByAddressContainingIgnoreCase(addressQuery);
    }

    @Transactional
    public void toggleSavedProduct(Long buyerId, Long productId) {
        Buyer buyer = getBuyer(buyerId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (buyer.getSavedProducts().contains(product)) {
            buyer.removeSavedProduct(product);
        } else {
            buyer.saveProduct(product);
        }
        buyerRepository.save(buyer);
    }
}