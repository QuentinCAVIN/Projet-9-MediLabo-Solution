package com.projet9.clientui.proxies;

import com.projet9.clientui.Dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "gateway" , url = "localhost:9101")
public interface PatientServiceProxy {
    @GetMapping(value = "/patient/{id}")
    PatientDto getPatient(@PathVariable("id") int id);

    @GetMapping(value = "/patient")
    PatientDto getPatient(@RequestParam String firstName, @RequestParam String lastName);
    @GetMapping(value = "/patient/list")
    List<PatientDto> getPatients();
    @PostMapping(value = "/patient")
    void savePatient(PatientDto patientDto);

    @PutMapping(value = "/patient")
    void updatePatient(PatientDto patientDto);
    @DeleteMapping(value = "/patient/{id}")
    void deletePatient(@PathVariable("id") int id);
}
