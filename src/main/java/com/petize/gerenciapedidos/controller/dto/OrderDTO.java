package com.petize.gerenciapedidos.controller.dto;

import com.petize.gerenciapedidos.enums.OrderStatus;

import java.time.LocalDate;
import java.util.List;

public record OrderDTO(LocalDate orderDate, List<OrderProductDTO> products, OrderStatus status) {
}

