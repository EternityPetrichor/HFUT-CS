package com.schedule.servlet;

import com.schedule.service.Impl.UserServiceImpl;
import com.schedule.service.UserService;
import com.schedule.utils.ThymeleafUtil;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    UserService service;
    @Override
    public void init() throws ServletException {
        service = new UserServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            String username = null;
            String password = null;
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("username")) username = cookie.getValue();
                if(cookie.getName().equals("password")) password = cookie.getValue();
            }
            if(username != null && password != null){
                //登陆校验
                if (service.login(username, password, req.getSession())){
                    resp.sendRedirect("index");
                    return;
                }
            }
        }

        resp.setContentType("text/html;charset=Utf-8");
        Context context = new Context();
        if (req.getSession().getAttribute("login-failure") != null){
            context.setVariable("failure", true);
            req.getSession().removeAttribute("login-failure");
        }
        if (req.getSession().getAttribute("user") != null){
            resp.sendRedirect("index");
            return;
        }
        ThymeleafUtil.process("login.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember-me");
        if (service.login(username, password, req.getSession())){
            if (remember != null){
                Cookie cookie_username = new Cookie("username", username);
                cookie_username.setMaxAge(60*60*24);
                Cookie cookie_password = new Cookie("password", password);
                cookie_password.setMaxAge(60*60*24);
                resp.addCookie(cookie_username);
                resp.addCookie(cookie_password);
            }

            resp.getWriter().write("Login Success!");
            resp.sendRedirect("index");
        }else {
            req.getSession().setAttribute("login-failure", new Object());
            this.doGet(req, resp);
        }
    }
}
