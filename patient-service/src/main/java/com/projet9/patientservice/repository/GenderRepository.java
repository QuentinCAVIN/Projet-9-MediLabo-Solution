package com.projet9.patientservice.repository;

import com.projet9.patientservice.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    Gender findByGender(String gender);
}
