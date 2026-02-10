package com.example.demo.dto.response;

import com.example.demo.enums.OrderStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class OrderEntityResponse {
    private CustomerResponse customerResponse;
    private int id;
    private int totalCost;
    private OrderStatus orderStatus;
    private Date createdAt;
    private List<OrderItemsResponse> orderItemsResponse;
}
