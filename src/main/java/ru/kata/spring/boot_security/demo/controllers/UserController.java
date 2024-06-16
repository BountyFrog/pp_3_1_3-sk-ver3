package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "users/users";
    }

    @GetMapping("show")
    public String showUser(@RequestParam(value = "id") int id, Model model) {
        User user = userService.findOne(id);
        model.addAttribute("user", user);
        return "users/show";
    }

    @GetMapping("new")
    public String newUser(@ModelAttribute("user") User user) {
        return "/users/new";
    }

    @PostMapping("create")
    public String createUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/users";
    }

    @GetMapping("edit")
    public String edit(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("user", userService.findOne(user.getId()));
        return "/users/edit";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("user") User user, @RequestParam(value = "id") int id) {
        userService.update(id, user);
        return "redirect:/users";
    }

    @PostMapping("delete")
    public String delete(@RequestParam(value = "id") int id) {
        userService.delete(id);
        return "redirect:/users";
    }
}