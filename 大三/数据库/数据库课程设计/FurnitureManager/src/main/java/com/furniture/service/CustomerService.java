package com.furniture.service;

import com.furniture.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer getCustomerById(int customerId);
    List<Customer> getAllCustomers();
    void addCustomer(Customer customer);
    void updateCustomer(Customer customer);
    void deleteCustomer(int customerId);
}
