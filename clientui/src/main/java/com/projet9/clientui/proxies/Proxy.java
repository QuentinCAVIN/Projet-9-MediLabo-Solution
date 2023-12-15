package com.projet9.clientui.proxies;

import com.projet9.clientui.Dto.AssessmentDto;
import com.projet9.clientui.Dto.NoteDto;
import com.projet9.clientui.Dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "gateway" , url = "${link.to.the.gateway}")
public interface Proxy {


    //////////// PATIENT-SERVICE/////////////
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


    ////////////NOTE-SERVICE/////////////
    @GetMapping(value = "/note/patient/{id}")
    List<NoteDto> getNotesByPatientId(@PathVariable("id") int PatientId);
    @GetMapping(value = "/note/{id}")
    NoteDto getNoteById(@PathVariable("id") String id);
    @PostMapping(value = "/note")
    void createNote(NoteDto note);
    @PutMapping(value = "/note")
    void updateNote(NoteDto note);
    @DeleteMapping(value = "/note/{id}")
    void deleteNote(@PathVariable("id") String id);
    @DeleteMapping(value = "/note/patient/{id}")
    void deleteNoteByPatientId(@PathVariable("id") int patientId);

    ////////////DIABETES-ASSESSMENT-SERVICE/////////////
    @GetMapping(value = "/assessment/{id}")
    AssessmentDto patientDiabetesAssessment(@PathVariable("id") int patientId);
}
