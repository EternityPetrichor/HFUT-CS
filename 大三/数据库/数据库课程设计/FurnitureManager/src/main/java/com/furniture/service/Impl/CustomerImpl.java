package com.furniture.service.Impl;

import com.furniture.entity.Customer;
import com.furniture.mapper.CustomerMapper;
import com.furniture.service.CustomerService;
import com.furniture.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class CustomerImpl implements CustomerService {

    @Override
    public Customer getCustomerById(int customerId) {
        try(SqlSession session = MybatisUtil.getSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.getCustomerById(customerId);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        try(SqlSession session = MybatisUtil.getSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            return mapper.getAllCustomers();
        }
    }

    @Override
    public void addCustomer(Customer customer) {
        try(SqlSession session = MybatisUtil.getSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            mapper.addCustomer(customer);
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        try(SqlSession session = MybatisUtil.getSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            mapper.updateCustomer(customer);
        }
    }

    @Override
    public void deleteCustomer(int customerId) {
        try(SqlSession session = MybatisUtil.getSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            mapper.deleteCustomer(customerId);
        }
    }
}
