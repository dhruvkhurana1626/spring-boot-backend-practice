package com.example.demo.controller;

import com.example.demo.exception.CustomerNotFound;
import com.example.demo.model.Review;
import com.example.demo.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/review")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping
    public ResponseEntity getReviewById(@RequestParam("id") int id) {
        try {
            List<Review> response = reviewService.getReviewById(id);
            return new ResponseEntity(response, HttpStatus.FOUND);
        } catch (CustomerNotFound e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
