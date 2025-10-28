package com.suarez.model;

import com.suarez.entity.ProductData;
import lombok.Data;

@Data
public class OrderItem {
    private ProductData product;
    private int quantity;
}
