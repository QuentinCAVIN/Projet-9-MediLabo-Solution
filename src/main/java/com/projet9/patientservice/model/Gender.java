package com.projet9.patientservice.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.aspectj.weaver.ast.Not;

public class Gender {
    /*0 = Not known;
1 = Male;
2 = Female;
9 = Not applicable.*/
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    int id;
    String gender;
}
