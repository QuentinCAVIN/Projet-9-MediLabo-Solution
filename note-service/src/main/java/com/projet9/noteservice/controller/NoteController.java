package com.projet9.noteservice.controller;

import com.projet9.noteservice.model.Note;
import com.projet9.noteservice.service.NoteService;
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
    public ResponseEntity<List<Note>> getNotesByPatientId(@PathVariable("id") int patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getNotesByPatient(patientId));
    }

    @GetMapping("/note/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getNoteById(id));
    }
    //La présence de la note est vérifiée dans clientui

    @PostMapping("/note")
    public ResponseEntity createNote(@RequestBody Note note) {
        noteService.createNote(note);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    //TODO : VOir avec Vincent si c'est une bonne pratique d'utiliser uniquement la méthode .save du Repository MongoDB
    // Voir Proxy dans clientui
    @PutMapping("/note")
    public ResponseEntity updateNote(@RequestBody Note note) {
        noteService.updateNote(note);
        return new ResponseEntity(HttpStatus.OK);
        //La présence du patient est vérifiée dans clientui
    }

    @DeleteMapping("/note/{id}")
    public ResponseEntity deleteNoteById(@PathVariable("id") String id) {
        noteService.deleteNoteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/note/patient/{id}")
    public ResponseEntity deleteNoteByPatient(@PathVariable("id")int patientId) {
        noteService.deleteNoteByPatientId(patientId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}