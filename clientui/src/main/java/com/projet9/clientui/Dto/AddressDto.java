package com.projet9.clientui.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private String number;
    private String street;

    @Override
    public String toString() {
        return number + " " + street;
    }
}
