package com.example.zametki;

import com.example.zametki.repos.NoteRepo;
import com.example.zametki.repos.UserRepo;
import lombok.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private final String email;
    private final String username;
    private final String password;
    private String firstName;
    private String secondName;
    private String thirdName;
    private Date birthday;
    private Integer age;
    private String description;

    @Transient
    private static Integer maxSavedDeletedNotes = 3;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Note> notes = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public void addNote(Note note) {
        this.notes.add(note);
    }

    public void replaceNote(Note oldversion, Note newversion) {
        int index = notes.indexOf(oldversion);
        notes.set(index, newversion);
    }

    public void hideNote(Note note, NoteRepo noteRepo) { //if false => on delete, if true => on recovery
        if (!note.getIsDeleted()) {
            Note firstOnDelete = null;
            int count = 0;
            for (Note note1 : notes) {
                if (note1.getIsDeleted()) {
                    count++;
                    if (firstOnDelete == null) firstOnDelete = note1;
                }
            }
            if (count == maxSavedDeletedNotes) {
                notes.remove(firstOnDelete);
                noteRepo.delete(noteRepo.findById(firstOnDelete.getChangeId()).get());
            }
        }
        notes.remove(note);
        note.setIsDeleted(!note.getIsDeleted());
        notes.add(note);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

