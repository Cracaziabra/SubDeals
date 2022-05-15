package com.example.zametki.controllers;

import com.example.zametki.Note;
import com.example.zametki.User;
import com.example.zametki.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthMenuController {

    private UserRepo userRepo;

    @Autowired
    public AuthMenuController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping(value = "/notes")
    public String notes() {
        return "notes";
    }

    @GetMapping(value = "/authmenu")
    public String showAuthmenu() {
        return "authmenu";
    }

    @GetMapping(value = "/history")
    public String showHistory() {
        return "history";
    }

    @ModelAttribute(name = "note")
    public Note note() {
        return new Note();
    }

    @ModelAttribute(name = "priorities")
    public List<String> priorities() {
        List<String> pros = new ArrayList<>();
        pros.add("Очень важное");
        pros.add("Важное");
        pros.add("Обычное");
        return pros;
    }

    @ModelAttribute(name = "notes")
    public ArrayList<Note> notes(@AuthenticationPrincipal User authuser) {
        User user = userRepo.findById(authuser.getId()).get();
        return new ArrayList<Note>(user.getNotes());
    }

}
