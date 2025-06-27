package com.furniture.entity;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Furniture {
    private int furnitureId;
    private String furnitureName;
    private int typeId;
    private int supplierId;
    private double purchasePrice;
    private double salePrice;
    private int stockQuantity;

    //public Furniture() {}
}
