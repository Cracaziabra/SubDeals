package com.example.zametki.repos;

import com.example.zametki.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepo extends CrudRepository<Note, Long> {


    void removeByChangeId(Long changeId);
}
