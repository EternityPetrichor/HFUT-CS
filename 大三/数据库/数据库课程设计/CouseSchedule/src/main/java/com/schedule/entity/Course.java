package com.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Course {
    int course_id;
    String name;
    int is_main_course;
}
