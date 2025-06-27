package com.schedule.entity;

import lombok.Data;

@Data
public class User {
    int id;
    String name;
    String passwd;
    String role;
}
