package com.furniture.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.sql.Date;


@Data
@AllArgsConstructor
public class Inbound {
    int inboundId;
    /*Furniture furniture;
    Supplier supplier;*/
    int furnitureId;
    int supplierId;
    Date inboundDate;
    int quantity;
}
