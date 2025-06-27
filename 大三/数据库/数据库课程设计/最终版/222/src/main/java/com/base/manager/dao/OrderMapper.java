package com.base.manager.dao;

import com.base.manager.dto.OrderSearchDTO;
import com.base.manager.pojo.Order;
import java.util.List;
import org.springframework.stereotype.Repository;
import tk.mapper.MyMapper;

@Repository
public interface OrderMapper extends MyMapper<Order> {
    List<OrderSearchDTO> getOrderList(OrderSearchDTO orderSearchDTO);
}