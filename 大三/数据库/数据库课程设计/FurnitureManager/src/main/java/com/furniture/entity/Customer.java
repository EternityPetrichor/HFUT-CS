package com.furniture.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {
    private int customerId;
    private String customerName;
    private String contactInfo;
    private String address;
}
