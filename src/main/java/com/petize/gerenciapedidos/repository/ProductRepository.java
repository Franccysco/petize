package com.petize.gerenciapedidos.repository;

import com.petize.gerenciapedidos.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
