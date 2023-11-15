package com.projet9.patientservice.service;

import com.projet9.patientservice.model.Address;

public interface AddressService {

    public Address getAddress (int id);

    public Address saveAddress (Address address);

    public void deleteAddress (int id);
}
