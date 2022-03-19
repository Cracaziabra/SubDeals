package com.example.zametki.controllers;

import com.example.zametki.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class MenuController {

    @GetMapping(value = "/notes")
    public String notes() {
        return "notes";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/menu")
    public String showMenu() {
        return "menu";
    }

}
