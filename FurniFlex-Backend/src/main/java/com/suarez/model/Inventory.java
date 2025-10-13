package com.suarez.model;

import com.suarez.entity.ProductData;
import lombok.Data;
import lombok.Getter;

@Data
public class Inventory {
    @Getter
    private int id;
    private String name;
    private int reservedQuantity;
    private int Quantity;
    private ProductData product;
}
