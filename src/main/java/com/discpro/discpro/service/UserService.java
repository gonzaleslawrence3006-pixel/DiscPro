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