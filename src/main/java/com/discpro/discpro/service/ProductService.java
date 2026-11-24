package com.discpro.discpro.service;

import com.discpro.discpro.model.Product;
import com.discpro.discpro.model.Seller;
import com.discpro.discpro.repository.ProductRepository;
import com.discpro.discpro.repository.SellerRepository;
import com.discpro.discpro.strategy.DiscountStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final Map<String, DiscountStrategy> discountStrategies;

    public ProductService(ProductRepository productRepository,
                          SellerRepository sellerRepository,
                          Map<String, DiscountStrategy> discountStrategies) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.discountStrategies = discountStrategies;
    }

    @Transactional
    public Product createOrUpdateListing(Long sellerId, Product product) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));

        product.setSeller(seller);
        recalculatePrice(product, seller.getDiscountPolicyType());

        return productRepository.save(product);
    }

    public void recalculatePrice(Product product, String policyType) {
        DiscountStrategy strategy = discountStrategies.getOrDefault(
                policyType, discountStrategies.get("STANDARD"));
        BigDecimal rate = strategy.calculateDiscount(product.getExpiryDate());
        product.applyDiscountRate(rate);
    }

    @Transactional
    public List<Product> getActiveLiveFeed() {
        List<Product> active = productRepository
                .findByExpiryDateGreaterThanEqualAndQuantityGreaterThanOrderByExpiryDateAsc(
                        LocalDate.now(), 0);

        for (Product product : active) {
            recalculatePrice(product, product.getSeller().getDiscountPolicyType());
        }
        return active;
    }

    public List<Product> getProductsBySeller(Long sellerId) {
        return productRepository.findBySellerIdOrderByExpiryDateAsc(sellerId);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}