package com.projet9.clientui.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
    private String id;
    private int patId;
    private String patient;
    @NotEmpty
    private String note;
}
