package com.projet9.clientui.proxies;

import com.projet9.clientui.Dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "gateway" , url = "localhost:9101")
public interface PatientServiceProxy {
    @GetMapping(value = "/patient/list")
    List<PatientDto> getPatients();
    @GetMapping(value = "/patient/{id}")
    PatientDto getPatient(@PathVariable("id") int id);
    @PostMapping(value = "/patient")
    void savePatient(PatientDto patientDto);
    @DeleteMapping(value = "/patient/{id}")
    void deletePatient(@PathVariable("id") int id);
}
