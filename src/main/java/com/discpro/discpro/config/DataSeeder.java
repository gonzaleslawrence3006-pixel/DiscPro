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
            // ID 1 & 2: Partner Stores (Seller Portal is separate / out-of-scope for public buyers)
            Seller cornerMart = sellerRepo.save(new Seller(
                    "Trisha Dimaunahan", "trishaDimaunahan@gmail.com", "pass123",
                    "Aeshachi Sarisari Store", "Brgy Alangilan Batangas City",
                    13.7565, 121.0583, "STANDARD"
            ));

            Seller sunriseBakery = sellerRepo.save(new Seller(
                    "Aldrich Miguel Villanueva", "aldrich123@gmail.com", "pass456",
                    "Leugim Bakeshop", "Brgy Balagtasan Batangas City",
                    13.7975, 121.0709, "BAKERY"
            ));

            // ID 3, 4, 5: Sample Buyer Accounts
            buyerRepo.save(new Buyer(
                    "Lawrence Gonzales", "lawrence.gonzales@gmail.com", "123go", "Brgy Kumintang-Ibaba"
            ));
            buyerRepo.save(new Buyer(
                    "Aldrich Villanueva", "aldrich.buyer@gmail.com", "buyer123", "Brgy Balagtas"
            ));
            buyerRepo.save(new Buyer(
                    "Trisha Dimaunahan", "trisha.buyer@gmail.com", "buyer456", "Brgy Alangilan"
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