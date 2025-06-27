package com.schedule.service.Impl;

import com.schedule.entity.Course;
import com.schedule.entity.User;
import com.schedule.mapper.CourseMapper;
import com.schedule.mapper.UserMapper;
import com.schedule.service.CourseService;
import com.schedule.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CourseServiceImpl implements CourseService {
    @Override
    public void addCourse(Course course) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            mapper.addCourse(course);
        }

    }

    @Override
    public Course getCourseById(int id) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            return mapper.getCourseById(id);
        }
    }

    @Override
    public List<Course> getAllCourses() {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            return mapper.getAllCourses();
        }
    }

    @Override
    public void deleteCourseById(int id) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            mapper.deleteCourseById(id);
        }
    }

    @Override
    public void updateCourse(Course course) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            mapper.updateCourse(course);
        }
    }
}
