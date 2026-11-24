package com.discpro.discpro.repository;

import com.discpro.discpro.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findByAddressContainingIgnoreCase(String address);
}