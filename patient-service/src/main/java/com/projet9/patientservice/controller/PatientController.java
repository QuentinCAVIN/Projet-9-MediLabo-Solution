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
    public ResponseEntity getPatient(@PathVariable("id") int id) {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatient(id));
        //La présence du patient est vérifiée dans clientui
    }

    @GetMapping("/patient")
    public ResponseEntity getPatient(@RequestParam String firstName, @RequestParam String lastName) {
        return patientService.getPatient(firstName, lastName).map(ResponseEntity::ok).orElse(new ResponseEntity(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/patient/list")
    public ResponseEntity getPatients() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatients());
    }

    @PostMapping("/patient")
    public ResponseEntity createPatient(@RequestBody Patient patient) {
        patientService.savePatient(patient);
        return new ResponseEntity(HttpStatus.CREATED);
        //La validité du patient est vérifiée dans clientui
    }

    @PutMapping("/patient")
    public ResponseEntity updatePatient(@RequestBody Patient patient) {
        patientService.savePatient(patient);
        return new ResponseEntity(HttpStatus.OK);
        //La présence du patient est vérifiée dans clientui
    }

    @DeleteMapping("/patient/{id}")
    public ResponseEntity deletePatient(@PathVariable("id") int id) {
        patientService.deletePatient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}