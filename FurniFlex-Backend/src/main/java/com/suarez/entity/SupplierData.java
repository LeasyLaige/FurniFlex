package com.suarez.entity;

import lombok.Data;
import lombok.Getter;

import javax.persistence.*;

@Data
@Entity
@Table(name = "supplier_data")
public class SupplierData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    private String name;
    private String contactInfo;
    private String address;
}
