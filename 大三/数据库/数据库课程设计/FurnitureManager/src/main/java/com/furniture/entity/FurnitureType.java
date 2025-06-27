package com.furniture.entity;

import lombok.Data;
import lombok.NonNull;

@Data
public class FurnitureType {
    @NonNull
    private int tid;
    @NonNull
    private String name;
    private String description;



}
