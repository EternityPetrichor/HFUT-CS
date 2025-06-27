package com.furniture.mapper;

import com.furniture.entity.Furniture;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FurnitureMapper {
    @Select("select * from furniture where furniture_id = #{furnitureId}")
    /*@Results({
            @Result(property = "furnitureId", column = "furniture_id"),
            @Result(property = "furnitureName", column = "furniture_name"),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "supplierId", column = "supplier_id"),
            @Result(property = "purchasePrice", column = "purchase_price"),
            @Result(property = "salePrice", column = "sale_price"),
            @Result(property = "stockQuantity", column = "stock_quantity")
    })*/
    Furniture findById(int furnitureId);

    @Select("select * from furniture")
    @Results({
            @Result(property = "furnitureId", column = "furniture_id"),
            @Result(property = "furnitureName", column = "furniture_name"),
            @Result(property = "typeId", column = "type_id"),
            @Result(property = "supplierId", column = "supplier_id"),
            @Result(property = "purchasePrice", column = "purchase_price"),
            @Result(property = "salePrice", column = "sale_price"),
            @Result(property = "stockQuantity", column = "stock_quantity")
    })
    List<Furniture> findAll();

    @Insert("insert into furniture(furniture_name, type_id, supplier_id, purchase_price, sale_price, stock_quantity) VALUES (#{furnitureName}, #{typeId}, #{supplierId}, #{purchasePrice}, #{salePrice}, #{stockQuantity})")
    void addFurniture(Furniture furniture);

    @Update("update furniture set furniture_name = #{furniture.furnitureName}, type_id = #{furniture.typeId}, supplier_id = #{furniture.supplierId}, purchase_price = #{furniture.purchasePrice}, sale_price = #{furniture.salePrice}, stock_quantity = #{furniture.stockQuantity} where furniture_id = #{furnitureId}")
    void updateFurniture(@Param("furnitureId") int furnitureId, @Param("furniture") Furniture furniture);

    @Delete("delete from furniture where furniture_id = #{furnitureId}")
    void deleteFurniture(int furnitureId);
}
