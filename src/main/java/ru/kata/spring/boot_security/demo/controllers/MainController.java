package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
public class MainController {
    private UserServiceImpl userServiceImpl;

    @Autowired
    public void setUserService(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/user")
    public String userPage(Principal principal) {
        userServiceImpl.findByName(principal.getName());
        return "user";
    }

    @GetMapping("/admin")
    public String adminPage(Principal principal) {
        return "admin";
    }
}
