package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Gender;
import com.projet9.patientservice.repository.GenderRepository;
import org.springframework.stereotype.Service;

@Service
public class GenderServiceImpl implements GenderService {
    GenderRepository genderRepository;
    public GenderServiceImpl (GenderRepository genderRepository){
        this.genderRepository = genderRepository;
    }
    @Override
    public Gender getGender(String gender) {
        return genderRepository.findByGender(gender);
    }
}