package com.projet9.clientui.controller;

import com.projet9.clientui.proxies.PatientServiceProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientController {

    private final PatientServiceProxy patientProxy;

    public ClientController(PatientServiceProxy patientProxy){
        this.patientProxy = patientProxy;
    }

    @GetMapping("/patient")
    public String home (Model model) {
        model.addAttribute("patients", patientProxy.getPatients());
        return "patient";
    }
}
