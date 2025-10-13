package com.suarez.model;

import lombok.Data;
import lombok.Getter;

@Data
public class Supplier {
    @Getter
    private int id;
    private String name;
    private String contactInfo;
    private String address;
}
