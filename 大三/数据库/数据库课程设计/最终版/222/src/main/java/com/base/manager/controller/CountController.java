package com.base.manager.controller;

import com.base.manager.dto.UserCountDTO;
import com.base.manager.service.CountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("count")
public class CountController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CountService countService;

    @RequestMapping("/countManage")
    public String countManage() {
        return "/count/count";
    }

    @PostMapping("getAllUser")
    @ResponseBody
    public Map<String,Object> getAlluser(UserCountDTO userCountDTO) {
        Map<String,Object> data = new HashMap();
        try {
            data = countService.getAllUser(userCountDTO);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户数据查询异常！", e);
        }
        return data;
    }


    @PostMapping("getSaleOrder")
    @ResponseBody
    public Map<String,Object> getSaleOrder() {
        Map<String,Object> data = new HashMap();
        try {
            data = countService.getSaleOrder();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户数据查询异常！", e);
        }
        return data;
    }


    @PostMapping("getSaleBing")
    @ResponseBody
    public Map<String,Object> getSaleBing() {
        Map<String,Object> data = new HashMap();
        try {
            data = countService.getSaleBing();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户数据查询异常！", e);
        }
        return data;
    }



    @PostMapping("getPfsBing")
    @ResponseBody
    public Map<String,Object> getPfsBing() {
        Map<String,Object> data = new HashMap();
        try {
            data = countService.getPfsBing();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户数据查询异常！", e);
        }
        return data;
    }

    @PostMapping("getSaleLine")
    @ResponseBody
    public Map<String,Object> getSaleLine() {
        Map<String,Object> data = new HashMap();
        try {
            data = countService.getSaleLine();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户数据查询异常！", e);
        }
        return data;
    }
}
