package com.example.demo.service;

import com.example.demo.Utility.Email;
import com.example.demo.Utility.Validation;
import com.example.demo.dto.request.CustomerRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.enums.Gender;
import com.example.demo.exception.CustomerNotFound;
import com.example.demo.exception.EmailAlreadyUsed;
import com.example.demo.exception.PhoneAlreadyUsed;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.transformers.CustomerTransformer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final Email email;
    private final Validation validation;

    public List<CustomerResponse> getCustomersByGender(Gender gender) {
        //Query to get all the Customer by Gender
        List<Customer> customerList = customerRepository.getCustomerByGender(gender);

        //if No Customer with same gender , return empty list
        if(customerList.isEmpty()) return new ArrayList<>();

        //Make a List of Customer Response
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        //populate it by a loop
        for(Customer customer : customerList){
            customerResponseList.add(CustomerTransformer.customerToCustomerResponse(customer));
        }
        //return the list of Customer Response with same gender
        return customerResponseList;
    }

    @Transactional
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        //exception
        validation.validateNewCustomer(customerRequest);

        //save
        Customer savedCustomer = customerRepository.save(CustomerTransformer.customerRequestToCustomer(customerRequest));

        //sending mail at confirmation
        email.sendEmailAtCustomerRegistration(savedCustomer);

        //reposonse
        return CustomerTransformer.customerToCustomerResponse(savedCustomer);
    }

    public CustomerResponse getCustomerById(int id) {
        //Checking if Customer with Id Exists - Exception also thrown via Validation Checker
        Customer customer = validation.checkIfCustomerExist(id);

        //return CustomerResponse if Customer Exist
        return CustomerTransformer.customerToCustomerResponse(customer);
    }

    public List<CustomerResponse> getCustomersByAge(int age) {
        //Query to get a List of Customer with Same Age
        List<Customer> customerList = customerRepository.findByAge(age);

        //Converting customer into CustomerResponse , and adding it into the customerResponse list
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        for(Customer c : customerList)
            customerResponseList.add(CustomerTransformer.customerToCustomerResponse(c));

        //return the CustomerResponse List
        return customerResponseList;
    }

}
