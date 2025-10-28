package com.suarez.model;

import com.suarez.entity.CustomerData;
import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class Order {
    @Getter
    private int id;
    private CustomerData customer;
    private java.util.List<OrderItem> items;
    private Date created;
    private Date lastUpdated;
    private String status;
}
