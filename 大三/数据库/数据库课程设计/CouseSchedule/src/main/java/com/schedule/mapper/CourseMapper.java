package com.schedule.mapper;

import com.schedule.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CourseMapper {
    @Insert("insert into courses(course_id, name, is_main_course) values (#{course_id}, #{name}, #{is_main_course})")
    void addCourse(Course course);
    @Select("SELECT * FROM courses WHERE course_id = #{id}")
    Course getCourseById(int id);
    @Select("SELECT * FROM courses")
    List<Course> getAllCourses();
    @Delete("DELETE FROM courses WHERE course_id = #{id}")
    void deleteCourseById(int id);
    @Update("UPDATE courses SET name = #{name}, is_main_course = #{is_main_course} WHERE course_id = #{course_id}")
    void updateCourse(Course course);

}
