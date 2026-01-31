package com.example.demo.repository;

import com.example.demo.enums.Gender;
import com.example.demo.model.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    void shouldSucceed_whenValidCustomer_IsSave(){

        //arrange
        Customer customer = Customer.builder()
                .name("test-user")
                .age(40)
                .email("test@email.com")
                .gender(Gender.MALE)
                .phonenumber("8368799788")
                .build();

        //act
        Customer savedCustomer = customerRepository.save(customer);

        //assert
        Assertions.assertEquals("test-user",savedCustomer.getName());
        Assertions.assertNotNull(savedCustomer.getId());

    }
}
