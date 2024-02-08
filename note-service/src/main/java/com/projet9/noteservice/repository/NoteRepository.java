package com.projet9.noteservice.repository;

import com.projet9.noteservice.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends MongoRepository<Note, String> {
    public List<Note> findByPatId(int patId);
}
