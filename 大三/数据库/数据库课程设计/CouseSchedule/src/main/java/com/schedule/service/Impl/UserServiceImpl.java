package com.schedule.service.Impl;

import com.schedule.entity.User;
import com.schedule.mapper.UserMapper;
import com.schedule.service.UserService;
import com.schedule.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpSession;

public class UserServiceImpl implements UserService {
    @Override
    public boolean login(String username, String password, HttpSession session) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            User user = userMapper.getUser(username, password);
            if(user == null) return false;
            session.setAttribute("user", user);
            return true;

        }
    }
}
