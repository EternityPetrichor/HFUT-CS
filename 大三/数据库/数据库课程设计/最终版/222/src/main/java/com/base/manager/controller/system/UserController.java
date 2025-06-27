package com.base.manager.controller.system;

import com.base.manager.response.PageDataResult;
import com.base.manager.dto.LoginDTO;
import com.base.manager.dto.UserSearchDTO;
import com.base.manager.pojo.BaseAdminUser;
import com.base.manager.service.AdminUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminUserService adminUserService;

    @RequestMapping("login")
    @ResponseBody
    public Map<String,Object> login(HttpServletRequest request, LoginDTO loginDTO, HttpSession session){
        logger.info("进行登陆");
        Map<String,Object> data = new HashMap();
        // 使用 shiro 进行登录
        Subject subject = SecurityUtils.getSubject();
        String userName = loginDTO.getUsername().trim();
        String password = loginDTO.getPassword().trim();
        String rememberMe = loginDTO.getRememberMe();
        String host = request.getRemoteAddr();
        System.out.println(password);
        //获取token
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password,host);
        //UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        System.out.println(token);
        // 设置 remenmberMe 的功能
        if (rememberMe != null && rememberMe.equals("on")) {
            token.setRememberMe(true);
        }

        try {
            subject.login(token);
            // 登录成功
            BaseAdminUser user = (BaseAdminUser) subject.getPrincipal();

            session.setAttribute("user", user.getSysUserName());
            data.put("code",1);
            data.put("url","/home");
            //data.put("message","登陆成功");
            logger.info(user.getSysUserName()+"登陆成功");
        } catch (UnknownAccountException e) {
            data.put("code",0);
            data.put("message",userName+"账号不存在");
            logger.error(userName+"账号不存在");
            return data;
        }catch (DisabledAccountException e){
            data.put("code",0);
            data.put("message",userName+"账号异常");
            logger.error(userName+"账号异常");
            return data;
        }catch (AuthenticationException e){
            data.put("code",0);
            data.put("message",userName+"密码错误");
            logger.error(userName+"密码错误");
            return data;
        }
        return data;
    }

    @RequestMapping("setPwd")
    @ResponseBody
    public Map<String,Object> setP(String pwd, String isPwd){
        logger.info("进行密码重置");
        Map<String,Object> data = new HashMap();
        if(!pwd.equals(isPwd)){
            data.put("code",0);
            data.put("message","两次输入的密码不一致!");
            logger.error("两次输入的密码不一致!");
            return data;
        }
        //获取当前登陆的用户信息
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        int result = adminUserService.updatePwd(user.getSysUserName(),pwd);
        if(result == 0){
            data.put("code",0);
            data.put("msg","修改密码失败！");
            logger.error("用户修改密码失败！");
            return data;
        }
        data.put("code",1);
        data.put("msg","修改密码成功！");
        logger.info("用户修改密码成功！");
        return data;
    }


    @RequestMapping("/userManage")
    public String userManage() {
        return "/user/userManage";
    }


    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult getUserList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,/*@Valid PageRequest page,*/ UserSearchDTO userSearch) {
        /*logger.info("分页查询用户列表！搜索条件：userSearch：" + userSearch + ",pageNum:" + page.getPageNum()
                + ",每页记录数量pageSize:" + page.getPageSize());*/
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            // 获取用户列表
            pdr = adminUserService.getUserList(userSearch, pageNum ,pageSize);
            logger.info("用户列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户列表查询异常！", e);
        }
        return pdr;
    }


    @RequestMapping(value = "/setUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> setUser(BaseAdminUser user) {
        logger.info("设置用户[新增或更新]！user:" + user);
        Map<String,Object> data = new HashMap();
        if(user.getId() == null){
            data = adminUserService.addUser(user);
        }else{
            data = adminUserService.updateUser(user);
        }
        return data;
    }

    @RequestMapping(value = "/updateUserStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateUserStatus(@RequestParam("id") Integer id,@RequestParam("status") Integer status) {
        logger.info("删除/恢复用户！id:" + id+" status:"+status);
        Map<String, Object> data = new HashMap<>();
        if(status == 0){
            //删除用户
            data = adminUserService.delUser(id,status);
        }else{
            //恢复用户
            data = adminUserService.recoverUser(id,status);
        }
        return data;
    }


}
