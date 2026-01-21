package com.example.demo.service;

import com.example.demo.exception.CustomerNotFound;
import com.example.demo.model.Address;
import com.example.demo.model.Customer;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service

public class AddressService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AddressRepository addressRepository;

    public Address addAddress(Address address, int id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isEmpty()){
            throw new CustomerNotFound("Invalid Id");
        }

        Customer customer = optionalCustomer.get();
        Address savedAddress = addressRepository.save(address);
        customer.setAddress(savedAddress);
        customerRepository.save(customer);

        return savedAddress;
    }
}
