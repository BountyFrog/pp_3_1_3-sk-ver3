package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RegistrationService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AuthController {

    private final UserValidator userValidator;
    private final RegistrationService registrationService;

    public AuthController(UserValidator userValidator, RegistrationService registrationService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/index")
    public String profilePage(Principal principal, Model model) {
        model.addAttribute("person", registrationService.findByUsername(principal.getName()));
        return "index";
    }

    @GetMapping("/login")
    public String welcomeToPage() {
        return "login";
    }

//    @GetMapping("/registration")
//    public String registrationPage(@ModelAttribute("user") User user) {
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String performRegistration(@ModelAttribute("user") @Valid User user,
//                                      BindingResult bindingResult) {
//        userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//        registrationService.add(user);
//        return "redirect:/login";
//    }
}