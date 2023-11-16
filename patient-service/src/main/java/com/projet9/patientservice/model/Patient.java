package com.projet9.patientservice.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient",
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"first_name", "last_name"}))
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")

    private LocalDate dateOfBirth;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "gender_id")
    private Gender gender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    @Valid
    private Address address;

    @Column(name = "phone_number")
    private String phoneNumber;

}
