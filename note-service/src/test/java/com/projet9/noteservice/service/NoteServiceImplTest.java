package com.projet9.noteservice.service;

import com.projet9.noteservice.model.Note;
import com.projet9.noteservice.repository.NoteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class NoteServiceImplTest {

    @Mock
    NoteRepository noteRepository;

    @InjectMocks
    NoteServiceImpl noteServiceUnderTest;

    @Test
    public void getNotesByPatientTest(){
        Note dummyNote = getDummyNote();
        List<Note> dummiesNotes = List.of(dummyNote, dummyNote);
        Mockito.when(noteRepository.findByPatId(dummyNote.getPatId())).thenReturn(dummiesNotes);

        List<Note> notes = noteServiceUnderTest.getNotesByPatient(dummyNote.getPatId());

        Assertions.assertThat(notes).isEqualTo(dummiesNotes);
        Mockito.verify(noteRepository,Mockito.times(1)).findByPatId(dummyNote.getPatId());
    }

    @Test
    public void getNotesByIdTest(){
        Note dummyNote = getDummyNote();

        Mockito.when(noteRepository.findById(dummyNote.getId())).thenReturn(Optional.of(dummyNote));

        Note note = noteServiceUnderTest.getNoteById(dummyNote.getId());

        Assertions.assertThat(note).isEqualTo(dummyNote);
        Mockito.verify(noteRepository,Mockito.times(1)).findById(dummyNote.getId());
    }

    @Test
    public void createNoteTest(){
        Note dummyNote = getDummyNote();

        noteServiceUnderTest.createNote(dummyNote);

        Mockito.verify(noteRepository,Mockito.times(1)).insert(dummyNote);
    }

    @Test
    public void updateNotTest(){
        Note dummyNote = getDummyNote();

        noteServiceUnderTest.updateNote(dummyNote);

        Mockito.verify(noteRepository,Mockito.times(1)).save(dummyNote);
    }

    @Test
    public void deleteNoteByIdTest(){
        Note dummyNote = getDummyNote();

        noteServiceUnderTest.deleteNoteById(dummyNote.getId());

        Mockito.verify(noteRepository,Mockito.times(1)).deleteById(dummyNote.getId());
    }

    @Test
    public void deleteNoteByPatientIdTest(){
        Note dummyNote = getDummyNote();
        List<Note> dummiesNotes = List.of(dummyNote,dummyNote);
        Mockito.when(noteRepository.findByPatId(dummyNote.getPatId())).thenReturn(dummiesNotes);

        noteServiceUnderTest.deleteNoteByPatientId(dummyNote.getPatId());

        Mockito.verify(noteRepository,Mockito.times(1)).deleteAll(dummiesNotes);
    }

    /////////////////SET UP///////////////////////

    private Note getDummyNote() {
        Note dummyNote = new Note();
        dummyNote.setId("000000000000000000000001");
        dummyNote.setPatient("Last Name");
        dummyNote.setPatId(1);
        dummyNote.setNote("Note");

        return dummyNote;
    }
}