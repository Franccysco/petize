package com.petize.gerenciapedidos.service;

import com.petize.gerenciapedidos.enums.OrderStatus;
import com.petize.gerenciapedidos.model.Order;
import com.petize.gerenciapedidos.repository.OrderRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderStatusConsumer {
    @Autowired
    private OrderRepository orderRepository;

    @RabbitListener(queues = "order-status")
    public void receiveMessage(Map<String, String> message) {
        Long orderId = Long.parseLong(message.get("orderId"));
        String status = message.get("status");

        Order order = this.orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.valueOf(status));
        this.orderRepository.save(order);

        System.out.println("Status do pedido atualizado para: " + status);
    }
}
