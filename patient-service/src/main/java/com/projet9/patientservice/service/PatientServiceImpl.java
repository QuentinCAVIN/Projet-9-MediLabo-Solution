package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Address;
import com.projet9.patientservice.model.Gender;
import com.projet9.patientservice.model.Patient;
import com.projet9.patientservice.repository.AddressRepository;
import com.projet9.patientservice.repository.GenderRepository;
import com.projet9.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;
    private GenderRepository genderRepository;
    private AddressRepository addressRepository;

    public PatientServiceImpl(PatientRepository patientRepository, GenderRepository genderRepository,
                              AddressRepository addressRepository) {
        this.patientRepository = patientRepository;
        this.genderRepository = genderRepository;
        this.addressRepository = addressRepository;
    }

    public Optional<Patient> getPatient(int id) {
        return patientRepository.findById(id);
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public Patient savePatient(Patient patient) {

        Gender gender = genderRepository.findByGender(patient.getGender().getGender());
        patient.setGender(gender);// Gender est obligatoirement définis dans l'ui et correspond aux gender en BDD pas besoin de vérif

        Address existingAddress = addressRepository.findByNumberAndStreet(
                patient.getAddress().getNumber(),patient.getAddress().getStreet());
        if (existingAddress != null) {
            patient.setAddress(existingAddress);
        }
        return patientRepository.save(patient);
    }

    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }
}