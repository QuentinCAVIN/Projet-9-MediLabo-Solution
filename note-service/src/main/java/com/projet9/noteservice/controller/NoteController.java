package com.projet9.noteservice.controller;

import com.projet9.noteservice.model.Note;
import com.projet9.noteservice.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/note/patient")
    public ResponseEntity<List<Note>> getNotesByPatient(@RequestParam String patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(noteService.getNotesByPatient(patientId));
    }
}