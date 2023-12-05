package com.projet9.diabetesassessmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenderDto {
    private String gender;

    @Override
    public String toString() {
        return gender;
    }
}
