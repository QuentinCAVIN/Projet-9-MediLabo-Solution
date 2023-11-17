package com.projet9.clientui.proxies;

import com.projet9.clientui.Dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "gateway" , url = "localhost:9101")
public interface PatientServiceProxy {
    @GetMapping(value = "/patients")
    List<PatientDto> getPatients();

    @PostMapping(value = "/patient")
    void createPatient(PatientDto patientDto);
}
