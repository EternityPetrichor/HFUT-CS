package com.schedule.service;

import com.schedule.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseService {
    void addCourse(Course course);
    Course getCourseById(int id);
    List<Course> getAllCourses();
    void deleteCourseById(int id);
    void updateCourse(Course course);
}
