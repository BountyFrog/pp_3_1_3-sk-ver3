package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.AdminService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/")
public class AdminController {

    private final AdminService adminService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(AdminService adminService, UserValidator userValidator) {
        this.adminService = adminService;
        this.userValidator = userValidator;
    }

    @GetMapping("{id}")
    public String showUserDetails(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", adminService.findOne(id));
        return "admin/show";
    }

    @GetMapping("users")
    public String showAllUsers(Model model) {
        System.out.println("1");
        model.addAttribute("users", adminService.findAll());
        System.out.println("1");
        return "admin/users";
    }

    @GetMapping("new")
    public String createNewUser(@ModelAttribute("user") User user) {
        return "admin/new";
    }

    @PostMapping("create")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult) {
        System.out.println("Контроллер запущен.");
        userValidator.validate(user, bindingResult);
        System.out.println("Валидатор пройден.");
        if (bindingResult.hasErrors()) return "admin/new";
        System.out.println("Ошибок нет.");
        adminService.add(user);
        System.out.println("Пользовател сохранен.");
        return "redirect:users";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", adminService.findOne(id));
        return "admin/edit";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute("person") @Valid User user,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) return "admin/edit";
        adminService.update(id, user);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        adminService.delete(id);
        return "redirect:/admin/users";
    }
}