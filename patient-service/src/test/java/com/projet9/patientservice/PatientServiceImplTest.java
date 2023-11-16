package com.projet9.patientservice;

import com.projet9.patientservice.model.Address;
import com.projet9.patientservice.model.Gender;
import com.projet9.patientservice.model.Patient;
import com.projet9.patientservice.repository.PatientRepository;
import com.projet9.patientservice.service.PatientServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {
    @Mock
    PatientRepository patientRepository;

    @InjectMocks
    public PatientServiceImpl patientServiceUnderTest;

    @Test
    public void getPatientTest() {
        Patient dummyPatient = getDummyPatient();
        Mockito.when(patientRepository.findById(dummyPatient.getId())).thenReturn(Optional.of(dummyPatient));

        Patient patient = patientServiceUnderTest.getPatient(dummyPatient.getId()).get();

        Assertions.assertThat(patient).isEqualTo(dummyPatient);
    }

    @Test
    public void getPatientsTest() {
        List<Patient> dummyPatients = Arrays.asList(getDummyPatient(), getDummyPatient());
        Mockito.when(patientRepository.findAll()).thenReturn(dummyPatients);

        List<Patient> patients = patientServiceUnderTest.getPatients();

        Assertions.assertThat(patients).isEqualTo(dummyPatients);
    }

    @Test
    public void savePatientTest() {
        Patient dummyPatient = getDummyPatient();

        patientServiceUnderTest.savePatient(dummyPatient);

        Mockito.verify(patientRepository, Mockito.times(1)).save(dummyPatient);
    }

    @Test
    public void deletePatientTest() {
        Patient dummyPatient = getDummyPatient();

        patientServiceUnderTest.deletePatient(dummyPatient.getId());

        Mockito.verify(patientRepository, Mockito.times(1)).deleteById(dummyPatient.getId());
    }

    public Patient getDummyPatient() {
        Gender dummyGender = new Gender();
        dummyGender.setGender("gender");

        Address dummyAddress = new Address();
        dummyAddress.setNumber("10 bis");
        dummyAddress.setStreet("main street");

        Patient dummyPatient = new Patient();
        dummyPatient.setId(1);
        dummyPatient.setFirstName("First Name");
        dummyPatient.setLastName("Last Name");
        dummyPatient.setDateOfBirth(new Date());
        dummyPatient.setPhoneNumber("06 07 08 09 10");
        dummyPatient.setGender(dummyGender);
        dummyPatient.setAddress(dummyAddress);
        return dummyPatient;
    }
}