package com.furniture.service.Impl;

import com.furniture.entity.FurnitureType;
import com.furniture.mapper.FurnitureTypeMapper;
import com.furniture.service.FurnitureTypeService;
import com.furniture.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class FurnitureTypeImpl implements FurnitureTypeService {
    @Override
    public FurnitureType getFurnitureTypeById(int tid) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            FurnitureTypeMapper mapper = sqlSession.getMapper(FurnitureTypeMapper.class);
            return mapper.getFurnitureTypeById(tid);
        }
    }

    @Override
    public List<FurnitureType> getAllFurnitureTypes() {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            FurnitureTypeMapper mapper = sqlSession.getMapper(FurnitureTypeMapper.class);
            return mapper.getAllFurnitureTypes();
        }
    }

    @Override
    public void addFurnitureType(FurnitureType furnitureType) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            FurnitureTypeMapper mapper = sqlSession.getMapper(FurnitureTypeMapper.class);
            String targetName = furnitureType.getName();
            /*for (int i = 0; i < mapper.countFurnitureTypes(); i++) {
                if (targetName.equals(mapper.getFurnitureTypeById(i).getName())){
                    System.out.println("该种类已经存在");
                    return;
                }
            }*/
            List<FurnitureType> list = mapper.getAllFurnitureTypes();
            int i = 0;
            while (list.iterator().hasNext()){
                if (list.get(i).getName().equals(targetName)) {
                    System.out.println("该种类已经存在，请检查信息重新录入!");
                    return;
                }
                i++;
            }
            mapper.addFurnitureType(furnitureType);
        }
    }

    @Override
    public void updateFurnitureType(FurnitureType furnitureType) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            FurnitureTypeMapper mapper = sqlSession.getMapper(FurnitureTypeMapper.class);
            mapper.updateFurnitureType(furnitureType);
        }
    }

    @Override
    public void deleteFurnitureType(int tid) {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            FurnitureTypeMapper mapper = sqlSession.getMapper(FurnitureTypeMapper.class);
            mapper.deleteFurnitureType(tid);
        }
    }

    @Override
    public int countFurnitureTypes() {
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            FurnitureTypeMapper mapper = sqlSession.getMapper(FurnitureTypeMapper.class);
            return mapper.countFurnitureTypes();
        }
    }
}
