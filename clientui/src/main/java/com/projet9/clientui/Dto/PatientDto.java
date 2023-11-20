package com.projet9.clientui.Dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;


    @Past
    @NotNull
    //TODO: quand un utilisateur rentre une date avec un format jj/mm/yyyyyy cela génére dans le html une erreur
    // qu'il faudrait personaliser. Gérer ça soit en bloquant l'utilisateur dans le html, soit en capturant l'exception
    // soit en remplacant le champ LocalDate en String et en implementant une annotation custom
    private LocalDate dateOfBirth;

    @Valid
    private GenderDto gender;


    private AddressDto address;

    private String phoneNumber;
}
