package com.example.zametki.controllers;

import com.example.zametki.RegisterForm;
import com.example.zametki.User;
import com.example.zametki.repos.UserRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/myprofile")
public class ProfileController {

    private UserRepo userRepo;

    public ProfileController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String showProfile() {
        return "myprofile";
    }

    @ModelAttribute(name = "userForm")
    public RegisterForm userForm(@AuthenticationPrincipal User user) {
        return new RegisterForm(user.getFirstName(), user.getSecondName(), user.getThirdName(),
                user.getAge(), user.getDescription(), user.getBirthday());
    }

    @PostMapping(value = "/updateUser")
    public String updateUser(@AuthenticationPrincipal User user, @ModelAttribute RegisterForm form) {
        form.updateUser(user);
        userRepo.save(user);
        return "redirect:/myprofile";
    }

}
