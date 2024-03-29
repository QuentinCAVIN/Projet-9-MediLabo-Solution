package com.projet9.clientui.controller;

import com.projet9.clientui.Dto.NoteDto;
import com.projet9.clientui.Dto.PatientDto;
import com.projet9.clientui.proxies.Proxy;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ClientController {

    private final Proxy proxy;

    public ClientController(Proxy proxy/*, NoteServiceProxy noteProxy*/) {
        this.proxy = proxy;
    }


    /////////////// DISPLAY HTML ////////////////
    @GetMapping("/patient")
    public String showPatientList(Model model) {
        model.addAttribute("patients", proxy.getPatients());
        return "patient-list";
    }

    @GetMapping("/patient/display")
    public String showDisplayPatient(Model model, @RequestParam int patientId,
                                     @RequestParam(required = false) String noteId) {
        model.addAttribute("patient", proxy.getPatient(patientId));
        model.addAttribute("notes", proxy.getNotesByPatientId(patientId));
        model.addAttribute("assessment", proxy.patientDiabetesAssessment(patientId));
        if (noteId != null) {
            model.addAttribute("noteToSave", proxy.getNoteById(noteId));
        } else {
            model.addAttribute("noteToSave", new NoteDto());
        }
        return "display-patient";
    }

    @GetMapping("/patient/add")
    public String showAddNewPatientForm(Model model) {
        model.addAttribute("patient", new PatientDto());
        return "add-new-patient";
    }

    @GetMapping("/patient/update/{id}")
    public String showUpdatePatientForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("patient", proxy.getPatient(id));
        return "update-patient";
    }


    //////////////////SAVE ///////////////////////

    @PostMapping("/patient/update")
    public String validateUpdatePatient(@Valid @ModelAttribute("patient") PatientDto patientDto,
                                        BindingResult result, Model model) {
        if ((patientDto.getAddress().getStreet() == "") && (patientDto.getAddress().getNumber() == "")) {
            patientDto.setAddress(null);
        }
        if (!result.hasErrors()) {
            proxy.updatePatient(patientDto);
            return "redirect:/patient/display?patientId=" + patientDto.getId();
        }
        model.addAttribute("patient", patientDto);
        return "update-patient";
    }

    @PostMapping("/patient/validate")
    public String validateNewPatient(@Valid @ModelAttribute("patient") PatientDto patientDto, BindingResult result, Model model) {
        PatientDto patientAlreadyPresentInDTB = proxy.getPatient(patientDto.getFirstName(), patientDto.getLastName());
        if (patientAlreadyPresentInDTB != null) {
            result.rejectValue("firstName", null,
                    patientDto.getFirstName() + " " + patientDto.getLastName() + " is already registered");
        }
        if ((patientDto.getAddress().getStreet() == "") && (patientDto.getAddress().getNumber() == "")) {
            patientDto.setAddress(null);
        }
        if (!result.hasErrors()) {
            proxy.savePatient(patientDto);
            return "redirect:/patient";
        }
        model.addAttribute("patient", patientDto);
        return "add-new-patient";
    }

    @PostMapping("/note/save")
    public String saveNote(@ModelAttribute("noteToSave") NoteDto note) {
        if (note.getId().isEmpty()) {
            note.setId(null);
            proxy.createNote(note);
        } else {
            proxy.updateNote(note);
        }
        return "redirect:/patient/display?patientId=" + note.getPatId();
    }


    ///////////////////////////DELETE///////////////////////////
    @GetMapping("/patient/delete/{id}")
    public String deletePatient(@PathVariable("id") int id, Model model) {
        proxy.deletePatient(id);
        proxy.deleteNoteByPatientId(id);
        return "redirect:/patient";
    }

    @GetMapping("/note/delete/{id}")
    public String deleteNote(@PathVariable("id") String id, @RequestParam int patientId) {
        proxy.deleteNote(id);
        return "redirect:/patient/display?patientId=" + patientId;
    }
}