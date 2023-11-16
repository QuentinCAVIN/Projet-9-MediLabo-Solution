package com.projet9.clientui.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private int id;

    private String firstName;

    private String lastName;


    private LocalDate dateOfBirth;


    private GenderDto gender;


    private AddressDto address;

    private String phoneNumber;
}
