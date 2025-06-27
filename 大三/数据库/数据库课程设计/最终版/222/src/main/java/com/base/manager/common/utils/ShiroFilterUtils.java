package com.base.manager.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.base.manager.response.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


public class ShiroFilterUtils {

    private static final Logger logger = LoggerFactory.getLogger(ShiroFilterUtils.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();


    public static boolean isAjax(ServletRequest request){
        String header = ((HttpServletRequest) request).getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            logger.info("shiro工具类【ShiroFilterUtils.isAjax】当前请求,为Ajax请求");
            return Boolean.TRUE;
        }
        logger.debug("shiro工具类【ShiroFilterUtils.isAjax】当前请求,非Ajax请求");
        return Boolean.FALSE;
    }

    public static void out(HttpServletResponse response, ResponseResult result){
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");//设置编码
            response.setContentType("application/json");//设置返回类型
            out = response.getWriter();
            out.println(objectMapper.writeValueAsString(result));//输出
            logger.info("用户在线数量限制【ShiroFilterUtils.out】响应json信息成功");
        } catch (Exception e) {
            logger.error("用户在线数量限制【ShiroFilterUtils.out】响应json信息出错", e);
        }finally{
            if(null != out){
                out.flush();
                out.close();
            }
        }
    }
}
