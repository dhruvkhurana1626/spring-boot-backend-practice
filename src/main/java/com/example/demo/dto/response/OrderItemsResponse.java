package com.example.demo.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class OrderItemsResponse {
    private ProductResponse productResponse;
    private int quantiy;
}
