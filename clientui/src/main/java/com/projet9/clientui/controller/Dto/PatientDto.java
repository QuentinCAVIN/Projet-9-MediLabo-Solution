package com.projet9.clientui.controller.Dto;

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

    private String gender;

    private String address;

    private String phoneNumber;
}
