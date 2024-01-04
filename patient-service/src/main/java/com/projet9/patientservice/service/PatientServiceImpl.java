package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Address;
import com.projet9.patientservice.model.Gender;
import com.projet9.patientservice.model.Patient;
//import com.projet9.patientservice.repository.AddressRepository;
import com.projet9.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;
    private GenderService genderService;
    private AddressService addressService;

    public PatientServiceImpl(PatientRepository patientRepository, GenderService genderService,
                              AddressService addressService) {
        this.patientRepository = patientRepository;
        this.genderService = genderService;
        this.addressService = addressService;
    }

    public Patient getPatient(int id) {
        return patientRepository.findById(id).get();
    }
    //La présence du patient est vérifiée dans clientui

    public Optional<Patient> getPatient(String firstName, String lastName) {
        return patientRepository.findByFirstNameAndLastName(firstName,lastName);
    }

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public void savePatient(Patient patient) {

        Gender gender = genderService.getGender(patient.getGender().getGender());
        patient.setGender(gender);
        // Gender est obligatoirement définis dans l'uri et correspond aux gender en BDD pas besoin de vérification

        //Pour éviter de créer plusieurs fois la même adresse en BDD
        if (patient.getAddress() != null) {
            Address existingAddress = addressService.getAddress(
                    patient.getAddress().getNumber(), patient.getAddress().getStreet());
            if (existingAddress != null) {
                patient.setAddress(existingAddress);
            }
        }

        patientRepository.save(patient);
    }

    public void deletePatient(int id) {
        patientRepository.deleteById(id);
    }
}