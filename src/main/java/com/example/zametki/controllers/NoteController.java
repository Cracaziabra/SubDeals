package com.example.zametki.controllers;

import com.example.zametki.Note;
import com.example.zametki.User;
import com.example.zametki.repos.NoteRepo;
import com.example.zametki.repos.UserRepo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping()
public class NoteController {

    private NoteRepo noteRepo;
    private UserRepo userRepo;

    public NoteController(NoteRepo noteRepo, UserRepo userRepo){
        this.noteRepo = noteRepo;
        this.userRepo = userRepo;
    }

    @PostMapping(value = "/createnote")
    @Transactional
    public String createNote(@Valid Note note, @AuthenticationPrincipal User authUser) {
        User user = userRepo.findById(authUser.getId()).get();
        if (note.getChangeId() == null) {
            noteRepo.save(note);
            note.setChangeId(note.getId());
            user.addNote(note);
        } else {
            Note note1 = noteRepo.findById(note.getChangeId()).get();
            user.replaceNote(note1, note);
            noteRepo.delete(note1);
        }
        userRepo.save(user);
        return "redirect:/notes";
    }

    @PostMapping(value = "/deletenote")
    public String updateNotes(Note note, @AuthenticationPrincipal User authUser) {
        User user = userRepo.findById(authUser.getId()).get();
        Note note1 = noteRepo.findById(note.getChangeId()).get();
        user.deleteNote(note1);
        noteRepo.delete(note1);
        userRepo.save(user);
        return "redirect:/notes";
    }
}
