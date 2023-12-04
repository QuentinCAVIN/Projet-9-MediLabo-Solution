package com.projet9.clientui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet9.clientui.Dto.AddressDto;
import com.projet9.clientui.Dto.GenderDto;
import com.projet9.clientui.Dto.NoteDto;
import com.projet9.clientui.Dto.PatientDto;
import com.projet9.clientui.proxies.PatientServiceProxy;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClientController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ClientControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    PatientServiceProxy patientServiceProxy;
    @Autowired
    ObjectMapper objectMapper;


    /////////////// DISPLAY HTML ////////////////

    @Test
    public void showPatientListTest() throws Exception {
        List<PatientDto> dummiesPatients = Arrays.asList(getDummyPatient(), getDummyPatient());
        Mockito.when(patientServiceProxy.getPatients()).thenReturn(dummiesPatients);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("patient-list"))
                .andExpect(MockMvcResultMatchers.model().attribute("patients", hasItem(
                        samePropertyValuesAs(dummiesPatients.get(0)))))
                .andExpect(MockMvcResultMatchers.model().attribute("patients", hasItem(
                        samePropertyValuesAs(dummiesPatients.get(1)))));

        /* example: https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/
         */
        Mockito.verify(patientServiceProxy, Mockito.times(1)).getPatients();

    }

    @Test
    public void showDisplayPatientTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();
        NoteDto dummyNote = getDummyNote();
        Mockito.when(patientServiceProxy.getPatient(dummyPatient.getId())).thenReturn(dummyPatient);
        Mockito.when(patientServiceProxy.getNotesByPatientId(dummyPatient.getId())).thenReturn(List.of(dummyNote));

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/display")
                        .param("patientId", String.valueOf(dummyPatient.getId())))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("display-patient"))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", dummyPatient))
                .andExpect(MockMvcResultMatchers.model().attribute("notes", Matchers.hasItem(
                        Matchers.samePropertyValuesAs(dummyNote))))
                .andExpect(MockMvcResultMatchers.model().attributeExists("noteToSave"));

        /* example: https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/
         */
        Mockito.verify(patientServiceProxy, Mockito.times(1)).getPatient(dummyPatient.getId());
        Mockito.verify(patientServiceProxy, Mockito.times(1)).getNotesByPatientId(dummyPatient.getId());
    }

    @Test
    public void showDisplayPatientWithEditNoteTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();
        NoteDto dummyNote = getDummyNote();
        Mockito.when(patientServiceProxy.getPatient(dummyPatient.getId())).thenReturn(dummyPatient);
        Mockito.when(patientServiceProxy.getNotesByPatientId(dummyPatient.getId())).thenReturn(List.of(dummyNote));
        Mockito.when(patientServiceProxy.getNoteById(dummyNote.getId())).thenReturn(dummyNote);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/display")
                        .param("patientId", String.valueOf(dummyPatient.getId()))
                        .param("noteId", dummyNote.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("display-patient"))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", dummyPatient))
                .andExpect(MockMvcResultMatchers.model().attribute("notes", Matchers.hasItem(
                        Matchers.samePropertyValuesAs(dummyNote))))
                .andExpect(MockMvcResultMatchers.model().attribute("noteToSave", dummyNote));

        /* example: https://www.petrikainulainen.net/programming/spring-framework/unit-testing-of-spring-mvc-controllers-normal-controllers/
         */
        Mockito.verify(patientServiceProxy, Mockito.times(1)).getPatient(dummyPatient.getId());
        Mockito.verify(patientServiceProxy, Mockito.times(1)).getNotesByPatientId(dummyPatient.getId());
        Mockito.verify(patientServiceProxy, Mockito.times(1)).getNoteById(dummyNote.getId());
    }

    @Test
    public void showAddNewPatientFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/add"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-new-patient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("patient"));
    }

    @Test
    public void showUpdatePatientFormTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();
        Mockito.when(patientServiceProxy.getPatient(dummyPatient.getId())).thenReturn(dummyPatient);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/update/{id}", dummyPatient.getId()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("update-patient"))
                .andExpect(MockMvcResultMatchers.model().attribute("patient", dummyPatient));
        Mockito.verify(patientServiceProxy, Mockito.times(1)).getPatient(dummyPatient.getId());
    }


    //////////////////SAVE ///////////////////////

    @Test
    public void validateUpdatePatientTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .param("id", String.valueOf(dummyPatient.getId()))
                        .param("firstName", dummyPatient.getFirstName())
                        .param("lastName", dummyPatient.getLastName())
                        .param("dateOfBirth", dummyPatient.getDateOfBirth().toString())
                        .param("gender.gender", dummyPatient.getGender().getGender())
                        .param("address.number", dummyPatient.getAddress().getNumber())
                        .param("address.street", dummyPatient.getAddress().getStreet())
                        .param("phoneNumber", dummyPatient.getPhoneNumber()))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(
                        "/patient/display?patientId=" + dummyPatient.getId()));
        Mockito.verify(patientServiceProxy, Mockito.times(1))
                .savePatient(Mockito.any(PatientDto.class));
    }

    @Test
    public void validateUpdatePatientWithoutAddressTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();
        dummyPatient.getAddress().setNumber("");
        dummyPatient.getAddress().setStreet("");

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .param("id", String.valueOf(dummyPatient.getId()))
                        .param("firstName", dummyPatient.getFirstName())
                        .param("lastName", dummyPatient.getLastName())
                        .param("dateOfBirth", dummyPatient.getDateOfBirth().toString())
                        .param("gender.gender", dummyPatient.getGender().getGender())
                        .param("address.number", dummyPatient.getAddress().getNumber())
                        .param("address.street", dummyPatient.getAddress().getStreet())
                        .param("phoneNumber", dummyPatient.getPhoneNumber()))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(
                        "/patient/display?patientId=" + dummyPatient.getId()));
        Mockito.verify(patientServiceProxy, Mockito.times(1))
                .savePatient(Mockito.any(PatientDto.class));
        ArgumentCaptor<PatientDto> captor = ArgumentCaptor.forClass(PatientDto.class);
        Mockito.verify(patientServiceProxy).savePatient(captor.capture());
        PatientDto savedPatient = captor.getValue();
        Assertions.assertThat(savedPatient.getAddress()).isNull();
    }

    @Test
    public void validateUpdatePatientWithInvalidPatientTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("dateOfBirth", "")
                        .param("gender.gender", "")
                        .param("address.number", "")
                        .param("address.street", "")
                        .param("phoneNumber", ""))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("update-patient"))
                .andExpect(model().attributeHasFieldErrors("patient",
                        "firstName", "lastName", "dateOfBirth", "gender.gender"));
        Mockito.verify(patientServiceProxy, Mockito.times(0))
                .savePatient(Mockito.any(PatientDto.class));
    }

    @Test
    public void validateNewPatientTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();
        Mockito.when(patientServiceProxy.getPatient(dummyPatient.getFirstName(), dummyPatient.getLastName()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/validate")
                        .param("firstName", dummyPatient.getFirstName())
                        .param("lastName", dummyPatient.getLastName())
                        .param("dateOfBirth", dummyPatient.getDateOfBirth().toString())
                        .param("gender.gender", dummyPatient.getGender().getGender())
                        .param("address.number", dummyPatient.getAddress().getNumber())
                        .param("address.street", dummyPatient.getAddress().getStreet())
                        .param("phoneNumber", dummyPatient.getPhoneNumber()))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patient"));
        Mockito.verify(patientServiceProxy, Mockito.times(1))
                .savePatient(Mockito.any(PatientDto.class));
    }

    @Test
    public void validatePatientWithInvalidPatientTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();
        Mockito.when(patientServiceProxy.getPatient(dummyPatient.getFirstName(), dummyPatient.getLastName()))
                .thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/validate")
                        .param("firstName", "")
                        .param("lastName", "")
                        .param("dateOfBirth", "")
                        .param("gender.gender", "")
                        .param("address.number", "")
                        .param("address.street", "")
                        .param("phoneNumber", ""))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-new-patient"))
                .andExpect(model().attributeHasFieldErrors("patient",
                        "firstName", "lastName", "dateOfBirth", "gender.gender"));
        Mockito.verify(patientServiceProxy, Mockito.times(0))
                .savePatient(Mockito.any(PatientDto.class));
    }

    @Test
    public void validatePatientWithAnExistingPatientTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();
        Mockito.when(patientServiceProxy.getPatient(dummyPatient.getFirstName(), dummyPatient.getLastName()))
                .thenReturn(dummyPatient);

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/validate")
                        .param("firstName", dummyPatient.getFirstName())
                        .param("lastName", dummyPatient.getLastName())
                        .param("dateOfBirth", dummyPatient.getDateOfBirth().toString())
                        .param("gender.gender", dummyPatient.getGender().getGender())
                        .param("address.number", dummyPatient.getAddress().getNumber())
                        .param("address.street", dummyPatient.getAddress().getStreet())
                        .param("phoneNumber", dummyPatient.getPhoneNumber()))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-new-patient"))
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString(
                        dummyPatient.getFirstName() + " " +
                                dummyPatient.getLastName() + " is already registered")))
                .andExpect(MockMvcResultMatchers.model().attributeExists("patient"));
        Mockito.verify(patientServiceProxy, Mockito.times(0))
                .savePatient(Mockito.any(PatientDto.class));
    }


    @Test
    public void saveNoteWhenCreateTest() throws Exception {
        NoteDto dummyNote = getDummyNote();

        mockMvc.perform(MockMvcRequestBuilders.post("/note/save")
                        .param("id","")
                        .param("note", dummyNote.getNote())
                        .param("patient", dummyNote.getPatient())
                        .param("patId", String.valueOf( dummyNote.getPatId())))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(
                        "/patient/display?patientId=" + dummyNote .getPatId()));
        Mockito.verify(patientServiceProxy, Mockito.times(1))
                .createNote(Mockito.any(NoteDto.class));
        Mockito.verify(patientServiceProxy, Mockito.times(0))
                .updateNote(Mockito.any(NoteDto.class));
    }

    @Test
    public void saveNoteWhenUpdateTest() throws Exception {
        NoteDto dummyNote = getDummyNote();

        mockMvc.perform(MockMvcRequestBuilders.post("/note/save")
                        .param("id",dummyNote.getId())
                        .param("note", dummyNote.getNote())
                        .param("patient", dummyNote.getPatient())
                        .param("patId", String.valueOf( dummyNote.getPatId())))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl(
                        "/patient/display?patientId=" + dummyNote .getPatId()));
        Mockito.verify(patientServiceProxy, Mockito.times(0))
                .createNote(Mockito.any(NoteDto.class));
        Mockito.verify(patientServiceProxy, Mockito.times(1))
                .updateNote(Mockito.any(NoteDto.class));
    }




    ///////////////////////////DELETE///////////////////////////

    @Test
    public void deletePatientTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/delete/{id}", dummyPatient.getId()))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patient"));
        Mockito.verify(patientServiceProxy, Mockito.times(1))
                .deletePatient(dummyPatient.getId());
        Mockito.verify(patientServiceProxy, Mockito.times(1))
                .deleteNoteByPatientId(dummyPatient.getId());
    }

    @Test
    public void deleteNoteTest() throws Exception {
        NoteDto dummyNote = getDummyNote();

        mockMvc.perform(MockMvcRequestBuilders.get("/note/delete/{id}", dummyNote.getId())
                        .param("patientId",String.valueOf(dummyNote.getPatId())))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers
                        .redirectedUrl("/patient/display?patientId=" + dummyNote.getPatId()));
        Mockito.verify(patientServiceProxy, Mockito.times(1))
                .deleteNote(dummyNote.getId());
    }

    ///////////////////////////SET UP///////////////////////////
    private PatientDto getDummyPatient() {
        GenderDto dummyGender = new GenderDto();
        dummyGender.setGender("gender");

        AddressDto dummyAddress = new AddressDto();
        dummyAddress.setNumber("10 bis");
        dummyAddress.setStreet("main street");

        PatientDto dummyPatient = new PatientDto();
        dummyPatient.setId(1);
        dummyPatient.setFirstName("First Name");
        dummyPatient.setLastName("Last Name");
        dummyPatient.setDateOfBirth(LocalDate.of(2020, 1, 8));
        dummyPatient.setPhoneNumber("06 07 08 09 10");
        dummyPatient.setGender(dummyGender);
        dummyPatient.setAddress(dummyAddress);
        return dummyPatient;
    }

    private NoteDto getDummyNote() {
        NoteDto dummyNote = new NoteDto();
        dummyNote.setId("000000000000000000000001");
        dummyNote.setPatient("Last Name");
        dummyNote.setPatId(1);
        dummyNote.setNote("Note");

        return dummyNote;
    }
}
