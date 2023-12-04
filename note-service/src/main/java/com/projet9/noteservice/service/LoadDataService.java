package com.projet9.noteservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet9.noteservice.model.InputData;
import com.projet9.noteservice.model.Note;
import com.projet9.noteservice.repository.NoteRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Service
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoadDataService {
    @Autowired
    private NoteRepository noteRepository;

    public void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Resource resource = new ClassPathResource("data.json"); // Class spring qui va chercher directement dans les ressources du projet
            File file = resource.getFile();
            InputData data = objectMapper.readValue(file, InputData.class);
            List<Note> notes = data.getNotes();
            noteRepository.saveAll(notes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}