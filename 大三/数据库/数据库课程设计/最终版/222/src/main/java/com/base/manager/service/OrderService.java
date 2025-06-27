package com.base.manager.service;

import com.base.manager.dto.OrderSearchDTO;
import com.base.manager.dto.ProductDTO;
import com.base.manager.pojo.Order;
import com.base.manager.pojo.Product;
import com.base.manager.response.PageDataResult;

import java.util.Map;

public interface OrderService {
    PageDataResult getOrderList(OrderSearchDTO orderSearchDTO, Integer pageNum, Integer pageSize);

    Map<String,Object> addOrders(Order order);

    Map<String,Object> delOrder(Integer id);

    Map<String,Object> updateOrder(Order order);
}

