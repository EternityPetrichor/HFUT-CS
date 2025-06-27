package com.furniture.mapper;

import com.furniture.entity.FurnitureType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface FurnitureTypeMapper {
    @Select("select * from furniture_types")
    List<FurnitureType> getAllFurnitureTypes();
    @Select("select * from furniture_types where type_id = #{tid}")
    FurnitureType getFurnitureTypeById(int tid);
    @Insert("insert into furniture_types(type_name, description) values (#{name}, #{description})")
    void addFurnitureType(FurnitureType furnitureType);

    @Update("update furniture_types set type_name = #{name}, description = #{description} where type_id = #{tid}")
    void updateFurnitureType(FurnitureType furnitureType);
    @Delete("delete from furniture_types where type_id = #{tid}")
    void deleteFurnitureType(int tid);
    @Select("select count(*) from furniture_types")
    int countFurnitureTypes();

}
