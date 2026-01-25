package com.example.demo.repository;

import com.example.demo.enums.Gender;
import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    List<Customer> getCustomerByGender(Gender gender);
    List<Customer> findByAge(int age);
}
