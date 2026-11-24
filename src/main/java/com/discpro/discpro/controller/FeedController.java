package com.discpro.discpro.controller;

import com.discpro.discpro.model.Seller;
import com.discpro.discpro.service.ProductService;
import com.discpro.discpro.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FeedController {

    private final ProductService productService;
    private final UserService userService;

    public FeedController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/feed")
    public String liveFeed(Model model) {
        model.addAttribute("products", productService.getActiveLiveFeed());
        return "feed";
    }

    @GetMapping("/stores")
    public String storeFinder(@RequestParam(required = false) String address, Model model) {
        model.addAttribute("stores", userService.findStores(address));
        model.addAttribute("searchQuery", address);
        return "stores";
    }

    @GetMapping("/api/stores")
    @ResponseBody
    public List<Object> getStoresGeoJson(@RequestParam(required = false) String address) {
        List<Object> result = new ArrayList<>();
        for (Seller seller : userService.findStores(address)) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", seller.getId());
            map.put("storeName", seller.getStoreName());
            map.put("address", seller.getAddress());
            map.put("latitude", seller.getLatitude());
            map.put("longitude", seller.getLongitude());
            map.put("policy", seller.getDiscountPolicyType());
            result.add(map);
        }
        return result;
    }
}