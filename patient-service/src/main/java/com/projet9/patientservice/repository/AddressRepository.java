package com.projet9.patientservice.repository;

import com.projet9.patientservice.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

    Address findByNumberAndStreet(String number, String street);
}
