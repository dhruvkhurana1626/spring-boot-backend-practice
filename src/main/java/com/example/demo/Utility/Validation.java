package com.example.demo.Utility;

import com.example.demo.dto.request.CustomerRequest;
import com.example.demo.dto.request.OrderItemRequest;
import com.example.demo.exception.CustomerNotFound;
import com.example.demo.exception.EmailAlreadyUsed;
import com.example.demo.exception.PhoneAlreadyUsed;
import com.example.demo.model.Customer;
import com.example.demo.model.OrderItems;
import com.example.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Validation {

    private final CustomerRepository customerRepository;

    public Customer checkIfCustomerExist(int customerId){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFound("Customer Id is Invalid"));

        return customer;
    }

    public void validateNewCustomer(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyUsed("Email already used");
        }
        if (customerRepository.existsByPhonenumber(request.getPhonenumber())) {
            throw new PhoneAlreadyUsed("Phone number already used");
        }
    }

    public void validateOrderItemsList(List<OrderItemRequest> orderItemRequestList){
        if (orderItemRequestList == null || orderItemRequestList.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
    }

}
