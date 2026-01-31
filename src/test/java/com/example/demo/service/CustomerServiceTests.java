package com.example.demo.service;

import com.example.demo.dto.response.CustomerResponse;
import com.example.demo.enums.Gender;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.transformers.CustomerTransformer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTests {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Test
    void whenCustomerIdExists_thenReturnSavedCustomer(){
        //arrange
        Customer customer = Customer.builder()
                .id(1)
                .name("dhruv")
                .age(99)
                .email("dhruvkhurana162@gmail.com")
                .gender(Gender.MALE)
                .phonenumber("8447464734")
                .build();

        Mockito.when(customerRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(customer));

        //act
        CustomerResponse customerResponse = CustomerTransformer.customerToCustomerResponse(customerService.getCustomerById(1));

        //assert
        Assertions.assertNotNull(customerResponse);
        Assertions.assertEquals("dhruv",customerResponse.getName());

    }
}
