package com.furniture.service.Impl;

import com.furniture.entity.Supplier;
import com.furniture.mapper.SupplierMapper;
import com.furniture.service.SupplierService;
import com.furniture.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class SupplierImpl implements SupplierService {
    @Override
    public Supplier findSuppliersById(int id) {
        try(SqlSession session = MybatisUtil.getSession()) {
            SupplierMapper mapper = session.getMapper(SupplierMapper.class);
            return mapper.findSuppliersById(id);
        }
    }

    @Override
    public List<Supplier> findAllSuppliers() {
        try(SqlSession session = MybatisUtil.getSession()) {
            SupplierMapper mapper = session.getMapper(SupplierMapper.class);
            return mapper.findAllSuppliers();
        }
    }

    @Override
    public void addSupplier(Supplier supplier) {
        try(SqlSession session = MybatisUtil.getSession()) {
            SupplierMapper mapper = session.getMapper(SupplierMapper.class);
            List<Supplier> list = mapper.findAllSuppliers();
            int i = 0;
            while (list.iterator().hasNext()){
                if (list.get(i).equals(supplier)){
                    System.out.println("添加失败，已存在改供应商信息！");
                    return;
                }
                i ++;
            }
            mapper.addSupplier(supplier);
        }
    }

    @Override
    public void updateSupplier(Supplier supplier) {
        try(SqlSession session = MybatisUtil.getSession()) {
            SupplierMapper mapper = session.getMapper(SupplierMapper.class);
            mapper.updateSupplier(supplier);
        }
    }

    @Override
    public void delete(int id) {
        try(SqlSession session = MybatisUtil.getSession()) {
            SupplierMapper mapper = session.getMapper(SupplierMapper.class);
            mapper.delete(id);
        }
    }
}
