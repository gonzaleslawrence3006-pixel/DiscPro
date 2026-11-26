package com.discpro.discpro.controller;

import com.discpro.discpro.model.Buyer;
import com.discpro.discpro.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("loggedInBuyerId") != null) {
            return "redirect:/buyer/dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String identifier,
                               @RequestParam String password,
                               HttpSession session,
                               Model model) {
        try {
            Buyer buyer = userService.authenticateBuyer(identifier, password);
            session.setAttribute("loggedInBuyerId", buyer.getId());
            session.setAttribute("loggedInBuyerName", buyer.getName());
            return "redirect:/buyer/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String name,
                                  @RequestParam String email,
                                  @RequestParam String password,
                                  @RequestParam String neighborhood,
                                  HttpSession session,
                                  Model model) {
        try {
            Buyer created = userService.registerBuyer(name, email, password, neighborhood);
            session.setAttribute("loggedInBuyerId", created.getId());
            session.setAttribute("loggedInBuyerName", created.getName());
            return "redirect:/buyer/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("registerError", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }
}