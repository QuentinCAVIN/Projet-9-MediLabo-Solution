package com.projet9.diabetesassessmentservice.proxies;

import com.projet9.diabetesassessmentservice.dto.NoteDto;
import com.projet9.diabetesassessmentservice.dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "gateway" , url = "${link.to.the.gateway}")
public interface Proxy {
    @GetMapping(value = "/patient/{id}")
    PatientDto getPatient(@PathVariable("id") int id);
    @GetMapping(value = "/note/patient/{id}")
    List<NoteDto> getNotesByPatientId(@PathVariable("id") int PatientId);
}
