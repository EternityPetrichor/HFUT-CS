package com.schedule.mapper;

import com.schedule.entity.Class;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ClassMapper {
    @Insert("insert into classes(class_id, name, grade) values (#{class_id}, #{name}, #{grade})")
    void addClass(@Param("class_id") int class_id, @Param("name") String name, @Param("grade") int grade);
    @Select("select * from classes where class_id = #{id}")
    Class getClassById(int id);
    @Select("select * from classes")
    List<Class> getAllClasses();
    @Delete("delete from classes where class_id = #{id}")
    void deleteClassById(int id);
    @Update("update classes set name = #{name}, grade = #{grade} where class_id = #{class_id}")
    void updateClass(Class clazz);
}
