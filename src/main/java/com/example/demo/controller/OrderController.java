package com.example.demo.controller;

import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/order")
public class OrderController  {

    @Autowired
    OrderService orderService;

//    @PostMapping
//    public ResponseEntity createOrder(@RequestParam ("custId") int customerId,
//                                      @RequestBody List<OrderItemRequest> orderItemRequest){
//
//    }

}