package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserDetailsServiceImpl;

import java.security.Principal;

@Controller
public class UserController {

    private final UserDetailsServiceImpl userDetailsService;

    public UserController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("user")
    public String showUser(Model model, @ModelAttribute User user, Principal principal) {
        model.addAttribute("user", userDetailsService.loadUserByUsername(principal.getName()));
        return "user";
    }
}