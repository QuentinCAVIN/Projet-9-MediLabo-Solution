package com.projet9.clientui.controller;

import com.projet9.clientui.Dto.PatientDto;
import com.projet9.clientui.proxies.PatientServiceProxy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientController {

    private final PatientServiceProxy patientProxy;

    public ClientController(PatientServiceProxy patientProxy){
        this.patientProxy = patientProxy;
    }

    @GetMapping("/patient")
    public String home (Model model) {
        model.addAttribute("patients", patientProxy.getPatients());
        return "patient-list";
    }

    @GetMapping("/patient/add")
    public String showAddNewPatientForm (Model model) {
        model.addAttribute("patient", new PatientDto());
        return "add-new-patient";
    }

    @PostMapping("/patient/validate")
    public String validatePatient (Model model, @ModelAttribute @DateTimeFormat(pattern = "dd/MM/yyyy") PatientDto patientDto) {
        patientProxy.createPatient(patientDto);
        return "redirect:/patient";
    }
}