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
    public ResponseEntity<Patient> getPatient(@PathVariable("id") int id) {
        if (patientService.getPatient(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatient(id).get());
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        //TODO: Faut il générer une erreur ici?
    }

    @GetMapping("/patient")
    public ResponseEntity<Optional<Patient>> getPatient(@RequestParam String firstName, @RequestParam String lastName) {
            return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatient(firstName, lastName));
        //TODO: contrairement à get patient/id je renvoie un optional Patient car il est suceptible d'être vide
        // patient/id au dessus n'est normalement jamais vide
    }

    @GetMapping("/patient/list")
    public ResponseEntity<List<Patient>> getPatients() {
        return ResponseEntity.status(HttpStatus.OK).body(patientService.getPatients());
        //TODO: Faut il renvoyer un code 404 en cas de liste vide?
    }

    @PostMapping("/patient")
    public ResponseEntity createPatient(@RequestBody Patient patient) {
        patientService.savePatient(patient);
        return ResponseEntity.status((HttpStatus.CREATED)).build();
    }

    @PutMapping("/patient/{id}") //
    public ResponseEntity updatePatient(@PathVariable("id") int id, @Valid Patient patient) {
        if (patientService.getPatient(id).isPresent()) {
            patientService.savePatient(patient);
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //TODO, Comment Spring fait le lien entre l'objet a modifier et celui passé en paramètre?
        //TODO vérifier en fin d'étape 2 que le patient donné en parametre est valide sinon faire un
        // if(name!= null){patientDeLaBDD.setName(patientEnParametre.getName)} (pour ne pas effacer les champs vide)
    }

    @DeleteMapping("/patient/{id}")
    public ResponseEntity deletePatient(@PathVariable("id") int id) {
        patientService.deletePatient(id); //TODO implémenter vérification de présence du patient? Soit il est éffacé ou soit il n'est pas présent = résultat final identique
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}