package com.furniture.service.Impl;

import com.furniture.entity.Furniture;
import com.furniture.mapper.FurnitureMapper;
import com.furniture.service.FurnitureService;
import com.furniture.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class FurnitureImpl implements FurnitureService {
    @Override
    public Furniture findById(int furnitureId) {
        try(SqlSession session = MybatisUtil.getSession()) {
            FurnitureMapper mapper = session.getMapper(FurnitureMapper.class);
            return mapper.findById(furnitureId);
        }
    }

    @Override
    public List<Furniture> findAll() {
        try(SqlSession session = MybatisUtil.getSession()) {
            FurnitureMapper mapper = session.getMapper(FurnitureMapper.class);
            return mapper.findAll();
        }
    }

    @Override
    public void addFurniture(Furniture furniture) {
        try(SqlSession session = MybatisUtil.getSession()) {
            FurnitureMapper mapper = session.getMapper(FurnitureMapper.class);
            mapper.addFurniture(furniture);
        }
    }

    @Override
    public void updateFurniture(int furnitureId, Furniture furniture) {
        try(SqlSession session = MybatisUtil.getSession()) {
            FurnitureMapper mapper = session.getMapper(FurnitureMapper.class);
            mapper.updateFurniture(furnitureId, furniture);
        }
    }

    @Override
    public void deleteFurniture(int furnitureId) {
        try(SqlSession session = MybatisUtil.getSession()) {
            FurnitureMapper mapper = session.getMapper(FurnitureMapper.class);
            mapper.deleteFurniture(furnitureId);
        }
    }
}
