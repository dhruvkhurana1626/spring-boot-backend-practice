package com.example.demo.service;

import com.example.demo.exception.CustomerNotFound;
import com.example.demo.model.Customer;
import com.example.demo.model.Review;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ReviewRepository reviewRepository;

    public List<Review> getReviewById(int id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if(optionalCustomer.isEmpty()){
            throw new CustomerNotFound("Invalid Id");
        }
        List<Review> reviewList = optionalCustomer.get().getReviewList();
        return  reviewList;
    }
}
