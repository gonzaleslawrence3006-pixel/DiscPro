package com.discpro.discpro.controller;

import com.discpro.discpro.service.ProductService;
import com.discpro.discpro.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/buyer")
public class BuyerController {

    private final UserService userService;
    private final ProductService productService;

    public BuyerController(UserService userService, ProductService productService) {
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping("/{buyerId}/dashboard")
    public String buyerDashboard(@PathVariable Long buyerId, Model model) {
        model.addAttribute("buyer", userService.getBuyer(buyerId));
        model.addAttribute("deals", productService.getActiveLiveFeed());
        return "buyer-dashboard";
    }

    @PostMapping("/{buyerId}/save/{productId}")
    public String toggleSaveItem(@PathVariable Long buyerId,
                                 @PathVariable Long productId,
                                 @RequestHeader(value = "Referer", required = false) String referer) {
        userService.toggleSavedProduct(buyerId, productId);
        return "redirect:" + (referer != null ? referer : "/buyer/" + buyerId + "/dashboard");
    }
}