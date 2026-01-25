package com.example.demo.dto.response;

import com.example.demo.model.Product;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class ReviewResponse {
    private String comment;
    private int rating;
    private CustomerResponse customerResponse;
    private ProductResponse productResponse;
}
