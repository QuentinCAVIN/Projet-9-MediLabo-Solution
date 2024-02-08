package com.projet9.patientservice.repository;

import com.projet9.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    Optional<Patient> findByFirstNameAndLastName(String firstName, String lastName);
}
