package com.example.demo.controller;

import com.example.demo.dto.request.CustomerRequest;
import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.enums.Gender;
import com.example.demo.exception.CustomerNotFound;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping
    public ResponseEntity addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse = customerService.addCustomer(customerRequest);
        return new ResponseEntity(customerResponse, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCustomerById(@RequestParam("id") int id){
        try {
            Customer customer = customerService.getCustomerById(id);
            return new ResponseEntity (customer,HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping
    public ResponseEntity getCustomersByGender(@RequestParam Gender gender){
        try{
            List<CustomerResponse> customerResponseList = customerService.getCustomersByGender(gender);
            return new ResponseEntity(customerResponseList,HttpStatus.OK);
        } catch (CustomerNotFound e) {
            return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
