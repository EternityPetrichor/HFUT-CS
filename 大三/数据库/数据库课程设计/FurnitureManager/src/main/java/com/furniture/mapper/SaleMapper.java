package com.furniture.mapper;

import com.furniture.entity.Sale;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SaleMapper {
    // 插入销售记录
    @Insert("INSERT INTO furniture_sales (furniture_id, customer_id, sale_date, quantity) " +
            "VALUES (#{sale.furnitureId}, #{sale.customerId}, #{sale.saleDate}, #{sale.quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "sale.saleId")
    void addSale(@Param("sale") Sale sale);

    // 根据家具名称查询销售记录
    @Select("SELECT s.sale_id, s.furniture_id, s.customer_id, s.sale_date, s.quantity, s.total_amount " +
            "FROM furniture_sales s " +
            "JOIN furniture f ON s.furniture_id = f.furniture_id " +
            "WHERE f.furniture_name = #{furnitureName}")
    @Results({
            @Result(property = "saleId", column = "sale_id"),
            @Result(property = "furnitureId", column = "furniture_id"),
            @Result(property = "customerId", column = "customer_id"),
            @Result(property = "saleDate", column = "sale_date"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "totalAmount", column = "total_amount")
    })
    List<Sale> findSalesByFurnitureName(@Param("furnitureName") String furnitureName);
    @Select("select COUNT(*) from furniture_sales")
    int countSale();
    @Select("select total_amount from furniture_sales where sale_id = #{id}")
    double findAmountById(int id);
}
