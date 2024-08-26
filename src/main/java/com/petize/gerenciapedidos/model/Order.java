package com.petize.gerenciapedidos.model;

import com.petize.gerenciapedidos.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Map;

@Entity
@Table(name = "tb_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate orderDate;
    @ElementCollection
    @CollectionTable(name = "tb_order_product_quantities", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyJoinColumn(name = "product_id")
    private Map<Product, Integer> productQuantities;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
