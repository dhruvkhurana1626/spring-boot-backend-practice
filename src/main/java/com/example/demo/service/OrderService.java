package com.example.demo.service;

import com.example.demo.Utility.Email;
import com.example.demo.Utility.Validation;
import com.example.demo.dto.request.OrderItemRequest;
import com.example.demo.dto.response.OrderEntityResponse;
import com.example.demo.enums.OrderStatus;
import com.example.demo.model.Customer;
import com.example.demo.model.OrderEntity;
import com.example.demo.model.OrderItems;
import com.example.demo.model.Product;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.OrderEntityRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.transformers.OrderTransformer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final Validation validation;
    private final Email email;
    private final OrderEntityRepository orderEntityRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Transactional
    public OrderEntityResponse placeOrder(int customerId,
                                          List<OrderItemRequest> orderItemRequestList) {

        // Validate customer existence
        Customer customer = validation.checkIfCustomerExist(customerId);

        // Validate order items list
        validation.validateOrderItemsList(orderItemRequestList);

        // Create new order entity
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomer(customer);

        List<OrderItems> orderItemsList = new ArrayList<>();
        int totalCost = 0;

        // Process each order item
        for (OrderItemRequest orderItemRequest : orderItemRequestList) {

            // Fetch product safely
            Product product = productRepository.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            int quantity = orderItemRequest.getQuantity();

            // Calculate price at the time of order
            int priceAtOrder = product.getPrice() * quantity;
            totalCost += priceAtOrder;

            // Create order item entity
            OrderItems orderItems = new OrderItems();
            orderItems.setQuantity(quantity);
            orderItems.setPrice(priceAtOrder);
            orderItems.setProduct(product);

            // Map order item to parent order
            orderItems.setOrderEntity(orderEntity);

            orderItemsList.add(orderItems);
        }

        // Set order details
        orderEntity.setOrderItems(orderItemsList);
        orderEntity.setTotalCost(totalCost);
        orderEntity.setOrderStatus(OrderStatus.CONFIRMED);

        // Persist order (order items cascade if configured)
        OrderEntity savedOrderEntity = orderEntityRepository.save(orderEntity);

        // Convert saved entity to response DTO
        OrderEntityResponse orderEntityResponse =
                OrderTransformer.orderEntityToOrderEntityResponse(savedOrderEntity);

        // Send order confirmation email
        email.sendEmailAfterOrderPlaced(orderEntityResponse);

        return orderEntityResponse;
    }


}
