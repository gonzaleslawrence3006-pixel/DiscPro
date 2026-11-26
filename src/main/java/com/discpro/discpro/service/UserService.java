package com.discpro.discpro.service;

import com.discpro.discpro.model.Buyer;
import com.discpro.discpro.model.Product;
import com.discpro.discpro.model.SavedItem;
import com.discpro.discpro.model.Seller;
import com.discpro.discpro.repository.BuyerRepository;
import com.discpro.discpro.repository.ProductRepository;
import com.discpro.discpro.repository.SavedItemRepository;
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
    private final SavedItemRepository savedItemRepository;

    public UserService(BuyerRepository buyerRepository,
                       SellerRepository sellerRepository,
                       ProductRepository productRepository,
                       SavedItemRepository savedItemRepository) {
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
        this.productRepository = productRepository;
        this.savedItemRepository = savedItemRepository;
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

    public List<SavedItem> getSavedItemsForBuyer(Long buyerId) {
        return savedItemRepository.findByBuyerId(buyerId);
    }

    @Transactional
    public void reserveOneUnit(Long buyerId, Long productId) {
        Buyer buyer = getBuyer(buyerId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (product.getQuantity() <= 0) {
            return; // Out of stock
        }

        product.setQuantity(product.getQuantity() - 1);
        productRepository.save(product);

        SavedItem item = savedItemRepository.findByBuyerIdAndProductId(buyerId, productId)
                .orElseGet(() -> new SavedItem(buyer, product, 0));
        item.incrementQuantity();
        savedItemRepository.save(item);
    }

    @Transactional
    public void decreaseOneUnit(Long buyerId, Long productId) {
        Optional<SavedItem> optItem = savedItemRepository.findByBuyerIdAndProductId(buyerId, productId);
        if (optItem.isEmpty()) return;

        SavedItem item = optItem.get();
        Product product = item.getProduct();
        product.setQuantity(product.getQuantity() + 1);
        productRepository.save(product);

        if (item.getReservedQuantity() <= 1) {
            savedItemRepository.delete(item);
        } else {
            item.decrementQuantity();
            savedItemRepository.save(item);
        }
    }

    @Transactional
    public void removeSavedItemAll(Long buyerId, Long productId) {
        Optional<SavedItem> optItem = savedItemRepository.findByBuyerIdAndProductId(buyerId, productId);
        if (optItem.isEmpty()) return;

        SavedItem item = optItem.get();
        Product product = item.getProduct();
        product.setQuantity(product.getQuantity() + item.getReservedQuantity());
        productRepository.save(product);

        savedItemRepository.delete(item);
    }
}