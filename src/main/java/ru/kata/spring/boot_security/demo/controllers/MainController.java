package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.sql.SQLException;


@Controller
public class MainController {

    private UserService userService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String home() {
        return "/index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/index";
    }
    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("user", userService.findByName(principal.getName()));
        return "user";
    }
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("allUsers", userService.userList());
        return "admin";
    }
    @GetMapping("/admin/createPage")
    public String newUser(User user) {
        return "create-user";
    }

    @PostMapping("/admin/createPage")
    public String create(User user) {
        userService.save(user);
        return "redirect:/admin";
    }
    @GetMapping("/admin/editPage/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/admin/editPage")
    public String update(User user) throws SQLException {
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Long id) throws SQLException {
        userService.delete(id);
        return "redirect:/admin";
    }
}
