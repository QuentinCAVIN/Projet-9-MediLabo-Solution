package com.projet9.patientservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet9.patientservice.model.Address;
import com.projet9.patientservice.model.Gender;
import com.projet9.patientservice.model.Patient;
import com.projet9.patientservice.service.PatientService;
import com.projet9.patientservice.service.PatientServiceImpl;
import org.glassfish.jaxb.core.v2.TODO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    PatientService patientService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getPatientByIdTest() throws Exception {
        Patient dummyPatient = getDummyPatient();
        Mockito.when(patientService.getPatient(dummyPatient.getId())).thenReturn(dummyPatient);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/{id}",
                        Integer.toString(dummyPatient.getId())))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dummyPatient)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //Test désactivé: La présence du patient est vérifiée dans clientui
    /*@Disabled
    @Test
    public void getPatientByIdNotFoundTest() throws Exception {
        Mockito.when(patientService.getPatient(1)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/{id}",
                        Integer.toString(1)))
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    */


    @Test
    public void getPatientByFirstNameAndLastNameTest() throws Exception {
        Patient dummyPatient = getDummyPatient();
        Mockito.when(patientService.getPatient(dummyPatient.getFirstName(), dummyPatient.getLastName()))
                .thenReturn(Optional.of(dummyPatient));

        mockMvc.perform(MockMvcRequestBuilders.get("/patient")
                        .param("firstName", dummyPatient.getFirstName())
                        .param("lastName", dummyPatient.getLastName()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dummyPatient)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getPatientByFirstNameAndLastNameNotFoundTest() throws Exception {
        Patient dummyPatient = getDummyPatient();
        Mockito.when(patientService.getPatient(dummyPatient.getFirstName(), dummyPatient.getLastName()))
                .thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/patient")
                        .param("firstName", dummyPatient.getFirstName())
                        .param("lastName", dummyPatient.getLastName()))
                .andExpect(MockMvcResultMatchers.content().string(""))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void getPatientsTest() throws Exception {
        List<Patient> dummiesPatients = Arrays.asList(getDummyPatient(), getDummyPatient());
        Mockito.when(patientService.getPatients())
                .thenReturn(dummiesPatients);

        mockMvc.perform(MockMvcRequestBuilders.get("/patient/list"))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(dummiesPatients)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void createPatientTest() throws Exception {
        Patient dummyPatient = getDummyPatient();

        mockMvc.perform(MockMvcRequestBuilders.post("/patient")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyPatient)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(patientService, Mockito.times(1))
                .savePatient(ArgumentMatchers.any(Patient.class));
    }

    @Test
    public void updatePatientTest() throws Exception {
        Patient dummyPatient = getDummyPatient();
        Mockito.when(patientService.getPatient(dummyPatient.getId())).thenReturn(dummyPatient);

        mockMvc.perform(MockMvcRequestBuilders.put("/patient/{id}", dummyPatient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyPatient)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(patientService, Mockito.times(1))
                .savePatient(ArgumentMatchers.any(Patient.class));

    }

    //Test désactivé: La présence du patient est vérifiée dans clientui
    /*
    @Test
    public void updatePatientNotFoundTest() throws Exception {
        Patient dummyPatient = getDummyPatient();
        Mockito.when(patientService.getPatient(dummyPatient.getId())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/patient/{id}", dummyPatient.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dummyPatient)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
*/
    @Test
    public void deletePatient() throws Exception {
        Patient dummyPatient = getDummyPatient();

        mockMvc.perform(MockMvcRequestBuilders.delete("/patient/{id}", dummyPatient.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Mockito.verify(patientService, Mockito.times(1)).deletePatient(dummyPatient.getId());
    }

    private Patient getDummyPatient() {
        Gender dummyGender = new Gender();
        dummyGender.setGender("gender");

        Address dummyAddress = new Address();
        dummyAddress.setNumber("10 bis");
        dummyAddress.setStreet("main street");

        Patient dummyPatient = new Patient();
        dummyPatient.setId(1);
        dummyPatient.setFirstName("First Name");
        dummyPatient.setLastName("Last Name");
        dummyPatient.setDateOfBirth(LocalDate.of(2020, 1, 8));
        dummyPatient.setPhoneNumber("06 07 08 09 10");
        dummyPatient.setGender(dummyGender);
        dummyPatient.setAddress(dummyAddress);
        return dummyPatient;
    }
}
