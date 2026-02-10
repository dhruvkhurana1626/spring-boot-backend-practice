package com.example.demo.service;

import com.example.demo.dto.request.OrderItemRequest;
import com.example.demo.dto.response.OrderEntityResponse;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.CustomerNotFound;
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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderEntityRepository orderEntityRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Transactional
    public OrderEntityResponse placeOrder(int customerId, List<OrderItemRequest> orderItemRequestList) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(()-> new CustomerNotFound("Customer Not Found"));

        OrderEntity orderEntity = new OrderEntity();

        List<OrderItems> orderItemsList = new ArrayList<>();
        int totalCost = 0;
        for(OrderItemRequest orderItemRequest : orderItemRequestList){
            int productId = orderItemRequest.getProductId();
            Product product = productRepository.findById(productId).get();

            int priceAtOrder = product.getPrice()*orderItemRequest.getQuantity();
            totalCost += priceAtOrder;

            OrderItems orderItems = new OrderItems();
            orderItems.setQuantity(orderItemRequest.getQuantity());
            orderItems.setPrice(priceAtOrder);
            orderItems.setProduct(product);
            orderItems.setOrderEntity(orderEntity);
            orderItemsList.add(orderItems);
        }

        orderEntity.setTotalCost(totalCost);
        orderEntity.setOrderStatus(OrderStatus.CONFIRMED);
        orderEntity.setCustomer(customer);
        orderEntity.setOrderItems(orderItemsList);

        OrderEntity savedOrderEntity = orderEntityRepository.save(orderEntity);

        OrderEntityResponse orderEntityResponse = OrderTransformer.orderEntityToOrderEntityResponse(savedOrderEntity);
        sendEmail(orderEntityResponse);
        return orderEntityResponse;
    }

    private void sendEmail(OrderEntityResponse orderEntityResponse) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dhruvjavadev162@gmail.com");
        message.setTo(orderEntityResponse.getCustomerResponse().getEmail());
        message.setSubject("Order Confirmed | Order ID: " + orderEntityResponse.getId());

        StringBuilder body = new StringBuilder();

        body.append("Hello ")
                .append(orderEntityResponse.getCustomerResponse().getName())
                .append(",\n\n");

        body.append("Thank you for placing your order with us. ")
                .append("Your order has been successfully placed and is currently in ")
                .append(orderEntityResponse.getOrderStatus())
                .append(" status.\n\n");

        body.append("Order Details:\n");
        body.append("Order ID   : ").append(orderEntityResponse.getId()).append("\n");
        body.append("Order Date : ").append(orderEntityResponse.getCreatedAt()).append("\n\n");

        body.append("Items Ordered:\n");

        orderEntityResponse.getOrderItemsResponse().forEach(item -> {
            body.append("- ")
                    .append(item.getProductResponse().getName())
                    .append(" | Qty: ").append(item.getQuantiy())
                    .append(" | Price: ₹").append(item.getProductResponse().getPrice()*item.getQuantiy())
                    .append("\n");
        });

        body.append("\nTotal Amount Paid: ₹")
                .append(orderEntityResponse.getTotalCost())
                .append("\n\n");

        body.append("If you have any questions regarding your order, ")
                .append("feel free to contact our support team.\n\n");

        body.append("We hope you enjoy your experience with us.\n\n");
        body.append("Best Regards,\n");
        body.append("Swiggato Team\n");
        body.append("— Powered by Java Backend");

        message.setText(body.toString());

        javaMailSender.send(message);
    }

}
