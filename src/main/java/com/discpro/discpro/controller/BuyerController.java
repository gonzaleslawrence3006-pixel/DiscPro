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
        model.addAttribute("savedItems", userService.getSavedItemsForBuyer(buyerId));
        model.addAttribute("deals", productService.getActiveLiveFeed());
        return "buyer-dashboard";
    }

    @PostMapping("/save/{productId}")
    public String reserveOne(@PathVariable Long productId,
                             HttpSession session,
                             @RequestHeader(value = "Referer", required = false) String referer) {
        Long buyerId = (Long) session.getAttribute("loggedInBuyerId");
        if (buyerId == null) {
            return "redirect:/login";
        }
        userService.reserveOneUnit(buyerId, productId);
        return "redirect:" + (referer != null ? referer : "/buyer/dashboard");
    }

    @PostMapping("/decrease/{productId}")
    public String decreaseOne(@PathVariable Long productId,
                              HttpSession session,
                              @RequestHeader(value = "Referer", required = false) String referer) {
        Long buyerId = (Long) session.getAttribute("loggedInBuyerId");
        if (buyerId == null) {
            return "redirect:/login";
        }
        userService.decreaseOneUnit(buyerId, productId);
        return "redirect:" + (referer != null ? referer : "/buyer/dashboard");
    }

    @PostMapping("/remove/{productId}")
    public String removeAll(@PathVariable Long productId,
                            HttpSession session,
                            @RequestHeader(value = "Referer", required = false) String referer) {
        Long buyerId = (Long) session.getAttribute("loggedInBuyerId");
        if (buyerId == null) {
            return "redirect:/login";
        }
        userService.removeSavedItemAll(buyerId, productId);
        return "redirect:" + (referer != null ? referer : "/buyer/dashboard");
    }
}