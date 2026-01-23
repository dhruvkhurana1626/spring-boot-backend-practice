package com.example.demo.service;

import com.example.demo.dto.request.CustomerRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.enums.Gender;
import com.example.demo.exception.CustomerNotFound;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.transformers.CustomerTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<CustomerResponse> getCustomersByGender(Gender gender) {
        List<Customer> customerList = customerRepository.getCustomerByGender(gender);
        if(customerList.isEmpty()) return new ArrayList<>();
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        for(Customer customer : customerList){
            customerResponseList.add(CustomerTransformer.customerToCustomerResponse(customer));
        }
        return customerResponseList;
    }

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {

        Customer savedCustomer = customerRepository.save(CustomerTransformer.customerRequestToCustomer(customerRequest));
        return CustomerTransformer.customerToCustomerResponse(savedCustomer);

    }

    public Customer getCustomerById(int id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isEmpty()){
            throw new CustomerNotFound("Invalid ID");
        }

        Customer customer = optionalCustomer.get();
        return customer;
    }
}
