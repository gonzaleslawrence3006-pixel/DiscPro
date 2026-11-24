package com.discpro.discpro.repository;

import com.discpro.discpro.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByExpiryDateGreaterThanEqualAndQuantityGreaterThanOrderByExpiryDateAsc(
            LocalDate date, int minQuantity);

    List<Product> findBySellerIdOrderByExpiryDateAsc(Long sellerId);

    List<Product> findByNameContainingIgnoreCaseAndExpiryDateGreaterThanEqual(
            String keyword, LocalDate date);
}