package com.projet9.clientui.proxies;

import com.projet9.clientui.controller.Dto.PatientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "patient-service" , url = "localhost:9001")
public interface PatientServiceProxy {
    @GetMapping(value = "/patients")
    List<PatientDto> getPatients();
}
