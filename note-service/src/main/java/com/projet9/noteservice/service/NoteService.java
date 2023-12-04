package com.projet9.noteservice.service;

import com.projet9.noteservice.model.Note;

import java.util.List;

public interface NoteService {
    List<Note> getNotesByPatient(int patientId);
    Note getNoteById (String id);
    void createNote(Note note);
    void updateNote(Note note);
    void deleteNoteById(String id);
    void deleteNoteByPatientId(int patientId);
}
