package com.schedule.service;

import javax.servlet.http.HttpSession;

public interface UserService {
    boolean login(String username, String password, HttpSession session);
}
