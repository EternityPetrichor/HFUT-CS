package com.furniture.mapper;

import com.furniture.entity.Furniture;
import com.furniture.entity.Inbound;
import com.furniture.entity.Supplier;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

public interface InboundMapper {
    @Insert("insert into furniture_inbound(furniture_id, supplier_id, inbound_date, quantity) VALUES (#{inbound.furniture.furnitureId}, #{inbound.supplier.id}, #{inbound.inboundDate}, #{inbound.quantity})")
    void addInbound(@Param("inbound") Inbound inbound);
    //@Select("SELECT i.*, f.*, s.* FROM furniture_inbound i JOIN furniture f ON i.furniture_id = f.furniture_id JOIN suppliers s ON i.supplier_id = s.supplier_id WHERE f.furniture_name = #{furnitureName}")
    /*@Select("SELECT i.inbound_id, i.inbound_date, i.quantity, " +
            "i.furniture_id AS furniture_id, " +
            "i.supplier_id AS supplier_id, " +
            "f.furniture_name AS furniture_name, " +
            "f.type_id AS furniture_type_id, " +
            "s.supplier_name AS supplier_name, " +
            "s.contact_info AS supplier_contact_info, " +
            "s.address AS supplier_address " +
            "FROM furniture_inbound i " +
            "JOIN furniture f ON i.furniture_id = f.furniture_id " +
            "JOIN suppliers s ON i.supplier_id = s.supplier_id " +
            "WHERE f.furniture_name = #{furnitureName}")
    @Results({
            @Result(property = "inboundId", column = "inbound_id"),
            @Result(property = "inboundDate", column = "inbound_date"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "furniture", column = "furniture_id",
                    javaType = Furniture.class,
                    one = @One(select = "com.furniture.mapper.FurnitureMapper.findById")),
            @Result(property = "supplier", column = "supplier_id",
                    javaType = Supplier.class,
                    one = @One(select = "com.furniture.mapper.SupplierMapper.findSuppliersById"))
    })*/
    @Select("SELECT i.*, f.*, s.* FROM furniture_inbound i JOIN furniture f ON i.furniture_id = f.furniture_id JOIN suppliers s ON i.supplier_id = s.supplier_id WHERE f.furniture_name = #{furnitureName}")
    List<Inbound> findInboundsByFurnitureName(@Param("furnitureName") String furnitureName);
    @Select("SELECT i.*, f.*, s.* FROM furniture_inbound i JOIN furniture f ON i.furniture_id = f.furniture_id JOIN suppliers s ON i.supplier_id = s.supplier_id WHERE s.supplier_name = #{furnitureName}")
    List<Inbound> findInboundsBySupplierName(String supplierName);

    @Select("select COUNT(*) from furniture_inbound")
    int countInbound();
}
/*@Results({
            @Result(property = "inboundId", column = "inbound_id"),
            @Result(property = "inboundDate", column = "inbound_date"),
            @Result(property = "quantity", column = "quantity"),
            // Mapping nested objects (Furniture and Supplier)
            @Result(property = "furniture.furnitureId", column = "furniture_id"),
            @Result(property = "furniture.furnitureName", column = "furniture_name"),
            @Result(property = "furniture.typeId", column = "type_id"),
            @Result(property = "furniture.supplierId", column = "supplier_id"),
            @Result(property = "furniture.purchasePrice", column = "purchase_price"),
            @Result(property = "furniture.salePrice", column = "sale_price"),
            @Result(property = "furniture.stockQuantity", column = "stock_quantity"),
            @Result(property = "supplier.id", column = "supplier_id"),
            @Result(property = "supplier.name", column = "supplier_name"),
            @Result(property = "supplier.info", column = "contact_info"),
            @Result(property = "supplier.addr", column = "address")
    })*/
