package com.projet9.clientui.controller;

import com.projet9.clientui.Dto.PatientDto;
import com.projet9.clientui.proxies.PatientServiceProxy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientController {

    private final PatientServiceProxy patientProxy;

    public ClientController(PatientServiceProxy patientProxy) {
        this.patientProxy = patientProxy;
    }

    @GetMapping("/patient")
    public String home(Model model) {
        model.addAttribute("patients", patientProxy.getPatients());
        return "patient-list";
    }

    @GetMapping("/patient/add")
    public String showAddNewPatientForm(Model model) {
        model.addAttribute("patient", new PatientDto());
        return "add-new-patient";
    }

    @GetMapping("/patient/update/{id}")
    public String showUpdatePatientForm(@PathVariable("id") int id, Model model) {
        PatientDto patientDto = patientProxy.getPatient(id);
        model.addAttribute("patient", patientProxy.getPatient(id));
        return "update-patient";
    }
    @PostMapping("/patient/update")
    public String validateUpdatePatient(@ModelAttribute PatientDto patientDto, Model model) {

        patientProxy.savePatient(patientDto);
        return "redirect:/patient";
    }

    @PostMapping("/patient/validate")
    public String validatePatient(@ModelAttribute PatientDto patientDto, Model model) {
        patientProxy.savePatient(patientDto);
        return "redirect:/patient";
    }

    @GetMapping("patient/delete/{id}")
    public String deletePatient(@PathVariable("id") int id, Model model) {
        patientProxy.deletePatient(id);
        return "redirect:/patient";
    }
}