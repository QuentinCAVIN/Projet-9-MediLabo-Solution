package com.projet9.diabetesassessmentservice.controller;

import com.projet9.diabetesassessmentservice.service.AssessmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DiabetesAssessmentController {

    AssessmentService assessmentService;

    public DiabetesAssessmentController(AssessmentService assessmentService)
    {
        this.assessmentService = assessmentService;
    }

    @GetMapping("/assessment/{id}")
    public ResponseEntity patientDiabetesAssessment(@PathVariable("id") int patientId) {
        return ResponseEntity.status(HttpStatus.OK).body(assessmentService.patientDiabetesAssessment(patientId));
    }
}