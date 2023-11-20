package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Address;
import com.projet9.patientservice.model.Patient;
//import com.projet9.patientservice.repository.AddressRepository;
import com.projet9.patientservice.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{

    private AddressRepository addressRepository;
    public AddressServiceImpl (AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

       public Address getAddress(String number, String street) {
        return addressRepository.findByNumberAndStreet(number,street);
    }
}
