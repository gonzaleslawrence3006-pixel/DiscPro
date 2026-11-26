package com.discpro.discpro.controller;

import com.discpro.discpro.service.ProductService;
import com.discpro.discpro.service.UserService;
import jakarta.servlet.http.HttpSession;
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

    @GetMapping("/dashboard")
    public String myDashboard(HttpSession session, Model model) {
        Long buyerId = (Long) session.getAttribute("loggedInBuyerId");
        if (buyerId == null) {
            return "redirect:/login";
        }
        model.addAttribute("buyer", userService.getBuyer(buyerId));
        model.addAttribute("deals", productService.getActiveLiveFeed());
        return "buyer-dashboard";
    }

    @PostMapping("/save/{productId}")
    public String toggleSaveItem(@PathVariable Long productId,
                                 HttpSession session,
                                 @RequestHeader(value = "Referer", required = false) String referer) {
        Long buyerId = (Long) session.getAttribute("loggedInBuyerId");
        if (buyerId == null) {
            return "redirect:/login";
        }
        userService.toggleSavedProduct(buyerId, productId);
        return "redirect:" + (referer != null ? referer : "/buyer/dashboard");
    }
}