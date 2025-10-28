package com.suarez.model;

import com.suarez.entity.CustomerData;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Data
public class Order {
    @Getter
    private int id;
    private CustomerData customer;
    private List<OrderItem> items;
    private Date created;
    private Date lastUpdated;
    private String status;

    // Shipping address and method
    private String recipientName;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String phone;
    private String shippingMethod; // standard | express
    private Double shippingCost;   // computed at checkout
}
