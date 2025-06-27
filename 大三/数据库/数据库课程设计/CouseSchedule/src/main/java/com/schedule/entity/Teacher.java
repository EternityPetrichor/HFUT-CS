package com.schedule.entity;

import lombok.*;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class Teacher {
    @NonNull
    int id;
    @NonNull
    String name;
    @NonNull
    String subject;
    String availability;
}
