package com.furniture.mapper;

import com.furniture.entity.Customer;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface CustomerMapper {
    @Select("select * from customers where customer_id = #{customerId}")
    Customer getCustomerById(int customerId);

    @Select("select * from customers")
    List<Customer> getAllCustomers();

    @Insert("insert into customers(customer_id, customer_name, contact_info, address) VALUES (#{customerId}, #{customerName}, #{contactInfo}, #{address})")
    void addCustomer(Customer customer);

    @Update("update customers set customer_name = #{customerName}, contact_info = #{contactInfo}, address = #{address} where customer_id = #{customerId}")
    void updateCustomer(Customer customer);

    @Delete("delete from customers where customer_id = #{customerId}")
    void deleteCustomer(int customerId);
    @Select("select COUNT(*) from customers")
    int countCustomer();
}
