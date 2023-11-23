package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Address;
import com.projet9.patientservice.repository.AddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {
    @Mock
    AddressRepository addressRepository;

    @InjectMocks
    AddressServiceImpl addressServiceUnderTest;

    @Test
    public void getAddressTest(){
        Address  dummyAddress = getDummyAddress();
        Mockito.when(addressRepository.findByNumberAndStreet(dummyAddress.getNumber(),dummyAddress.getStreet()))
                .thenReturn(dummyAddress);

        Address address = addressServiceUnderTest.getAddress(dummyAddress.getNumber(),dummyAddress.getStreet());

        Assertions.assertThat(address).isEqualTo(dummyAddress);
        Mockito.verify(addressRepository, Mockito.times(1))
                .findByNumberAndStreet(dummyAddress.getNumber(), dummyAddress.getStreet());
    }

    public Address getDummyAddress(){
        Address dummyAddress = new Address();
        dummyAddress.setNumber("number");
        dummyAddress.setStreet("street");
        return dummyAddress;
    }
}
