package com.projet9.diabetesassessmentservice.controller;

import com.projet9.diabetesassessmentservice.proxies.Proxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DiabetesAssessmentController {

    private final Proxy proxy;

    DiabetesAssessmentController(Proxy proxy) {
        this.proxy = proxy;
    }

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.status(HttpStatus.OK).body(proxy.getPatients());
    }
}
