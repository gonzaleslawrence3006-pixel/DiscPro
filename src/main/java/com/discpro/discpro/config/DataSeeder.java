package com.discpro.discpro.config;

import com.discpro.discpro.model.Buyer;
import com.discpro.discpro.model.Product;
import com.discpro.discpro.model.Seller;
import com.discpro.discpro.repository.BuyerRepository;
import com.discpro.discpro.repository.SellerRepository;
import com.discpro.discpro.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(SellerRepository sellerRepo,
                                   BuyerRepository buyerRepo,
                                   ProductService productService) {
        return args -> {
            Seller cornerMart = sellerRepo.save(new Seller(
                    "Maria Santos", "maria@cornermart.local", "pass123",
                    "Santos Neighborhood Mart", "123 Rizal Ave, Brgy. San Isidro",
                    14.0273, 121.5920, "STANDARD"
            ));

            Seller sunriseBakery = sellerRepo.save(new Seller(
                    "Carlos Reyes", "carlos@sunrisebakery.local", "pass123",
                    "Sunrise Artisan Bakery", "45 Quezon St, Poblacion",
                    14.0261, 121.5935, "BAKERY"
            ));

            buyerRepo.save(new Buyer(
                    "Ana Cruz", "ana@resident.local", "pass123", "Brgy. San Isidro"
            ));

            Product milk = new Product();
            milk.setBarcode("4800016644801");
            milk.setName("Full Cream Fresh Milk 1L");
            milk.setCategory("Dairy");
            milk.setOriginalPrice(new BigDecimal("95.00"));
            milk.setExpiryDate(LocalDate.now().plusDays(1));
            milk.setQuantity(8);
            productService.createOrUpdateListing(cornerMart.getId(), milk);

            Product bread = new Product();
            bread.setBarcode("4800112233445");
            bread.setName("Whole Wheat Loaf (Sealed)");
            bread.setCategory("Bread");
            bread.setOriginalPrice(new BigDecimal("72.00"));
            bread.setExpiryDate(LocalDate.now().plusDays(2));
            bread.setQuantity(12);
            productService.createOrUpdateListing(sunriseBakery.getId(), bread);
        };
    }
}