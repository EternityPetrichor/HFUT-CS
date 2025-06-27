package com.schedule.mapper;

import com.schedule.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TeacherMapper {
    @Insert("insert into teachers(teacher_id, name, subjects) values (#{id}, #{name}, #{subject})")
    void addTeacher(Teacher teacher);
    @Delete("delete from teachers where teacher_id = #{id}")
    void deleteTeacherById(int id);
    @Select("select * from teachers where teacher_id = #{id}")
    Teacher selectTeacherById(int id);
    @Select("SELECT * FROM teachers")
    List<Teacher> selectAllTeachers();
    @Update("UPDATE teachers SET name = #{name}, subjects = #{subject}, availability = #{availability} WHERE teacher_id = #{id}")
    void updateTeacher(Teacher teacher);
}
