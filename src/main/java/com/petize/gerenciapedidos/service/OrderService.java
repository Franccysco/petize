package com.petize.gerenciapedidos.service;

import com.petize.gerenciapedidos.controller.dto.OrderDTO;
import com.petize.gerenciapedidos.controller.dto.OrderProductDTO;
import com.petize.gerenciapedidos.enums.OrderStatus;
import com.petize.gerenciapedidos.model.Order;
import com.petize.gerenciapedidos.model.Product;
import com.petize.gerenciapedidos.repository.OrderRepository;
import com.petize.gerenciapedidos.repository.ProductRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(OrderDTO orderDTO) {
        Map<Product, Integer> productQuantities = new HashMap<>();

        for (OrderProductDTO orderProduct : orderDTO.products()) {
            Product product = productRepository.findById(orderProduct.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            productQuantities.put(product, orderProduct.quantity());
        }

        Order order = new Order(null, orderDTO.orderDate(), productQuantities, orderDTO.status());
        return this.orderRepository.save(order);
    }

    public Order updateOrder(Long id, OrderDTO orderDTO) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        Map<Product, Integer> productQuantities = new HashMap<>();
        for (OrderProductDTO orderProductDTO : orderDTO.products()) {
            Product product = productRepository.findById(orderProductDTO.productId()).orElseThrow(() -> new RuntimeException("Product not found"));
            productQuantities.put(product, orderProductDTO.quantity());
        }
        order.setOrderDate(orderDTO.orderDate());
        order.setProductQuantities(productQuantities);
        order.setStatus(orderDTO.status());
        return this.orderRepository.save(order);
    }

    public Order updateStatus(Long id, String status) {
        Order order = this.orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(OrderStatus.valueOf(status));
        this.rabbitTemplate.convertAndSend("order-exchange", "order.status." + id,
                Map.of("orderId", id.toString(), "status", status));

        return order;
    }
}
