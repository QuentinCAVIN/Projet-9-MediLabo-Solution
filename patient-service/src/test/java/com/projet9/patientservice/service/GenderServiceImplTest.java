package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Gender;
import com.projet9.patientservice.repository.GenderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class GenderServiceImplTest {
    @Mock
    GenderRepository genderRepository;

    @InjectMocks
    GenderServiceImpl genderServiceUnderTest;

    @Test
    public void getGenderTest() {
        Gender dummyGender = getDummyGender();
        Mockito.when(genderRepository.findByGender(dummyGender.getGender())).thenReturn(dummyGender);

        Gender gender = genderServiceUnderTest.getGender(dummyGender.getGender());

        Assertions.assertThat(gender).isEqualTo(dummyGender);
        Mockito.verify(genderRepository, Mockito.times(1)).findByGender(dummyGender.getGender());
    }

    private Gender getDummyGender() {
        Gender dummyGender = new Gender();
        dummyGender.setGender("gender");
        return dummyGender;
    }
}