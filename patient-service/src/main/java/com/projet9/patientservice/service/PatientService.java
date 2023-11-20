package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {
    public Optional<Patient> getPatient(int id);

    public Optional<Patient> getPatient(String firstName, String lastName);

    List<Patient> getPatients();

    public Patient savePatient(Patient patient);

    public void deletePatient(int id);


}
