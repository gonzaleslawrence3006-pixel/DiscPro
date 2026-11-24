package com.discpro.discpro.controller;

import com.discpro.discpro.model.Product;
import com.discpro.discpro.service.ProductService;
import com.discpro.discpro.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/seller")
public class SellerController {

    private final ProductService productService;
    private final UserService userService;

    public SellerController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/{sellerId}/dashboard")
    public String sellerDashboard(@PathVariable Long sellerId, Model model) {
        model.addAttribute("seller", userService.getSeller(sellerId));
        model.addAttribute("products", productService.getProductsBySeller(sellerId));
        model.addAttribute("newProduct", new Product());
        return "seller-dashboard";
    }

    @PostMapping("/{sellerId}/products")
    public String addProduct(@PathVariable Long sellerId,
                             @Valid @ModelAttribute("newProduct") Product product,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("seller", userService.getSeller(sellerId));
            model.addAttribute("products", productService.getProductsBySeller(sellerId));
            return "seller-dashboard";
        }
        productService.createOrUpdateListing(sellerId, product);
        return "redirect:/seller/" + sellerId + "/dashboard";
    }

    @PostMapping("/{sellerId}/products/{productId}/delete")
    public String deleteProduct(@PathVariable Long sellerId, @PathVariable Long productId) {
        productService.deleteProduct(productId);
        return "redirect:/seller/" + sellerId + "/dashboard";
    }
}