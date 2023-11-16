package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Patient;
import com.projet9.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PatientServiceImpl implements PatientService{

    private PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository=patientRepository;
    }

    public Optional<Patient> getPatient(int id) {
        return patientRepository.findById(id);
    }
    public List<Patient> getPatients(){
        return  patientRepository.findAll();
    }
    public Patient savePatient(Patient patient){
        return patientRepository.save(patient);
    }
    public void deletePatient(int id){
        patientRepository.deleteById(id);
    }
}
