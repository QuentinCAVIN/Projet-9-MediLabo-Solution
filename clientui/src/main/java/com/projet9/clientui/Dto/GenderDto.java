package com.projet9.clientui.Dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenderDto {

    int id;

    private String gender;

    @Override
    public String toString()
    {
        return gender;
    }
}

