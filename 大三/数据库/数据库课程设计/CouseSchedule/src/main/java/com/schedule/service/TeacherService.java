package com.schedule.service;

import com.schedule.entity.Teacher;

import java.util.List;

public interface TeacherService {
    void addTeacher(Teacher teacher);
    void deleteTeacherById(int id);
    Teacher selectTeacherById(int id);
    List<Teacher> selectAllTeachers();
    void updateTeacher(Teacher teacher);
}
