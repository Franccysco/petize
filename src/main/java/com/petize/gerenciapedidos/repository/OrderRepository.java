package com.petize.gerenciapedidos.repository;

import com.petize.gerenciapedidos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
