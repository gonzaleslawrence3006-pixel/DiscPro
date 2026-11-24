package com.discpro.discpro.controller;

import com.discpro.discpro.service.ProductService;
import com.discpro.discpro.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProductService productService;
    private final UserService userService;

    public HomeController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "DiscPro");
        model.addAttribute("products", productService.getActiveLiveFeed());
        model.addAttribute("stores", userService.findStores(null));
        return "home";
    }
}