package com.schedule.service.Impl;

import com.schedule.entity.Class;
import com.schedule.mapper.ClassMapper;
import com.schedule.mapper.CourseMapper;
import com.schedule.service.ClassService;
import com.schedule.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class ClassServiceImpl implements ClassService {
    @Override
    public void addClass(int class_id, String name, int grade) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            ClassMapper mapper = sqlSession.getMapper(ClassMapper.class);
            mapper.addClass(class_id, name, grade);
        }
    }

    @Override
    public Class getClassById(int id) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            ClassMapper mapper = sqlSession.getMapper(ClassMapper.class);
            return mapper.getClassById(id);
        }
    }

    @Override
    public List<Class> getAllClasses() {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            ClassMapper mapper = sqlSession.getMapper(ClassMapper.class);
            return mapper.getAllClasses();
        }
    }

    @Override
    public void deleteClassById(int id) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            ClassMapper mapper = sqlSession.getMapper(ClassMapper.class);
            mapper.deleteClassById(id);
        }
    }

    @Override
    public void updateClass(Class clazz) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            ClassMapper mapper = sqlSession.getMapper(ClassMapper.class);
            mapper.updateClass(clazz);
        }
    }
}

/*import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClassCourseMapper {

    @Select("SELECT * FROM classcourses WHERE class_course_id = #{id}")
    ClassCourse getClassCourseById(int id);

    @Select("SELECT * FROM classcourses WHERE class_id = #{classId}")
    List<ClassCourse> getClassCoursesByClassId(int classId);

    @Select("SELECT * FROM classcourses WHERE course_id = #{courseId}")
    List<ClassCourse> getClassCoursesByCourseId(int courseId);

    @Select("SELECT * FROM classcourses")
    List<ClassCourse> getAllClassCourses();

    @Insert("INSERT INTO classcourses (class_course_id, class_id, course_id) " +
            "VALUES (#{classCourseId}, #{classId}, #{courseId})")
    @Options(useGeneratedKeys = true, keyProperty = "classCourseId")
    void insertClassCourse(ClassCourse classCourse);

    @Update("UPDATE classcourses SET class_id = #{classId}, course_id = #{courseId} WHERE class_course_id = #{classCourseId}")
    void updateClassCourse(ClassCourse classCourse);

    @Delete("DELETE FROM classcourses WHERE class_course_id = #{id}")
    void deleteClassCourseById(int id);
}
*/
