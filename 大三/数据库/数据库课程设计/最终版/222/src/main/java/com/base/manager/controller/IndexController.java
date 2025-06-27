package com.base.manager.controller;

import com.base.manager.pojo.BaseAdminUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("login")
    public String tologin(){
        logger.info("定向登陆页");
        return "login";
    }

    @RequestMapping("home")
    public String home() {
        logger.info("进入主页处理");

        Object principal = SecurityUtils.getSubject().getPrincipal();
        if (principal instanceof BaseAdminUser) {
            BaseAdminUser user = (BaseAdminUser) principal;
            if (user.getRoleId() == 4) {
                return "/pfs/productManage";
            } else {
                return "/home";
            }
        }
        // 如果 principal 不是 BaseAdminUser 类型或者为 null，可以考虑返回一个默认的页面或者错误页面
        return "/home"; // 或者其他适当的处理
    }


    @RequestMapping("logout")
    public String logout(){
        logger.info("退出系统");
        Subject subject = SecurityUtils.getSubject();
        subject.logout(); // shiro底层删除session的会话信息
        return "redirect:login";
    }

}
