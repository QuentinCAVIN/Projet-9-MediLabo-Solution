package com.projet9.diabetesassessmentservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet9.diabetesassessmentservice.model.Assessment;
import com.projet9.diabetesassessmentservice.service.AssessmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = DiabetesAssessmentController.class)
@AutoConfigureMockMvc(addFilters = false)
public class DiabetesAssessmentControllerTest {

    @MockBean
    AssessmentServiceImpl assessmentService;
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Test
    public void patientDiabetesAssessment() throws Exception{
        int patientId = 1;
        Assessment assessment = Assessment.None;
        Mockito.when(assessmentService.patientDiabetesAssessment(patientId)).thenReturn(assessment);

        mockMvc.perform(MockMvcRequestBuilders.get("/assessment/{id}", patientId))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(assessment)));
        Mockito.verify(assessmentService, Mockito.times(1)).patientDiabetesAssessment(patientId);
    }
}
