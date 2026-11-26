package com.discpro.discpro.repository;

import com.discpro.discpro.model.SavedItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SavedItemRepository extends JpaRepository<SavedItem, Long> {
    List<SavedItem> findByBuyerId(Long buyerId);
    Optional<SavedItem> findByBuyerIdAndProductId(Long buyerId, Long productId);
}