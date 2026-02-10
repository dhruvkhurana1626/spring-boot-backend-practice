package com.example.demo.service;

import com.example.demo.dto.request.CustomerRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.enums.Gender;
import com.example.demo.exception.CustomerNotFound;
import com.example.demo.exception.EmailAlreadyUsed;
import com.example.demo.exception.PhoneAlreadyUsed;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.transformers.CustomerTransformer;
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

    @Autowired(required = false)
    private JavaMailSender javaMailSender;

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
        //exception
        if (customerRepository.existsByEmail(customerRequest.getEmail())) {
            throw new EmailAlreadyUsed("Email already used");
        }

        if (customerRepository.existsByPhonenumber(customerRequest.getPhonenumber())) {
            throw new PhoneAlreadyUsed("Phone number already used");
        }

        //save
        Customer savedCustomer = customerRepository.save(CustomerTransformer.customerRequestToCustomer(customerRequest));

        //sending mail at confirmation
        sendEmail(savedCustomer);

        //reposonse
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

    public List<CustomerResponse> getCustomersByAge(int age) {
        List<Customer> customerList = customerRepository.findByAge(age);
        if(customerList.size()==0)return new ArrayList<>();
        List<CustomerResponse> customerResponseList = new ArrayList<>();
        for(Customer c : customerList) customerResponseList.add(CustomerTransformer.customerToCustomerResponse(c));
        return customerResponseList;
    }

    private void sendEmail(Customer customer){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dhruvjavadev162@gmail.com");
        message.setTo(customer.getEmail());
        message.setSubject("Thanks for Creating an Account with E-Commerce Project");
        message.setText(
                "Hello " + customer.getName() + ",\n\n" +
                        "Welcome to the Java Backend Ecommerce Project.\n\n" +
                        "Your account has been successfully created. We kindly request you to " +
                        "confirm your registered phone number (" + customer.getPhonenumber() + ") " +
                        "to ensure uninterrupted access to our services.\n\n" +
                        "You are now free to explore and test the available APIs, including customer, " +
                        "product, order, and review features.\n\n" +
                        "If you have any questions or face any issues, feel free to reach out.\n\n" +
                        "Best regards,\n" +
                        "Dhruv Khurana\n" +
                        "Backend Engineer\n" +
                        "Java | Spring Boot"
        );

        javaMailSender.send(message);
    }
}
