package com.furniture;

import com.furniture.entity.*;
import com.furniture.mapper.*;
import com.furniture.utils.MybatisUtil;
import lombok.extern.java.Log;
import org.apache.ibatis.session.SqlSession;

import java.sql.Date;
import java.util.Scanner;

@Log
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean shouldContinue = true;

        while (shouldContinue) {
            displayMenu();
            int input = Integer.parseInt(scanner.nextLine());
            shouldContinue = processInput(input, scanner);
        }
    }

    private static void displayMenu() {
        System.out.println("========================");
        System.out.println("1. 管理员");
        System.out.println("2. 供货商");
        System.out.println("3. 买家");
        System.out.println("4. 退出系统");
        System.out.println("========================");
        System.out.println("请选择身份：");
    }

    private static boolean processInput(int input, Scanner scanner) {
        switch (input) {
            case 1:
                ManagerFunction(scanner);
                System.out.println("hello");
                return true;
            case 2:
                System.out.println("woo");
                return true;
            case 3:
                CustomerFunction(scanner);
                return true;
            case 4:
                System.out.println("已退出！");
                return false;
            default:
                System.out.println("请重新输入！");
                return true;
        }
    }

    private static void ManagerFunction(Scanner scanner){
        while (true){
            System.out.println("========================");
            System.out.println("1.添加家具种类");
            System.out.println("2.添加家具");
            System.out.println("3.查看全部家具信息");
            System.out.println("4.家具入库");
            System.out.println("5.返回上一界面");
            System.out.println("========================");
            System.out.print("请选择功能：");
            int input = Integer.parseInt(scanner.nextLine());
            switch (input){
                case 1:
                    try(SqlSession session = MybatisUtil.getSession()) {
                        FurnitureTypeMapper mapper = session.getMapper(FurnitureTypeMapper.class);
                        System.out.print("请输入种类ID:");
                        int id = Integer.parseInt(scanner.nextLine());
                        System.out.print("请输入种类名字:");
                        String name = scanner.nextLine();
                        mapper.addFurnitureType(new FurnitureType(id, name));
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("请重新输入！");
            }
        }

    }

    private static void CustomerFunction(Scanner scanner){

        while (true){
            System.out.println("========================");
            System.out.println("1.注册顾客信息");
            System.out.println("2.查看全部家具信息");
            System.out.println("3.搜索目标家具");
            System.out.println("4.购买家具");
            System.out.println("5.返回上一界面");
            System.out.println("========================");
            System.out.print("请选择功能：");
            int input = Integer.parseInt(scanner.nextLine());
            switch (input){
                case 1:
                    try(SqlSession session = MybatisUtil.getSession()) {
                        CustomerMapper mapper = session.getMapper(CustomerMapper.class);
                        int id = mapper.countCustomer() + 1;
                        System.out.print("请输入姓名:");
                        String name = scanner.nextLine();
                        System.out.print("请输入联系方式:");
                        String info = scanner.nextLine();
                        System.out.print("请输入地址:");
                        String addr = scanner.nextLine();
                        mapper.addCustomer(new Customer(id, name, info, addr));
                    }
                    break;
                case 2:
                    try(SqlSession session = MybatisUtil.getSession()) {
                        FurnitureMapper mapper = session.getMapper(FurnitureMapper.class);
                        mapper.findAll().forEach(System.out::println);
                    }
                    break;
                case 3:
                    try(SqlSession session = MybatisUtil.getSession()) {
                        FurnitureMapper mapper = session.getMapper(FurnitureMapper.class);
                        System.out.print("请输入家具ID：");
                        int id = Integer.parseInt(scanner.nextLine());
                        try {
                            if (mapper.findById(id).getFurnitureId()!=0)
                                System.out.println(mapper.findById(id));
                        }
                        catch (NullPointerException e){
                            System.out.println("没有此家具！");
                        }

                    }
                    break;
                case 4:
                    try(SqlSession session = MybatisUtil.getSession()) {
                        SaleMapper mapper = session.getMapper(SaleMapper.class);
                        FurnitureMapper mapper2 = session.getMapper(FurnitureMapper.class);
                        int key = mapper.countSale() + 1;
                        System.out.print("请输入要购买家具的ID:");
                        int fid = Integer.parseInt(scanner.nextLine());
                        System.out.print("请输入用户的ID:");
                        int cid = Integer.parseInt(scanner.nextLine());
                        System.out.print("请输入要购买家具的数量:");
                        int quantity = Integer.parseInt(scanner.nextLine());
                        Date currentDate = new Date(System.currentTimeMillis());
                        mapper.addSale(new Sale(key, fid, cid, currentDate, quantity));
                        System.out.println("请等待处理...........................");
                        System.out.print("请输入支付方式:(支付宝/微信)");
                        String method = scanner.nextLine();
                        PaymentMapper mapper1 = session.getMapper(PaymentMapper.class);
                        mapper1.addPayment(new Payment(key, key, currentDate, quantity*mapper2.findById(fid).getSalePrice(), method));
                        System.out.println("购买成功！");
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("请重新输入！");
            }
        }
    }
}
