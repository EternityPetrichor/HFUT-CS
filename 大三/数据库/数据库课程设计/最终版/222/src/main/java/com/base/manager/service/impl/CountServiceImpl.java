package com.base.manager.service.impl;

import com.base.manager.common.utils.DateUtils;
import com.base.manager.dao.CountMapper;
import com.base.manager.dto.UserCountDTO;
import com.base.manager.pojo.*;
import com.base.manager.service.CountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountServiceImpl implements CountService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CountMapper countMapper;
    @Override
    public Map<String, Object> getAllUser(UserCountDTO userCountDTO) {
        Map<String,Object> data = new HashMap();
        //查询总用户数
        List<UserCount> allUser = countMapper.getAllUser(userCountDTO);
        int allnum = allUser.get(0).getNum();
        data.put("allnum",allnum);
        //查询总产品数
        List<UserCount> allProduct = countMapper.getProduct(userCountDTO);
        int allproductnum = allProduct.get(0).getNum();
        data.put("allproductnum",allproductnum);
        //查询订单量
        List<UserCount> allOrder = countMapper.getOrder(userCountDTO);
        int allordernum = allOrder.get(0).getNum();
        data.put("allordernum",allordernum);
        //查询总销售额
        List<UserCount> allmoney = countMapper.getMoney(userCountDTO);
        int allmoneynum = allmoney.get(0).getNum();
        data.put("allmoneynum",allmoneynum);
        //查询当天用户数
        userCountDTO.setStartTime(DateUtils.getNowDateString());
        List<UserCount> newUser = countMapper.getAllUser(userCountDTO);
        int newnum = newUser.get(0).getNum();
        data.put("newnum",newnum);
        //查询当天总产品数
        List<UserCount> newProduct = countMapper.getProduct(userCountDTO);
        int newproductnum = allProduct.get(0).getNum();
        data.put("newproductnum",newproductnum);
        //查询当天总订单量
        List<UserCount> newOrder = countMapper.getOrder(userCountDTO);
        int newordernum = newOrder.get(0).getNum();
        data.put("newordernum",newordernum);
        //查询当天总销售额
        List<UserCount> newmoney = countMapper.getMoney(userCountDTO);
        int newmoneynum = newmoney.get(0).getNum();
        data.put("newmoneynum",newmoneynum);
        return data;
    }

    @Override
    public Map<String, Object> getSaleOrder() {
        Map<String,Object> data = new HashMap();
        List<Count> list = countMapper.getSaleOrder();
        String[] allprice = new String[list.size()];
        String[] orders = new String[list.size()];
        String[] pname = new String[list.size()];
        if(list.size()>0){
           for(int i=0;i<list.size();i++){
               allprice[i]=(list.get(i).getAllprice()).toString();
               orders[i]=(list.get(i).getOrders()).toString();
               pname[i]=(list.get(i).getPname());
           }
        }
        data.put("allprice",allprice);
        data.put("orders",orders);
        data.put("pname",pname);
        return data;
    }

    @Override
    public Map<String, Object> getSaleBing() {
        Map<String,Object> data = new HashMap();
        List<Bing> list = countMapper.getSaleBing();
        String[] pname = new String[list.size()];
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                pname[i]=(list.get(i).getName());
            }
        }
        data.put("order",list);
        data.put("pname",pname);
        return data;
    }

    @Override
    public Map<String, Object> getPfsBing() {
        Map<String,Object> data = new HashMap();
        List<Bing> list = countMapper.getPfsBing();
        String[] pname = new String[list.size()];
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                pname[i]=(list.get(i).getName());
            }
        }
        data.put("order",list);
        data.put("pname",pname);
        return data;
    }

    @Override
    public Map<String, Object> getSaleLine() {
        Map<String,Object> data = new HashMap();
        List<SaleLine> list = countMapper.getSaleLine();
        String[] ordertime = new String[list.size()];
        String[] allprice = new String[list.size()];
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                ordertime[i]=(list.get(i).getOrdertime());
                allprice[i]=((list.get(i).getAllprice()).toString());
            }
        }
        data.put("allprice",allprice);
        data.put("ordertime",ordertime);
        return data;
    }
}
