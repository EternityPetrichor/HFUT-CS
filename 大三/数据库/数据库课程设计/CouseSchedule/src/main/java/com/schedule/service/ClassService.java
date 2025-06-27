package com.schedule.service;

import com.schedule.entity.Class;

import java.util.List;

public interface ClassService {
    void addClass(int class_id, String name, int grade);
    Class getClassById(int id);
    List<Class> getAllClasses();
    void deleteClassById(int id);
    void updateClass(Class clazz);
}
