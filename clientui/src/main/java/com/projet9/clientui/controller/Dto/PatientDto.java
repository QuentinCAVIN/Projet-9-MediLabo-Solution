package com.projet9.clientui.controller.Dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {

    private int id;

    private String firstName;

    private String lastName;

    private Date dateOfBirth;


    private GenderDto gender;


    private AddressDto address;

    private String phoneNumber;
}
