package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final UserValidator userValidator;

    public UserController(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("users")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users";
    }

    @GetMapping("show/{id}")
    public String showUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.findOne(id));
        return "show";
    }

    @GetMapping("new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping("create")
    public String createUser(@ModelAttribute("user") User user,
                             BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) return "new";
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userService.findOne(id));
        return "edit";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "edit";
        userService.update(id, user);
        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/users";
    }
}