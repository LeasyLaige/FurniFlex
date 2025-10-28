package com.suarez.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "product_data")
public class ProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private int id;

    private String name;
    private String description;
    private String type;
    private String image;
    private BigDecimal price;
    // Extended specifications
    private String sku;
    private String dimensions; // e.g., 84" W x 35" D x 30" H
    private String material;   // e.g., Solid oak, Linen blend
    private String color;      // e.g., Walnut, Charcoal
    private String weight;     // e.g., 65 lb

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date lastUpdated;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date created;
}
