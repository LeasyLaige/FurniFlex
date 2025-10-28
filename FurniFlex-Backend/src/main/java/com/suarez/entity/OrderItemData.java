package com.suarez.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_item_data")
public class OrderItemData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderData order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductData product;

    private int quantity;
}
