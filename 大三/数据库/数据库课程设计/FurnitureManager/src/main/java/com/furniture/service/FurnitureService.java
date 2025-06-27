package com.furniture.service;

import com.furniture.entity.Furniture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FurnitureService {
    Furniture findById(int furnitureId);
    List<Furniture> findAll();
    void addFurniture(Furniture furniture);
    void updateFurniture(int furnitureId, Furniture furniture);
    void deleteFurniture(int furnitureId);
}
