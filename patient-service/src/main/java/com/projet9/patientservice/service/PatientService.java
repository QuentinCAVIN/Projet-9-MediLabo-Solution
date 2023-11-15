package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Patient;

import java.util.List;

public interface PatientService {
    public Patient getPatient(int id);

    List<Patient> getPatients();

    public Patient savePatient(Patient patient);

    public void deletePatient(int id);


}
