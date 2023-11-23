package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Address;
import com.projet9.patientservice.model.Gender;
import com.projet9.patientservice.model.Patient;
import com.projet9.patientservice.repository.PatientRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PatientServiceImplTest {
    @Mock
    PatientRepository patientRepository;
    @Mock
    GenderService genderService;
    @Mock
    AddressService addressService;

    @InjectMocks
    public PatientServiceImpl patientServiceUnderTest;

    @Test
    public void getPatientByIdTest() {
        Patient dummyPatient = getDummyPatient();
        Mockito.when(patientRepository.findById(dummyPatient.getId())).thenReturn(Optional.of(dummyPatient));

        Patient patient = patientServiceUnderTest.getPatient(dummyPatient.getId());

        Assertions.assertThat(patient).isEqualTo(dummyPatient);
    }

    @Test
    public void getPatientByAddressTest() {
        Patient dummyPatient = getDummyPatient();
        Mockito.when(patientRepository.findByFirstNameAndLastName(
                dummyPatient.getFirstName(), dummyPatient.getLastName())).thenReturn(Optional.of(dummyPatient));

        Patient patient = patientServiceUnderTest.getPatient(
                dummyPatient.getFirstName(), dummyPatient.getLastName()).get();

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
        Gender genderInDatabase = new Gender();
        genderInDatabase.setId(1);
        genderInDatabase.setGender("gender in database");

        Mockito.when(genderService.getGender(dummyPatient.getGender().getGender())).thenReturn(genderInDatabase);
        Mockito.when(addressService.getAddress(
                dummyPatient.getAddress().getNumber(), dummyPatient.getAddress().getStreet()))
                        .thenReturn(null);

        patientServiceUnderTest.savePatient(dummyPatient);

        Mockito.verify(patientRepository, Mockito.times(1)).save(dummyPatient);
        Assertions.assertThat(dummyPatient.getGender().getId()).isEqualTo(genderInDatabase.getId());
        Assertions.assertThat(dummyPatient.getAddress()).isNotNull();
    }

    @Test
    public void savePatientWithExistingAddressTest() {
        Patient dummyPatient = getDummyPatient();
        Gender genderInDatabase = new Gender();
        genderInDatabase.setId(1);
        genderInDatabase.setGender(dummyPatient.getGender().getGender());
        Address addressInDatabase = new Address();
        addressInDatabase.setId(1);
        addressInDatabase.setStreet(dummyPatient.getAddress().getStreet());
        addressInDatabase.setStreet(dummyPatient.getAddress().getStreet());

        Mockito.when(genderService.getGender(dummyPatient.getGender().getGender())).thenReturn(genderInDatabase);
        Mockito.when(addressService.getAddress(
                        dummyPatient.getAddress().getNumber(), dummyPatient.getAddress().getStreet()))
                .thenReturn(addressInDatabase);

        patientServiceUnderTest.savePatient(dummyPatient);

        Mockito.verify(patientRepository, Mockito.times(1)).save(dummyPatient);
        Assertions.assertThat(dummyPatient.getId()).isEqualTo(genderInDatabase.getId());
        Assertions.assertThat(dummyPatient.getId()).isEqualTo(genderInDatabase.getId());
        Assertions.assertThat(dummyPatient.getAddress().getId()).isEqualTo(addressInDatabase.getId());
    }


    @Test
    public void deletePatientTest() {
        Patient dummyPatient = getDummyPatient();

        patientServiceUnderTest.deletePatient(dummyPatient.getId());

        Mockito.verify(patientRepository, Mockito.times(1)).deleteById(dummyPatient.getId());
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