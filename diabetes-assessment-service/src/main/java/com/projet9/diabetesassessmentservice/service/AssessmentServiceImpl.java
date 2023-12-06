package com.projet9.diabetesassessmentservice.service;

import com.projet9.diabetesassessmentservice.dto.PatientDto;
import com.projet9.diabetesassessmentservice.model.Assessment;
import com.projet9.diabetesassessmentservice.proxies.Proxy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    private final Proxy proxy;
    private final TriggerCounter triggerCounters;

    public AssessmentServiceImpl(Proxy proxy, TriggerCounter triggerCounters) {
        this.proxy = proxy;
        this.triggerCounters = triggerCounters;
    }

    public Assessment patientDiabetesAssessment(int patientId) {
        Long numberOfTriggers = triggerCounters.countTriggers(proxy.getNotesByPatientId(patientId));
        PatientDto patientDto = proxy.getPatient(patientId);
        String gender = patientDto.getGender().getGender();
        int age = calculateAge(patientDto.getDateOfBirth());

        if (numberOfTriggers < 2) {
            return Assessment.None;//None
        }

        else if (age >= 30) {
            if (numberOfTriggers >= 8) {
                return Assessment.EarlyOnset;//Early onset
            } else if (numberOfTriggers >= 6) {
                return Assessment.InDanger;// in Danger
            } else if (numberOfTriggers >= 2) {
                return Assessment.Borderline;// Borderline
            }
        }

        else {
            switch (gender) {
                case "Female": {
                    if (numberOfTriggers >= 7) {
                        return Assessment.EarlyOnset;//Early onset
                    } else if (numberOfTriggers >= 4) {
                        return Assessment.InDanger;//In Danger
                    }
                }
                //gender peut prendre la valeur Unknown, on traite ce cas de la manière la plus contraignante par sécurité.
                default: {
                    if (numberOfTriggers >= 5) {
                        return  Assessment.EarlyOnset;//Early onset
                    } else if  (numberOfTriggers >= 3) {
                        return  Assessment.InDanger;//in Danger
                    }
                }
            }
        }
        return Assessment.ERROR;
    }

    private int calculateAge(LocalDate dateOfBirth) {
        LocalDate now = LocalDate.now();
        return Period.between(dateOfBirth, now).getYears();
    }
}