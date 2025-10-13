package com.suarez.model;

import com.suarez.entity.CustomerData;
import com.suarez.entity.ProductData;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class Order {
    @Getter
    private int id;
    private CustomerData customer;
    private ProductData product;
    private int quantity;
    private Date created;
    private Date lastUpdated;
    private String status;
}
