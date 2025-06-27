package com.furniture.Servlet;

import com.furniture.service.FurnitureService;
import com.furniture.service.Impl.FurnitureImpl;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/index")
public class FurnitureServlet extends HttpServlet {
    FurnitureService service;
    @Override
    public void init() throws ServletException {
        service = new FurnitureImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=Utf-8");
        Context context = new Context();
        context.setVariable("furniture_list", service.findAll());
    }
}
