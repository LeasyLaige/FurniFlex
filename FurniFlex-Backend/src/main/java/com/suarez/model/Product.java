package com.suarez.model;

import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product {
    @Getter
    private int id;
    private String name;
    private String description;
    private String type;
    private BigDecimal price;
    private Date lastUpdated;
    private Date created;
}
