package com.projet9.noteservice.service;

import com.projet9.noteservice.model.Note;
import com.projet9.noteservice.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private NoteRepository noteRepository;

    public NoteServiceImpl (NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public List<Note> getNotesByPatient(int patientId){
        return noteRepository.findByPatId(patientId);
    }

    public Note getNoteById (String id) {
        return noteRepository.findById(id).get();
    }
    //La présence de la note est vérifiée dans clientui

    public void createNote(Note note){
        noteRepository.insert(note);
    }

    public void updateNote(Note note){
        noteRepository.save(note);
    }

    public void deleteNoteById(String id){
        noteRepository.deleteById(id);
    }

    public void deleteNoteByPatientId(int patientId) {
        List<Note> allPatientsNotes = noteRepository.findByPatId(patientId);
        noteRepository.deleteAll(allPatientsNotes);
    }
}