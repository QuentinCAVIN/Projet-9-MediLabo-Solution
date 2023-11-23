package com.projet9.clientui.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet9.clientui.Dto.AddressDto;
import com.projet9.clientui.Dto.GenderDto;
import com.projet9.clientui.Dto.PatientDto;
import com.projet9.clientui.proxies.PatientServiceProxy;
import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ClientController.class)
public class ClientControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    PatientServiceProxy patientServiceProxy;
    @Autowired
    ObjectMapper objectMapper;

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

    @Test
    public void validateUpdatePatientTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update")
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
    public void validateUpdatePatientWithoutAddressTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();
        dummyPatient.getAddress().setNumber("");
        dummyPatient.getAddress().setStreet("");

        mockMvc.perform(MockMvcRequestBuilders.post("/patient/update")
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

    ///////////// Haut dessus c'est bon
    @Test
    public void validatePatientTest() throws Exception {
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
                                dummyPatient.getLastName() + " is already registered")));
        Mockito.verify(patientServiceProxy, Mockito.times(0))
                .savePatient(Mockito.any(PatientDto.class));

    }// TODO Ajouter une vérification pour l'objet présent dans le model
    //  ".andExpect(MockMvcResultMatchers.model().attribute("patient", dummyPatient));"

    @Test
    public void deletePatientTest() throws Exception {
        PatientDto dummyPatient = getDummyPatient();
        mockMvc.perform(MockMvcRequestBuilders.get("/patient/delete/{id}", dummyPatient.getId()))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patient"));
        Mockito.verify(patientServiceProxy, Mockito.times(1))
                .deletePatient(dummyPatient.getId());
    }


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
    /*   @Test
    @WithMockUser
    public void homeTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoints"));
    }

    @Test
    public void addCurvePointFormTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/add"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"));
    }

    @Test
    public void validateTestWithValidObject() throws Exception {
        CurvePoint dummyCurvePoint = getDummyCurvePoint();

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .param("curveId", String.valueOf(dummyCurvePoint.getCurveId()))
                        .param("term", String.valueOf(dummyCurvePoint.getTerm()))
                        .param("value", String.valueOf(dummyCurvePoint.getValue())))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/curvePoint/add"));
        Mockito.verify(curvePointService, Mockito.times(1))
                .saveCurvePoint(ArgumentMatchers.any(CurvePoint.class));
    }

    @Test
    public void validateTestWithWrongObject() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate"))

                .andExpect(MockMvcResultMatchers.view().name("/curvePoint/add"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(CoreMatchers.containsString("must not be null")))
                .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"));
    }

    @Test
    public void showUpdateFormTest() throws Exception {
        CurvePoint dummyCurvePoint = getDummyCurvePoint();
        Mockito.when(curvePointService.getCurvePointById(dummyCurvePoint.getId())).thenReturn(Optional.of(dummyCurvePoint));

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/1"))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/update"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"));
    }

    @Test
    public void updateCurvePointTestWithValidObject() throws Exception {
        CurvePoint dummyCurvePoint = getDummyCurvePoint();

        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
                        .param("curveId", String.valueOf(dummyCurvePoint.getCurveId()))
                        .param("term", String.valueOf(dummyCurvePoint.getTerm()))
                        .param("value", String.valueOf(dummyCurvePoint.getValue())))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/curvePoint/list"));
        Mockito.verify(curvePointService, Mockito.times(1))
                .saveCurvePoint(ArgumentMatchers.any(CurvePoint.class));
    }

    @Test
    public void updateCurvePointTestWithWrongObject() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1"))

                .andExpect(MockMvcResultMatchers.view().name("curvePoint/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(CoreMatchers.containsString("must not be null")))
                .andExpect(MockMvcResultMatchers.model().attributeExists("curvePoint"));
    }

    @Test
    public void deleteCurvePointTest() throws Exception {
        CurvePoint dummyCurvePoint = getDummyCurvePoint();
        Mockito.when(curvePointService.getCurvePointById(dummyCurvePoint.getId())).thenReturn(Optional.of(dummyCurvePoint));

        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1"))

                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/curvePoint/list"));
        Mockito.verify(curvePointService, Mockito.times(1))
                .deleteCurvePoint(ArgumentMatchers.any(CurvePoint.class));
    }

    public CurvePoint getDummyCurvePoint() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setId(1);
        curvePoint.setCurveId(10);
        curvePoint.setTerm(10d);
        curvePoint.setValue(30d);
        return curvePoint;
    }
}*/
}
