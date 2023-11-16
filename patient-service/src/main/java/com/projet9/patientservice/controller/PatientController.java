package com.projet9.patientservice.controller;

import com.projet9.patientservice.model.Patient;
import com.projet9.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class PatientController {
    private PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }
    @GetMapping("/patient/{id}")
    public ResponseEntity<Optional<Patient>> getPatient(@PathVariable("id")int id){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatient(id));
    }

    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getPatients(){
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatients());
    }

    @PostMapping("/patient")
    public ResponseEntity createPatient(@RequestBody Patient patient){
        patientService.savePatient(patient);
        return ResponseEntity.status((HttpStatus.CREATED)).build();
    }

    @PutMapping("/patient/{id}") //
    public ResponseEntity updatePatient(@PathVariable("id") int id, @Valid Patient patient){

        //TODO, Comment Spring fait le lien entre l'objet a modifier et celui passé en paramètre?
        patientService.savePatient(patient);
    return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/patient/{id}")
    public ResponseEntity deletePatient(@PathVariable("id") int id){
    patientService.deletePatient(id); //TODO implémenter vérification de présence du patient?
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
