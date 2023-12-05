package com.projet9.clientui.proxies;

import com.projet9.clientui.Dto.NoteDto;
import com.projet9.clientui.Dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "gateway" , url = "localhost:9101")
public interface PatientServiceProxy {
    //TODO Modifier le nom de la classe si on regroupe les methodes de patient-service et note-service
    // (deux classe @FeignCLient génèrent une erreur au démarrage)
    @GetMapping(value = "/patient/{id}")
    PatientDto getPatient(@PathVariable("id") int id);

    @GetMapping(value = "/patient")
    PatientDto getPatient(@RequestParam String firstName, @RequestParam String lastName);
    @GetMapping(value = "/patient/list")
    List<PatientDto> getPatients();
    @PostMapping(value = "/patient")
    void savePatient(PatientDto patientDto);
    @PutMapping(value = "/patient")
    void updatePatient(PatientDto patientDto);
    //TODO Ne sert pas pour l'instant, Meme reflexion que pour updateNote plus bas
    @DeleteMapping(value = "/patient/{id}")
    void deletePatient(@PathVariable("id") int id);
    @GetMapping(value = "/note/patient/{id}")
    List<NoteDto> getNotesByPatientId(@PathVariable("id") int PatientId);
    @GetMapping(value = "/note/{id}")
    NoteDto getNoteById(@PathVariable("id") String id);
    @PostMapping(value = "/note")
    void createNote(NoteDto note);
    @PutMapping(value = "/note")
    void updateNote(NoteDto note);
    //TODO Voir avec Vincent si il ne serait pas mieux d'utiliser une seul méthode pour update et create (voir note-service controller)

    @DeleteMapping(value = "/note/{id}")
    void deleteNote(@PathVariable("id") String id);

    @DeleteMapping (value = "/note/patient/{id}")
    void deleteNoteByPatientId(@PathVariable("id") int patientId);
}
