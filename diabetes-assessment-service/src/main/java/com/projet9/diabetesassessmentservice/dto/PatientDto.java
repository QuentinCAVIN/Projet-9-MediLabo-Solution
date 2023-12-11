package com.projet9.diabetesassessmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private int id;
    private LocalDate dateOfBirth;
    private GenderDto gender;
}

