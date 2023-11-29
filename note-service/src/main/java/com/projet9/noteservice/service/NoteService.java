package com.projet9.noteservice.service;

import com.projet9.noteservice.model.Note;

import java.util.List;

public interface NoteService {
    List<Note> getNotesByPatient(int patientId);
}
