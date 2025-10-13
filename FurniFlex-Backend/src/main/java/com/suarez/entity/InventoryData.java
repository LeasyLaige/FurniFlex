package com.suarez.entity;

import lombok.Data;
import lombok.Getter;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "inventory")
public class InventoryData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private int id;

    private String name;
    private int reservedQuantity;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductData productData;
}
