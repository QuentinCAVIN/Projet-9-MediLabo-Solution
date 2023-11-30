package com.projet9.noteservice.controller;

import com.projet9.noteservice.model.Note;
import com.projet9.noteservice.service.NoteService;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/note/patient/{id}")
    public ResponseEntity<List<Note>> getNotesByPatient(@PathVariable("id") int patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getNotesByPatient(patientId));
    }

    @PostMapping("/note")
    public ResponseEntity createNote(@RequestBody Note note) {
        noteService.saveNote(note);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/note")
    public ResponseEntity updateNote(@RequestBody Note note) {
        noteService.updateNote(note);
        return new ResponseEntity(HttpStatus.OK);
        //La présence du patient est vérifiée dans clientui
    }
}