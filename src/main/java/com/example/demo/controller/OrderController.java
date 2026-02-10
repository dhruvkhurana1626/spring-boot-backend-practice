package com.example.demo.controller;

import com.example.demo.dto.request.OrderItemRequest;
import com.example.demo.dto.response.OrderEntityResponse;
import com.example.demo.exception.CustomerNotFound;
import com.example.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
@RequiredArgsConstructor
public class OrderController  {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity placeOrder(@RequestParam int customerId,
                                     @RequestBody List<OrderItemRequest> orderItemRequestList){
        try{
            OrderEntityResponse orderEntityResponse = orderService.placeOrder(customerId,orderItemRequestList);
            return new ResponseEntity(orderEntityResponse,HttpStatus.CREATED);
        }
        catch (CustomerNotFound e){
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

}