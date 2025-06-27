package com.furniture.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;

@Data
public class Sale {
    private int saleId;
    private int furnitureId;     // 家具ID
    private int customerId;     // 客户ID
    private Date saleDate;       // 销售日期
    private int quantity;        // 销售数量
    private double totalAmount;  // 销售总金额

    public Sale(int saleId, int furnitureId, int customerId, Date saleDate, int quantity) {
        this.saleId = saleId;
        this.furnitureId = furnitureId;
        this.customerId = customerId;
        this.saleDate = saleDate;
        this.quantity = quantity;
    }
}
