package com.furniture.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
@AllArgsConstructor
public class Payment {
    private int paymentId;
    private int saleId;
    private Date paymentDate;
    private double amount;
    private String paymentMethod;
}
