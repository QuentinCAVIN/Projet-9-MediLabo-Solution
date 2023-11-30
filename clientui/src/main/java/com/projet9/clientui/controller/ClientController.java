package com.projet9.clientui.controller;

import com.projet9.clientui.Dto.PatientDto;
import com.projet9.clientui.proxies.PatientServiceProxy;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientController {

    private final PatientServiceProxy patientProxy;
  //  private final NoteServiceProxy noteProxy;

    public ClientController(PatientServiceProxy patientProxy/*, NoteServiceProxy noteProxy*/) {
        this.patientProxy = patientProxy;
       // this.noteProxy = noteProxy;
    }

    @GetMapping("/patient")
    public String showPatientList(Model model) {
        model.addAttribute("patients", patientProxy.getPatients());
        return "patient-list";
    }

    @GetMapping("/patient/add")
    public String showAddNewPatientForm(Model model) {
        model.addAttribute("patient", new PatientDto());
        return "add-new-patient";
    }

    @GetMapping("/patient/display")
    public String showDisplayPatientt(Model model) {
        model.addAttribute("patient", patientProxy.getPatient(1));
        model.addAttribute("notes", patientProxy.getNotesByPatientId(1));
        return "display-patient";
        //TODO Methode en phase de test, remplacer les valeurs fixe "1" par des variables
    }

    @GetMapping("/patient/update/{id}")
    public String showUpdatePatientForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("patient", patientProxy.getPatient(id));
        return "update-patient";
    }
    @PostMapping("/patient/update")
    public String validateUpdatePatient(@Valid @ModelAttribute ("patient") PatientDto patientDto,
                                        BindingResult result, Model model) {
        if((patientDto.getAddress().getStreet() == "") && (patientDto.getAddress().getNumber()== "")){ //TODO Vérifier si c'est pas un peu n'importe quoi (pour eviter les champs vide dans la bdd)
            patientDto.setAddress(null);
        }
        if (!result.hasErrors()){
        patientProxy.savePatient(patientDto);
        return "redirect:/patient";
        }
        model.addAttribute("patient", patientDto);
        return "update-patient";
    }

    @PostMapping("/patient/validate")
    public String validatePatient(@Valid @ModelAttribute("patient") PatientDto patientDto,  BindingResult result, Model model) {
        PatientDto patientAlreadyPresentInDTB = patientProxy.getPatient(patientDto.getFirstName(),patientDto.getLastName());
        if (patientAlreadyPresentInDTB != null) {
            result.rejectValue("firstName",null,
                    patientDto.getFirstName() + " " + patientDto .getLastName() + " is already registered");
        }
        if((patientDto.getAddress().getStreet() == "") && (patientDto.getAddress().getNumber()== "")){ //TODO Vérifier si c'est pas un peu n'importe quoi
            patientDto.setAddress(null);
        }
        if (!result.hasErrors()){
        patientProxy.savePatient(patientDto);
        return "redirect:/patient";
        }
        model.addAttribute("patient", patientDto);
        return "add-new-patient";
    }

    @GetMapping("patient/delete/{id}")
    public String deletePatient(@PathVariable("id") int id, Model model) {
        patientProxy.deletePatient(id);
        return "redirect:/patient";
    }


}