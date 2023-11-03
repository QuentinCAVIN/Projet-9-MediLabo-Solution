package com.projet9.patientservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient",
        uniqueConstraints=
@UniqueConstraint(columnNames={"first_name", "last_name"})) //TODO ne fonctionne pas en cas d'homonyme. Voir si on laisse
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    private Gender gender = Gender.NOT_KNOWN;

    @ManyToOne(cascade = CascadeType.ALL) // TODO: Si je supprime une adresse ça supprime le patient?
// https://www.baeldung.com/jpa-cascade-types
    @JoinColumn(name="address_id")
    private Address address;

    @Column(name="phone_number")
    private String phoneNumber;

    enum Gender  {
        MALE,
        FEMALE,
        NOT_KNOWN,
        NOT_APPLICABLE
    }

}
