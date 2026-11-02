package com.example.order_service.controllers;

import com.example.base_domain.dtos.Order;
import com.example.base_domain.dtos.OrderEvent;
import com.example.order_service.kafka.OrderProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderController {
    final private OrderProducer producer;

    @PostMapping("/orders")
    public String placeOrder(@RequestBody Order order){
        order.setOrderId(UUID.randomUUID().toString());
        OrderEvent orderEvent = OrderEvent
                .builder()
                .status("PENDING")
                .message("Order status is in pending state")
                .order(order)
                .build();
        producer.sendMessage(orderEvent);
        return "Order Placed Successfully";
    }
}
