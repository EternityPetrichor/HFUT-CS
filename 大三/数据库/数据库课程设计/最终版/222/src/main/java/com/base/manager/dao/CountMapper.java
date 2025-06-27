package com.base.manager.dao;

import com.base.manager.dto.UserCountDTO;
import com.base.manager.pojo.*;
import org.springframework.stereotype.Repository;
import tk.mapper.MyMapper;

import java.util.List;

@Repository
public interface CountMapper extends MyMapper<Product> {

    List<UserCount> getAllUser(UserCountDTO userCountDTO);

    List<UserCount> getProduct(UserCountDTO userCountDTO);

    List<UserCount> getOrder(UserCountDTO userCountDTO);

    List<UserCount> getMoney(UserCountDTO userCountDTO);

    List<Count> getSaleOrder();

    List<Bing> getSaleBing();

    List<Bing> getPfsBing();

    List<SaleLine> getSaleLine();
}