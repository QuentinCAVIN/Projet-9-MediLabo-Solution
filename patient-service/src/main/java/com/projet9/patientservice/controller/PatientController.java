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
            return patientService.getPatient(id).map(ResponseEntity::ok).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
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
        return ResponseEntity.status((HttpStatus.CREATED)).build();
    }

    @PutMapping("/patient/{id}") //
    public ResponseEntity updatePatient(@PathVariable("id") int id, Patient patient) {
        if (patientService.getPatient(id).isPresent()) {
            patientService.savePatient(patient);
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        //TODO vérifier en fin d'étape 2 que le patient donné en parametre est valide sinon faire un
        // if(name!= null){patientDeLaBDD.setName(patientEnParametre.getName)} (pour ne pas effacer les champs vide)
        // je renvoi in code NotFound alors qu'il est imposible d'activer le endpoint avec un patient notFOund {id}, corriger ça pour rester cohérent
    }

    @DeleteMapping("/patient/{id}")
    public ResponseEntity deletePatient(@PathVariable("id") int id) {
        patientService.deletePatient(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    //TODO Utiliser l'écriture .map( partout
}