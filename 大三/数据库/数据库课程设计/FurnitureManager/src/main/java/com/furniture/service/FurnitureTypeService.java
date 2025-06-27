package com.furniture.service;

import com.furniture.entity.FurnitureType;

import java.util.List;

public interface FurnitureTypeService {
    FurnitureType getFurnitureTypeById(int tid);
    List<FurnitureType> getAllFurnitureTypes();
    void addFurnitureType(FurnitureType furnitureType);
    void updateFurnitureType(FurnitureType furnitureType);
    void deleteFurnitureType(int tid);
    int countFurnitureTypes();
}
