package com.base.manager.service;

import com.base.manager.dto.OrderSearchDTO;
import com.base.manager.dto.UserCountDTO;
import com.base.manager.pojo.Order;
import com.base.manager.response.PageDataResult;

import java.util.Map;

public interface CountService {
    Map<String, Object> getAllUser(UserCountDTO userCountDTO);

    Map<String, Object> getSaleOrder();

    Map<String, Object> getSaleBing();

    Map<String, Object> getSaleLine();

    Map<String, Object> getPfsBing();
}

