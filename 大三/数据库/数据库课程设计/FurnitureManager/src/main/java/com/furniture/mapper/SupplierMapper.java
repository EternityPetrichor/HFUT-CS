package com.furniture.mapper;

import com.furniture.entity.Supplier;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface SupplierMapper {
    @Select("select * from suppliers where supplier_id = #{id}")
    Supplier findSuppliersById(int id);

    @Select("select * from suppliers")
    List<Supplier> findAllSuppliers();

    @Insert("insert into suppliers(supplier_name, contact_info, address) values (#{name}, #{info}, #{addr})")
    void addSupplier(Supplier supplier);

    @Update("update suppliers set supplier_name = #{name}, contact_info = #{info}, address = #{addr} where supplier_id = #{id}")
    void updateSupplier(Supplier supplier);

    @Delete("delete from suppliers where supplier_id = #{id}")
    void delete(int id);
}
