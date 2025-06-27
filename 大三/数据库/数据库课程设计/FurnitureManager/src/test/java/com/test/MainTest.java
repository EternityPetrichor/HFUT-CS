package com.test;

import com.furniture.entity.*;
import com.furniture.mapper.*;
import com.furniture.service.Impl.FurnitureTypeImpl;
import com.furniture.utils.MybatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;

import java.sql.Date;


public class MainTest {
    @Test
    public void test1(){
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            FurnitureTypeMapper mapper = sqlSession.getMapper(FurnitureTypeMapper.class);
            //mapper.getAllFurnitureTypes().forEach(System.out::println);
            mapper.addFurnitureType(new FurnitureType(6, "沙发"));
        }
    }
    @Test
    public void test2(){
        FurnitureTypeImpl furnitureType = new FurnitureTypeImpl();
        furnitureType.addFurnitureType(new FurnitureType(7, "沙发"));
    }

    @Test
    public void test3(){
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            SupplierMapper  mapper = sqlSession.getMapper(SupplierMapper.class);
            //mapper.addSupplier(new Supplier("北京家具有限公司", "010-12345678", "北京市朝阳区XXX路123号"));
            //mapper.addSupplier(new Supplier("上海装饰用品有限公司", "021-87654321", "上海市浦东新区YYY路456号"));
            //mapper.addSupplier(new Supplier("广州建材有限公司", "020-55556666", "广州市天河区ZZZ街789号"));
            //mapper.findAllSuppliers().forEach(System.out::println);
            System.out.println(mapper.findSuppliersById(1));
        }
    }

    @Test
    public void test4(){
        try(SqlSession sqlSession = MybatisUtil.getSession()) {
            FurnitureMapper mapper = sqlSession.getMapper(FurnitureMapper.class);
            //mapper.addFurniture(new Furniture("木质餐桌", 1, 1, 300.00, 500.00, 50));
            //mapper.updateFurniture(1, new Furniture("木质餐桌", 1, 1, 300.00, 500.00, 50));
            System.out.println(mapper.findById(1));
            //mapper.findAll().forEach(System.out::println);
        }
    }

    @Test
    public void test5(){
        try(SqlSession session = MybatisUtil.getSession()) {
            CustomerMapper mapper = session.getMapper(CustomerMapper.class);
            mapper.updateCustomer(new Customer(3,"Michael Johnson", "555-555-5555", "789 Oak Avenue, Any city"));
        }
    }
    @Test
    public void test6(){
        try(SqlSession session = MybatisUtil.getSession()) {
            /*Date currentDate = new Date(System.currentTimeMillis());
            FurnitureMapper mapper = session.getMapper(FurnitureMapper.class);
            SupplierMapper mapper1 = session.getMapper(SupplierMapper.class);
            mapper.addFurniture(new Furniture(3, "写字床", 3,3,120, 150, 10));
            Furniture furniture = mapper.findById(3);
            Supplier supplier = mapper1.findSuppliersById(furniture.getSupplierId());
            InboundMapper mapper2 = session.getMapper(InboundMapper.class);
            int index  = mapper2.countInbound()+1;
            mapper2.addInbound(new Inbound(index, furniture, supplier, currentDate, 5));*/
            InboundMapper mapper = session.getMapper(InboundMapper.class);
            //System.out.println(mapper.findInboundsByFurnitureName("单人床"));
            mapper.findInboundsBySupplierName("北京家具有限公司").forEach(System.out::println);
        }

        //System.out.println("Current Date and Time: " + currentDate);
    }
    @Test
    public void test7(){
        try(SqlSession session = MybatisUtil.getSession()) {
            SaleMapper mapper = session.getMapper(SaleMapper.class);
            Date currentDate = new Date(System.currentTimeMillis());
            int key = mapper.countSale()+1;
            //mapper.addSale(new Sale(key, 2,2,currentDate,4));
            //mapper.findSalesByFurnitureName("单人床").forEach(System.out::println);
            //System.out.println(mapper.findAmountById(1));
            String payMethod = "支付宝";
            mapper.addSale(new Sale(key, 3, 3, currentDate, 6));
            PaymentMapper mapper1 = session.getMapper(PaymentMapper.class);
            mapper1.addPayment(new Payment(key, key, currentDate, mapper.findAmountById(key), payMethod));
        }
    }

}
