package com.schedule.service.Impl;

import com.schedule.entity.Teacher;
import com.schedule.mapper.ClassMapper;
import com.schedule.mapper.TeacherMapper;
import com.schedule.service.TeacherService;
import com.schedule.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class TeacherServiceImpl implements TeacherService {

    @Override
    public void addTeacher(Teacher teacher) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            mapper.addTeacher(teacher);
        }
    }

    @Override
    public void deleteTeacherById(int id) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            mapper.deleteTeacherById(id);
        }
    }

    @Override
    public Teacher selectTeacherById(int id) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            return mapper.selectTeacherById(id);
        }
    }

    @Override
    public List<Teacher> selectAllTeachers() {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            return mapper.selectAllTeachers();
        }
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            mapper.updateTeacher(teacher);
        }
    }

}
