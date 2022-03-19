package com.example.zametki.controllers;

import com.example.zametki.RegisterForm;
import com.example.zametki.repos.UserRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/register")
public class RegistrationController {

    private PasswordEncoder encoder;
    private UserRepo userRepo;

    public RegistrationController(PasswordEncoder encoder, UserRepo userRepo) {
        this.encoder = encoder;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String showRegistration() {
        return "registration";
    }

    @PostMapping
    public String processReg(RegisterForm form) {
        userRepo.save(form.toUser(encoder));
        return "redirect:/login";
    }
}
