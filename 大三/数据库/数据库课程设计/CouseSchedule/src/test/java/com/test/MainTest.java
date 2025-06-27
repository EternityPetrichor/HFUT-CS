package com.test;

import com.schedule.entity.Class;
import com.schedule.entity.Course;
import com.schedule.entity.Teacher;
import com.schedule.mapper.ClassMapper;
import com.schedule.mapper.CourseMapper;
import com.schedule.mapper.TeacherMapper;
import com.schedule.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public void test(){
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            mapper.addCourse(new Course(2, "数学", 1));
        }

    }

    @Test
    public void test2(){
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            ClassMapper mapper = sqlSession.getMapper(ClassMapper.class);
            //mapper.addClass(3,"3班", 1);
            //mapper.getAllClasses().forEach(System.out::println);
            mapper.updateClass(new Class(3, "3班", 3));
        }
    }

    @Test
    public void test3(){
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            mapper.addTeacher(new Teacher(1, "王老师", "数学"));
        }
    }
    @Test
    public void test4(){
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            //System.out.println(mapper.selectTeacherById(1));
            //mapper.selectAllTeachers().forEach(System.out::println);
            mapper.updateTeacher(new Teacher(1, "李老师", "语文"));
        }
    }
    @Test
    public void test5(){
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            mapper.updateCourse(new Course(2, "数学", 0));

        }
    }
}
