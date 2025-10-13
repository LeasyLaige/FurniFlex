package com.suarez.model;

import lombok.Data;
import lombok.Getter;

import java.util.Date;

@Data
public class Customer {
    @Getter
    private int id;
    private String name;
    private String email;
    private String phone;
    private Date created;
    private Date lastUpdated;
}
