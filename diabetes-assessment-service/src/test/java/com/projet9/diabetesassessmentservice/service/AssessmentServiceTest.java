package com.projet9.diabetesassessmentservice.service;

import com.projet9.diabetesassessmentservice.dto.GenderDto;
import com.projet9.diabetesassessmentservice.dto.NoteDto;
import com.projet9.diabetesassessmentservice.dto.PatientDto;
import com.projet9.diabetesassessmentservice.model.Assessment;
import com.projet9.diabetesassessmentservice.proxies.Proxy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.*;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AssessmentServiceTest {

    @Mock
    private Proxy proxy;
    @Mock
    private TriggerCounter triggerCounter;

    @InjectMocks
    AssessmentServiceImpl assessmentServiceUnderTest;

    @Nested
    @Tag("patientDiabetesAssessmentTest")
    public class patientDiabetesAssessmentTest {

        @Test
        public void PatientWith0Trigger() {
            PatientDto oldPatient = getOldPatient();
            setNumberOfTriggerInPatientNotes(0);
            Mockito.when(proxy.getPatient(oldPatient.getId())).thenReturn(oldPatient);

            Assessment assessment = assessmentServiceUnderTest.patientDiabetesAssessment(oldPatient.getId());
            Assertions.assertThat(assessment).isEqualTo(Assessment.None);
        }

        @Test
        public void OldPatientWith9Trigger() {
            PatientDto oldPatient = getOldPatient();
            setNumberOfTriggerInPatientNotes(9);
            Mockito.when(proxy.getPatient(oldPatient.getId())).thenReturn(oldPatient);

            Assessment assessment = assessmentServiceUnderTest.patientDiabetesAssessment(oldPatient.getId());
            Assertions.assertThat(assessment).isEqualTo(Assessment.EarlyOnset);
        }

        @Test
        public void OldPatientWith7Trigger() {
            PatientDto oldPatient = getOldPatient();
            setNumberOfTriggerInPatientNotes(7);
            Mockito.when(proxy.getPatient(oldPatient.getId())).thenReturn(oldPatient);

            Assessment assessment = assessmentServiceUnderTest.patientDiabetesAssessment(oldPatient.getId());
            Assertions.assertThat(assessment).isEqualTo(Assessment.InDanger);
        }

        @Test
        public void OldPatientWith2Trigger() {
            PatientDto oldPatient = getOldPatient();
            setNumberOfTriggerInPatientNotes(2);
            Mockito.when(proxy.getPatient(oldPatient.getId())).thenReturn(oldPatient);

            Assessment assessment = assessmentServiceUnderTest.patientDiabetesAssessment(oldPatient.getId());
            Assertions.assertThat(assessment).isEqualTo(Assessment.Borderline);
        }

        @Test
        public void YoungFemalePatientWith8Trigger() {
            PatientDto youngFemalePatient = getYoungFemalePatient();
            setNumberOfTriggerInPatientNotes(8);
            Mockito.when(proxy.getPatient(youngFemalePatient.getId())).thenReturn(youngFemalePatient);

            Assessment assessment = assessmentServiceUnderTest.patientDiabetesAssessment(youngFemalePatient.getId());
            Assertions.assertThat(assessment).isEqualTo(Assessment.EarlyOnset);
        }

        @Test
        public void YoungFemalePatientWith5Trigger() {
            PatientDto youngFemalePatient = getYoungFemalePatient();
            setNumberOfTriggerInPatientNotes(5);
            Mockito.when(proxy.getPatient(youngFemalePatient.getId())).thenReturn(youngFemalePatient);

            Assessment assessment = assessmentServiceUnderTest.patientDiabetesAssessment(youngFemalePatient.getId());
            Assertions.assertThat(assessment).isEqualTo(Assessment.InDanger);
        }

        @Test
        public void YoungMalePatientWith5Trigger() {
            PatientDto youngFemalePatient = getYoungMalePatient();
            setNumberOfTriggerInPatientNotes(5);
            Mockito.when(proxy.getPatient(youngFemalePatient.getId())).thenReturn(youngFemalePatient);

            Assessment assessment = assessmentServiceUnderTest.patientDiabetesAssessment(youngFemalePatient.getId());
            Assertions.assertThat(assessment).isEqualTo(Assessment.EarlyOnset);
        }

        @Test
        public void YoungMalePatientWith3Trigger() {
            PatientDto youngFemalePatient = getYoungMalePatient();
            setNumberOfTriggerInPatientNotes(3);
            Mockito.when(proxy.getPatient(youngFemalePatient.getId())).thenReturn(youngFemalePatient);

            Assessment assessment = assessmentServiceUnderTest.patientDiabetesAssessment(youngFemalePatient.getId());
            Assertions.assertThat(assessment).isEqualTo(Assessment.InDanger);
        }
    }

    ///////////////////////////SET UP///////////////////////////
    private void setNumberOfTriggerInPatientNotes(int numberOfTriggerInPatientNote) {
        Mockito.when(triggerCounter.countTriggers(Mockito.anyList())).thenReturn(numberOfTriggerInPatientNote);
    }

    private PatientDto getYoungMalePatient() {
        GenderDto dummyGender = new GenderDto();
        dummyGender.setGender("Male");

        PatientDto dummyPatient = new PatientDto();
        dummyPatient.setId(1);
        dummyPatient.setDateOfBirth(LocalDate.now().minusYears(1));
        dummyPatient.setGender(dummyGender);
        return dummyPatient;
    }

    private PatientDto getYoungFemalePatient() {
        GenderDto dummyGender = new GenderDto();
        dummyGender.setGender("Female");

        PatientDto dummyPatient = new PatientDto();
        dummyPatient.setId(1);
        dummyPatient.setDateOfBirth(LocalDate.now().minusYears(1));
        dummyPatient.setGender(dummyGender);
        return dummyPatient;
    }


    private PatientDto getOldPatient() {
        GenderDto dummyGender = new GenderDto();
        dummyGender.setGender("gender");

        PatientDto dummyPatient = new PatientDto();
        dummyPatient.setId(1);
        dummyPatient.setDateOfBirth(LocalDate.now().minusYears(90));
        dummyPatient.setGender(dummyGender);
        return dummyPatient;
    }

    private static List<NoteDto> getDummiesNotes() {
        NoteDto dummyNote = new NoteDto();
        dummyNote.setId("000000000000000000000001");
        dummyNote.setPatId(1);
        dummyNote.setNote("Note");

        return List.of(dummyNote, dummyNote);
    }
}