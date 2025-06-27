package com.base.manager.controller;

import com.alibaba.fastjson.JSONObject;
import com.base.manager.common.utils.DateUtils;
import com.base.manager.dto.OrderSearchDTO;
import com.base.manager.dto.ProductDTO;
import com.base.manager.pojo.BaseAdminUser;
import com.base.manager.pojo.Order;
import com.base.manager.pojo.Product;
import com.base.manager.response.PageDataResult;
import com.base.manager.service.OrderService;
import com.base.manager.service.ProductService;
import net.sf.ehcache.search.expression.Or;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("order")
public class OrderController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrderService orderService;


    @RequestMapping("/orderManage")
    public String userManage() {
        Object principal = SecurityUtils.getSubject().getPrincipal();

        if (principal instanceof BaseAdminUser) {
            BaseAdminUser user = (BaseAdminUser) principal;

            if (user.getRoleId() == 4) {
                return "/pfs/orderManage";
            } else {
                return "/order/orderManage";
            }
        }

        // 如果不是 BaseAdminUser 或 principal 为 null 的情况下的处理
        return "/order/orderManage";
    }


    @PostMapping("getOrderList")
    @ResponseBody
    public PageDataResult getUserList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,/*@Valid PageRequest page,*/ OrderSearchDTO orderSearchDTO) {
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
            // 获取订单
            pdr = orderService.getOrderList(orderSearchDTO, pageNum ,pageSize);
            logger.info("产品列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("订单列表查询异常！", e);
        }
        return pdr;
    }


    @PostMapping("addOrders")
    @ResponseBody
    public Map<String,Object> addOrders(@RequestBody JSONObject jsonParam) {
        logger.info("设置产品[新增或更新]！Product:" + jsonParam);
        Order order = new Order();
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        order.setStatus(0);
        order.setUsername(user.getSysUserName());
        order.setUid(user.getId());
        order.setPid(new Integer(jsonParam.get("id").toString()));
        order.setNum(new Integer(jsonParam.get("num").toString()));
        order.setOrderTime(DateUtils.getCurrentDate());
        BigDecimal num = new BigDecimal(jsonParam.get("num").toString());
        BigDecimal price = new BigDecimal(jsonParam.get("price").toString());
        BigDecimal allprice = num.multiply(price);
        order.setAllprice(allprice);
        order.setPname(jsonParam.get("pname").toString());
        Map<String,Object> data = new HashMap();
        data = orderService.addOrders(order);
        return data;
    }


    @RequestMapping(value = "/delOrder", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delOrder(@RequestParam("id") Integer id) {
        logger.info("删除/恢复订单！id:" + id);
        Map<String, Object> data = new HashMap<>();
        //删除产品
        data = orderService.delOrder(id);

        return data;
    }


    @PostMapping("appOrder")
    @ResponseBody
    public Map<String,Object> appOrder(Order order) {
        logger.info("审批:" + order);
        Map<String,Object> data = new HashMap();
        data = orderService.updateOrder(order);
        return data;
    }
}
