package com.example.zametki.controllers;

import com.example.zametki.RegisterForm;
import com.example.zametki.User;
import com.example.zametki.repos.UserRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

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

    @ModelAttribute(name = "testForm")
    public RegisterForm regForm() {
        return new RegisterForm();
    }

    @PostMapping
    public String processReg(@ModelAttribute(name = "testForm") @Valid RegisterForm form, BindingResult result) {
        if (result.hasErrors()) return "/registration";
        User user = form.toUser(encoder);
        if (userRepo.findByUsername(user.getUsername()) != null) {
            FieldError error = new FieldError("form", "username", "Такое имя пользователя уже занято.");
            result.addError(error);
            return "/registration";
        }
        if (userRepo.findByEmail(user.getEmail()) != null) {
            FieldError error = new FieldError("form", "email", "Аккаунт с таким адресом электронной почты уже зарегистрирован");
            result.addError(error);
            return "/registration";
        }
        userRepo.save(user);
        return "redirect:/authmenu";
    }
}
