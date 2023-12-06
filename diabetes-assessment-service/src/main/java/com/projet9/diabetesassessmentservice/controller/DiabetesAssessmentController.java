package com.projet9.diabetesassessmentservice.controller;

import com.projet9.diabetesassessmentservice.proxies.Proxy;
import com.projet9.diabetesassessmentservice.service.AssessmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DiabetesAssessmentController {

    private final Proxy proxy;

    @Autowired
    AssessmentServiceImpl assessmentService;

    DiabetesAssessmentController(Proxy proxy) {
        this.proxy = proxy;
    }

    @GetMapping("/test/{id}")
    public ResponseEntity test(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(assessmentService.count(id));
    }
}
