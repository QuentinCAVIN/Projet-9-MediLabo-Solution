package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Address;
import com.projet9.patientservice.model.Patient;
import com.projet9.patientservice.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService{

    private AddressRepository addressRepository;
    public AddressServiceImpl (AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    public Address getAddress(int id) {
        return addressRepository.findById(id).orElse(null);
    }

    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(int id) {
        addressRepository.deleteById(id);
    }
}
