package com.projet9.diabetesassessmentservice.service;

import com.projet9.diabetesassessmentservice.proxies.Proxy;
import org.springframework.stereotype.Service;

@Service
public class AssessmentServiceImpl implements AssessmentService{

    private final Proxy proxy;

    private final TriggerCounter triggerCounters;

    public AssessmentServiceImpl(Proxy proxy, TriggerCounter triggerCounters){
        this.proxy = proxy;
        this.triggerCounters = triggerCounters;
    }


    public long count (int patid){
        return triggerCounters.countTriggers(proxy.getNotesByPatientId(patid));
    }
}