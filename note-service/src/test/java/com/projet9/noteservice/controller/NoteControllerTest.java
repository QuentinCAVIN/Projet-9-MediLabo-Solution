package com.projet9.noteservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet9.noteservice.model.Note;
import com.projet9.noteservice.service.LoadDataService;
import com.projet9.noteservice.service.NoteService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;


@WebMvcTest(controllers = NoteController.class)
@AutoConfigureMockMvc(addFilters = false)
public class NoteControllerTest {

    @MockBean
    private LoadDataService loadDataService;
    @Autowired
    MockMvc mockMvc;
    @MockBean
    NoteService noteService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getNotesByPatientIdTest() throws Exception {
        Note dummyNote = getDummyNote();
        List<Note> dummiesNotes = List.of(dummyNote, dummyNote);
        Mockito.when(noteService.getNotesByPatient(dummyNote.getPatId())).thenReturn(dummiesNotes);

        mockMvc.perform(MockMvcRequestBuilders.get("/note/patient/{id}", dummyNote.getPatId()))

                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dummiesNotes)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(noteService, Mockito.times(1)).getNotesByPatient(dummyNote.getPatId());
    }

    @Test
    public void getNoteByIdTest() throws Exception {
        Note dummyNote = getDummyNote();

        Mockito.when(noteService.getNoteById(dummyNote.getId())).thenReturn(dummyNote);

        mockMvc.perform(MockMvcRequestBuilders.get("/note/{id}", dummyNote.getId()))

                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dummyNote)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(noteService, Mockito.times(1)).getNoteById(dummyNote.getId());
    }

    @Test
    public void createNoteTest() throws Exception {
        Note dummyNote = getDummyNote();

        mockMvc.perform(MockMvcRequestBuilders.post("/note").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyNote)))

                .andExpect(MockMvcResultMatchers.status().isCreated());
        ArgumentCaptor<Note> captor = ArgumentCaptor.forClass(Note.class);
        Mockito.verify(noteService, Mockito.times(1)).createNote(captor.capture());
        System.out.println(captor.getValue());
    }

    @Test
    public void updateNoteTest() throws Exception {
        Note dummyNote = getDummyNote();

        mockMvc.perform(MockMvcRequestBuilders.put("/note")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyNote)))

                .andExpect(MockMvcResultMatchers.status().isOk());
        ArgumentCaptor<Note> captor = ArgumentCaptor.forClass(Note.class);
        Mockito.verify(noteService, Mockito.times(1)).updateNote(captor.capture());
    }

    @Test
    public void deleteNotByIdTest() throws Exception {
        Note dummyNote = getDummyNote();

        mockMvc.perform(MockMvcRequestBuilders.delete("/note/{id}", dummyNote.getId()))

                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(noteService, Mockito.times(1)).deleteNoteById(dummyNote.getId());
    }

    @Test
    public void deleteNoteByPatientTest() throws Exception {
        Note dummyNote = getDummyNote();

        mockMvc.perform(MockMvcRequestBuilders.delete("/note/patient/{id}", dummyNote.getPatId()))

                .andExpect(MockMvcResultMatchers.status().isNoContent());
        Mockito.verify(noteService, Mockito.times(1)).deleteNoteByPatientId(dummyNote.getPatId());
    }


    //////////////////////SET UP/////////////////////////
    private Note getDummyNote() {
        Note dummyNote = new Note();
        dummyNote.setId("000000000000000000000001");
        dummyNote.setPatient("Last Name");
        dummyNote.setPatId(1);
        dummyNote.setNote("Note");

        return dummyNote;
    }
}
