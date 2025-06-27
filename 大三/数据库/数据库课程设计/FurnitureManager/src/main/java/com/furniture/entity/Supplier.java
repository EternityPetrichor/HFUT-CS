package com.furniture.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Supplier {
    int id;
    String name;
    String info;
    String addr;
}
