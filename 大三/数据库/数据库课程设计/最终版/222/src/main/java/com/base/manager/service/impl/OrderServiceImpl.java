package com.base.manager.service.impl;

import com.base.manager.dao.OrderMapper;
import com.base.manager.dao.ProductMapper;
import com.base.manager.dto.OrderSearchDTO;
import com.base.manager.dto.ProductDTO;
import com.base.manager.pojo.BaseAdminUser;
import com.base.manager.pojo.Order;
import com.base.manager.pojo.Product;
import com.base.manager.response.PageDataResult;
import com.base.manager.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
@Service
public class OrderServiceImpl implements OrderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private ProductMapper productMapper;
    @Override
    public PageDataResult getOrderList(OrderSearchDTO orderSearchDTO, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();

        try {
            Object principal = SecurityUtils.getSubject().getPrincipal();

            if (principal instanceof BaseAdminUser) {
                BaseAdminUser user = (BaseAdminUser) principal;
                //BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
                if (user.getRoleId() == 4) {
                    orderSearchDTO.setUid(user.getId());
                }

                // 开始分页
                PageHelper.startPage(pageNum, pageSize);

                // 获取订单列表并按时间降序排序
                List<OrderSearchDTO> orderList = orderMapper.getOrderList(orderSearchDTO);

                if (orderList != null && !orderList.isEmpty()) {
                    PageInfo<OrderSearchDTO> pageInfo = new PageInfo<>(orderList);
                    pageDataResult.setList(orderList);
                    pageDataResult.setTotals((int) pageInfo.getTotal());
                } else {
                    pageDataResult.setList(Collections.emptyList());
                    pageDataResult.setTotals(0);
                }
            }
        } catch (Exception e) {
            logger.error("获取订单列表时出错", e);
            // 处理异常（例如，在 PageDataResult 中设置错误信息）
        }

        return pageDataResult;
    }

    @Override
    public Map<String, Object> addOrders(Order order) {
        logger.info("设置定单[新增或更新]！order:" + order);
        Map<String,Object> data = new HashMap();
        int result=orderMapper.insert(order);
        if(result == 0){
            data.put("code",0);
            data.put("msg","新增定单失败");
            logger.error("新增定单失败");
            return data;
        }
        data.put("code",1);
        data.put("msg","新增定单成功");
        return data;
    }

    @Override
    public Map<String, Object> delOrder(Integer id) {
        Map<String, Object> data = new HashMap<>();
        try {
            int result = orderMapper.deleteByPrimaryKey(id);
            if(result == 0){
                data.put("code",0);
                data.put("msg","删除订单失败");
            }
            data.put("code",1);
            data.put("msg","删除订单成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除订单异常！", e);
        }
        return data;
    }

    @Override
    public Map<String, Object> updateOrder(Order order) {
        Map<String,Object> data = new HashMap();
        try{
            //扣除库在
            Product product = productMapper.selectByPrimaryKey(order.getPid());
            Integer stoct = product.getStock();
            Integer num  =  order.getNum();
            Integer i = stoct-num;
            product.setStock(i);
            if(i>= 0){
                orderMapper.updateByPrimaryKeySelective(order);
                int result= productMapper.updateByPrimaryKeySelective(product);
                if(result == 0){
                    data.put("code",0);
                    data.put("msg","审批失败！");
                    logger.error("订单[审批]，结果=审批失败！");
                    return data;
                }else{
                    data.put("code",1);
                    data.put("msg","审批成功！");
                    logger.info("订单[审批]，结果=审批成功！");
                }
            }else{
                data.put("code",0);
                data.put("msg","审批失败,端口库存不足");
                logger.info("订单[审批]，结果=审批失败！");
            }

        }catch (Exception e) {
            e.printStackTrace();
            logger.error("订单[审批]异常！", e);
            return data;
        }
        return data;
    }
}
