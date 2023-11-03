package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Patient;

public interface PatientService {
    public Patient getPatient(int id);

    public Patient savePatient(Patient patient);

    public void deletePatient(int id);
}
