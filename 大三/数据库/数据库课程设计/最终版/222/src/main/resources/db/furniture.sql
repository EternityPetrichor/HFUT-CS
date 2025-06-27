/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80021
Source Host           : localhost:3306
Source Database       : sales analysis

Target Server Type    : MYSQL
Target Server Version : 80021
File Encoding         : 65001

Date: 2021-07-06 12:05:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `base_admin_permission`
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_permission`;
CREATE TABLE `base_admin_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单名称',
  `pid` int DEFAULT NULL COMMENT '父菜单id',
  `descpt` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '描述',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '菜单url',
  `create_time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '添加时间',
  `update_time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新时间',
  `del_flag` int DEFAULT NULL COMMENT '删除标志（0:删除 1：存在）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of base_admin_permission
-- ----------------------------
INSERT INTO `base_admin_permission` VALUES ('1', '登记出库', '0', '登记出库', '/product/productManage', '2024-06-02 16:44:22', '2024-07-06 09:53:27', '1');
INSERT INTO `base_admin_permission` VALUES ('2', '首页', '0', '管理员页面', '/home', '2024-06-03 09:54:03', '2024-06-03 09:54:03', '1');
INSERT INTO `base_admin_permission` VALUES ('3', '出库管理', '0', '出库管理', '/order/orderManage', '2024-06-02 16:47:32', '2024-07-06 10:45:12', '1');
INSERT INTO `base_admin_permission` VALUES ('17', '系统管理', '0', '系统管理', '', '2024-06-04 22:50:35', '2024-06-04 22:50:35', '1');
INSERT INTO `base_admin_permission` VALUES ('18', '账号管理', '17', '账号管理', '/user/userManage', '2024-06-04 22:50:35', '2024-06-04 22:50:35', '1');
INSERT INTO `base_admin_permission` VALUES ('19', '角色管理', '17', '角色管理', '/role/roleManage', '2024-06-04 22:50:35', '2024-06-04 22:50:35', '1');
INSERT INTO `base_admin_permission` VALUES ('20', '权限管理', '17', '权限管理', '/permission/permissionManage', '2024-06-04 22:50:35', '2024-06-04 22:50:35', '1');
INSERT INTO `base_admin_permission` VALUES ('22', '入库管理', '0', '入库管理', '/product/productManage', '2024-06-04 22:46:02', '2024-07-06 09:58:49', '1');
INSERT INTO `base_admin_permission` VALUES ('23', '统计分析', '0', '统计分析', '/count/countManage', '2024-06-04 22:50:21', '2024-06-05 23:23:20', '1');

-- ----------------------------
-- Table structure for `base_admin_role`
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_role`;
CREATE TABLE `base_admin_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '权限角色ID',
  `role_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `role_desc` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '角色描述',
  `permissions` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '权限',
  `create_time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '创建时间',
  `update_time` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '更新时间',
  `role_status` int NOT NULL DEFAULT '1' COMMENT '1：有效 \r\n            0：无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户角色表';

-- ----------------------------
-- Records of base_admin_role
-- ----------------------------
INSERT INTO `base_admin_role` VALUES ('1', '系统管理员', '系统管理员', '2,3,22,23,17', '2024-06-04 22:50:35', '2024-06-04 22:50:35', '1');
INSERT INTO `base_admin_role` VALUES ('2', '仓库管理员', '仓库管理员', '2,3,22,23,17', '2024-06-04 22:50:35', '2024-07-06 10:01:53', '1');
INSERT INTO `base_admin_role` VALUES ('4', '销售员', '销售员', '1,3', '2024-06-01 16:01:24', '2024-07-06 10:02:31', '1');

-- ----------------------------
-- Table structure for `base_admin_user`
-- ----------------------------
DROP TABLE IF EXISTS `base_admin_user`;
CREATE TABLE `base_admin_user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `sys_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '系统用户名称',
  `sys_user_pwd` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '系统用户密码',
  `role_id` int DEFAULT NULL COMMENT '角色',
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '手机号',
  `reg_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登记时间',
  `user_status` int NOT NULL DEFAULT '0' COMMENT '状态（0：无效；1：有效）',
  `email` varchar(30) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统管理员帐号';

-- ----------------------------
-- Records of base_admin_user
-- ----------------------------
 INSERT INTO `base_admin_user` VALUES ('1', 'admin', '88212f91e2e9cf36981a91b6c518af5c', '1', '13411182215', '2024-06-01 16:44:36', '1', null, null);
-- INSERT INTO `base_admin_user` VALUES ('1', 'admin', '123456', '1', '13411182215', '2021-06-01 16:44:36', '1', null, null);
INSERT INTO `base_admin_user` VALUES ('21', 'sale3', 'cfe456d4ff79e6340b9077202f1e1ca9', '4', '13726892223', '2024-06-01 16:44:36', '1', '137', 'no');
INSERT INTO `base_admin_user` VALUES ('24', 'sale', 'adb7209f848df5b2a067a15d46cc0615', '4', '13726892278', '2024-06-04 09:11:53', '1', '12345678@.com', '宣城');
INSERT INTO `base_admin_user` VALUES ('26', 'sale2', '3298adc097224d62f3f806bf1045eb06', '4', '13726892278', '2024-06-05 12:54:58', '1', null, null);
INSERT INTO `base_admin_user` VALUES ('35', 'sale1', '07722ee4e130cfa0f9177026d950949e', '4', '12345678912', '2024-06-06 11:16:17', '1', '123', '123');

-- ----------------------------
-- Table structure for `base_month`
-- ----------------------------
DROP TABLE IF EXISTS `base_month`;
CREATE TABLE `base_month` (
  `date` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of base_month
-- ----------------------------
INSERT INTO `base_month` VALUES ('2018-01');
INSERT INTO `base_month` VALUES ('2018-02');
INSERT INTO `base_month` VALUES ('2018-03');
INSERT INTO `base_month` VALUES ('2018-04');
INSERT INTO `base_month` VALUES ('2018-05');
INSERT INTO `base_month` VALUES ('2018-06');
INSERT INTO `base_month` VALUES ('2018-07');
INSERT INTO `base_month` VALUES ('2018-08');
INSERT INTO `base_month` VALUES ('2018-09');
INSERT INTO `base_month` VALUES ('2018-10');
INSERT INTO `base_month` VALUES ('2018-11');
INSERT INTO `base_month` VALUES ('2018-12');
INSERT INTO `base_month` VALUES ('2019-01');
INSERT INTO `base_month` VALUES ('2019-02');
INSERT INTO `base_month` VALUES ('2019-03');
INSERT INTO `base_month` VALUES ('2019-04');
INSERT INTO `base_month` VALUES ('2019-05');
INSERT INTO `base_month` VALUES ('2019-06');
INSERT INTO `base_month` VALUES ('2019-07');
INSERT INTO `base_month` VALUES ('2019-08');
INSERT INTO `base_month` VALUES ('2019-09');
INSERT INTO `base_month` VALUES ('2019-10');
INSERT INTO `base_month` VALUES ('2019-11');
INSERT INTO `base_month` VALUES ('2019-12');
INSERT INTO `base_month` VALUES ('2020-01');
INSERT INTO `base_month` VALUES ('2020-02');
INSERT INTO `base_month` VALUES ('2020-03');
INSERT INTO `base_month` VALUES ('2020-04');
INSERT INTO `base_month` VALUES ('2020-05');
INSERT INTO `base_month` VALUES ('2020-06');
INSERT INTO `base_month` VALUES ('2020-07');
INSERT INTO `base_month` VALUES ('2020-08');
INSERT INTO `base_month` VALUES ('2020-09');
INSERT INTO `base_month` VALUES ('2020-10');
INSERT INTO `base_month` VALUES ('2020-11');
INSERT INTO `base_month` VALUES ('2020-12');
INSERT INTO `base_month` VALUES ('2021-01');
INSERT INTO `base_month` VALUES ('2021-02');
INSERT INTO `base_month` VALUES ('2021-03');
INSERT INTO `base_month` VALUES ('2021-04');
INSERT INTO `base_month` VALUES ('2021-05');
INSERT INTO `base_month` VALUES ('2021-06');
INSERT INTO `base_month` VALUES ('2021-07');
INSERT INTO `base_month` VALUES ('2021-08');
INSERT INTO `base_month` VALUES ('2021-09');
INSERT INTO `base_month` VALUES ('2021-10');
INSERT INTO `base_month` VALUES ('2021-11');
INSERT INTO `base_month` VALUES ('2021-12');
INSERT INTO `base_month` VALUES ('2022-01');
INSERT INTO `base_month` VALUES ('2022-02');
INSERT INTO `base_month` VALUES ('2022-03');
INSERT INTO `base_month` VALUES ('2022-04');
INSERT INTO `base_month` VALUES ('2022-05');
INSERT INTO `base_month` VALUES ('2022-06');
INSERT INTO `base_month` VALUES ('2022-07');
INSERT INTO `base_month` VALUES ('2022-08');
INSERT INTO `base_month` VALUES ('2022-09');
INSERT INTO `base_month` VALUES ('2022-10');
INSERT INTO `base_month` VALUES ('2022-11');
INSERT INTO `base_month` VALUES ('2022-12');
INSERT INTO `base_month` VALUES ('2023-01');
INSERT INTO `base_month` VALUES ('2023-02');
INSERT INTO `base_month` VALUES ('2023-03');
INSERT INTO `base_month` VALUES ('2023-04');
INSERT INTO `base_month` VALUES ('2023-05');
INSERT INTO `base_month` VALUES ('2023-06');
INSERT INTO `base_month` VALUES ('2023-07');
INSERT INTO `base_month` VALUES ('2023-08');
INSERT INTO `base_month` VALUES ('2023-09');
INSERT INTO `base_month` VALUES ('2023-10');
INSERT INTO `base_month` VALUES ('2023-11');
INSERT INTO `base_month` VALUES ('2023-12');
INSERT INTO `base_month` VALUES ('2024-01');
INSERT INTO `base_month` VALUES ('2024-02');
INSERT INTO `base_month` VALUES ('2024-03');
INSERT INTO `base_month` VALUES ('2024-04');
INSERT INTO `base_month` VALUES ('2024-05');
INSERT INTO `base_month` VALUES ('2024-06');
INSERT INTO `base_month` VALUES ('2024-07');
INSERT INTO `base_month` VALUES ('2024-08');
INSERT INTO `base_month` VALUES ('2024-09');
INSERT INTO `base_month` VALUES ('2024-10');
INSERT INTO `base_month` VALUES ('2024-11');
INSERT INTO `base_month` VALUES ('2024-12');
INSERT INTO `base_month` VALUES ('2025-01');
INSERT INTO `base_month` VALUES ('2025-02');
INSERT INTO `base_month` VALUES ('2025-03');
INSERT INTO `base_month` VALUES ('2025-04');
INSERT INTO `base_month` VALUES ('2025-05');
INSERT INTO `base_month` VALUES ('2025-06');
INSERT INTO `base_month` VALUES ('2025-07');
INSERT INTO `base_month` VALUES ('2025-08');
INSERT INTO `base_month` VALUES ('2025-09');
INSERT INTO `base_month` VALUES ('2025-10');
INSERT INTO `base_month` VALUES ('2025-11');
INSERT INTO `base_month` VALUES ('2025-12');
INSERT INTO `base_month` VALUES ('2026-01');
INSERT INTO `base_month` VALUES ('2026-02');
INSERT INTO `base_month` VALUES ('2026-03');
INSERT INTO `base_month` VALUES ('2026-04');
INSERT INTO `base_month` VALUES ('2026-05');
INSERT INTO `base_month` VALUES ('2026-06');
INSERT INTO `base_month` VALUES ('2026-07');
INSERT INTO `base_month` VALUES ('2026-08');
INSERT INTO `base_month` VALUES ('2026-09');
INSERT INTO `base_month` VALUES ('2026-10');
INSERT INTO `base_month` VALUES ('2026-11');
INSERT INTO `base_month` VALUES ('2026-12');
INSERT INTO `base_month` VALUES ('2027-01');
INSERT INTO `base_month` VALUES ('2027-02');
INSERT INTO `base_month` VALUES ('2027-03');
INSERT INTO `base_month` VALUES ('2027-04');
INSERT INTO `base_month` VALUES ('2027-05');
INSERT INTO `base_month` VALUES ('2027-06');
INSERT INTO `base_month` VALUES ('2027-07');
INSERT INTO `base_month` VALUES ('2027-08');
INSERT INTO `base_month` VALUES ('2027-09');
INSERT INTO `base_month` VALUES ('2027-10');
INSERT INTO `base_month` VALUES ('2027-11');
INSERT INTO `base_month` VALUES ('2027-12');
INSERT INTO `base_month` VALUES ('2028-01');
INSERT INTO `base_month` VALUES ('2028-02');
INSERT INTO `base_month` VALUES ('2028-03');
INSERT INTO `base_month` VALUES ('2028-04');
INSERT INTO `base_month` VALUES ('2028-05');
INSERT INTO `base_month` VALUES ('2028-06');
INSERT INTO `base_month` VALUES ('2028-07');
INSERT INTO `base_month` VALUES ('2028-08');
INSERT INTO `base_month` VALUES ('2028-09');
INSERT INTO `base_month` VALUES ('2028-10');
INSERT INTO `base_month` VALUES ('2028-11');
INSERT INTO `base_month` VALUES ('2028-12');
INSERT INTO `base_month` VALUES ('2029-01');
INSERT INTO `base_month` VALUES ('2029-02');
INSERT INTO `base_month` VALUES ('2029-03');
INSERT INTO `base_month` VALUES ('2029-04');
INSERT INTO `base_month` VALUES ('2029-05');
INSERT INTO `base_month` VALUES ('2029-06');
INSERT INTO `base_month` VALUES ('2029-07');
INSERT INTO `base_month` VALUES ('2029-08');
INSERT INTO `base_month` VALUES ('2029-09');
INSERT INTO `base_month` VALUES ('2029-10');
INSERT INTO `base_month` VALUES ('2029-11');
INSERT INTO `base_month` VALUES ('2029-12');
INSERT INTO `base_month` VALUES ('2030-01');
INSERT INTO `base_month` VALUES ('2030-02');
INSERT INTO `base_month` VALUES ('2030-03');
INSERT INTO `base_month` VALUES ('2030-04');
INSERT INTO `base_month` VALUES ('2030-05');
INSERT INTO `base_month` VALUES ('2030-06');
INSERT INTO `base_month` VALUES ('2030-07');
INSERT INTO `base_month` VALUES ('2030-08');
INSERT INTO `base_month` VALUES ('2030-09');
INSERT INTO `base_month` VALUES ('2030-10');
INSERT INTO `base_month` VALUES ('2030-11');
INSERT INTO `base_month` VALUES ('2030-12');

-- ----------------------------
-- Table structure for `orders`
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `uid` int NOT NULL COMMENT '下单人编号',
  `pid` int NOT NULL COMMENT '产品编号',
  `num` int NOT NULL COMMENT '订单数量',
  `order_time` datetime DEFAULT NULL COMMENT '订单审核通过时间',
  `status` int DEFAULT NULL COMMENT '订单审核状态',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '下单时间',
  `username` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `allprice` decimal(10,2) DEFAULT NULL,
  `pname` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('29', '24', '16', '1', '2024-06-06 10:36:39', '1', '2024-06-06 10:38:40', 'sale', '3000.00', '床');
INSERT INTO `orders` VALUES ('30', '24', '18', '1', '2024-06-06 10:36:51', '1', '2024-06-06 10:39:14', 'sale', '3000.00', '饭桌');
INSERT INTO `orders` VALUES ('31', '24', '18', '2', '2024-06-06 10:38:02', '1', '2024-06-06 10:39:10', 'sale', '6000.00', '饭桌');
INSERT INTO `orders` VALUES ('32', '24', '17', '1', '2024-06-06 10:40:16', '1', '2024-06-06 10:50:35', 'sale', '2500.00', '沙发');
INSERT INTO `orders` VALUES ('33', '26', '16', '1', '2024-06-06 10:54:23', '1', '2024-06-06 10:58:10', 'sale2', '3000.00', '床');
INSERT INTO `orders` VALUES ('34', '26', '18', '2', '2024-06-06 10:54:31', '1', '2024-06-06 10:58:23', 'sale2', '6000.00', '饭桌');
INSERT INTO `orders` VALUES ('35', '26', '18', '2', '2024-06-06 10:54:35', '1', '2024-06-06 10:58:13', 'sale2', '6000.00', '饭桌');
INSERT INTO `orders` VALUES ('36', '26', '21', '1', '2024-06-06 10:54:42', '1', '2024-06-06 10:58:16', 'sale2', '800.00', '茶几');
INSERT INTO `orders` VALUES ('37', '26', '20', '1', '2024-06-06 10:54:47', '1', '2024-06-06 10:58:19', 'sale2', '500.00', '书桌');
INSERT INTO `orders` VALUES ('38', '26', '18', '1', '2024-06-06 10:54:54', '1', '2024-06-06 10:58:26', 'sale2', '3000.00', '饭桌');
INSERT INTO `orders` VALUES ('39', '35', '16', '1', '2024-06-06 11:18:04', '1', '2024-06-06 11:18:50', 'sale1', '3000.00', '床');
INSERT INTO `orders` VALUES ('40', '35', '17', '1', '2024-06-06 11:18:10', '1', '2024-06-06 11:18:55', 'sale1', '2500.00', '沙发');
INSERT INTO `orders` VALUES ('41', '35', '18', '1', '2024-06-06 11:18:15', '1', '2024-06-06 11:27:37', 'sale1', '3000.00', '饭桌');
INSERT INTO `orders` VALUES ('42', '35', '20', '1', '2024-06-06 11:18:20', '0', null, 'sale1', '500.00', '书桌');
INSERT INTO `orders` VALUES ('43', '35', '21', '1', '2024-06-06 11:18:25', '2', '2024-06-06 11:19:03', 'sale1', '800.00', '茶几');
INSERT INTO `orders` VALUES ('44', '26', '16', '1', '2024-06-06 11:26:58', '0', null, 'sale2', '3000.00', '床');
INSERT INTO `orders` VALUES ('45', '26', '17', '2', '2024-06-06 11:27:05', '0', null, 'sale2', '5000.00', '沙发');
INSERT INTO `orders` VALUES ('46', '26', '19', '1', '2024-06-06 11:27:11', '0', null, 'sale2', '5000.00', '衣柜');
INSERT INTO `orders` VALUES ('47', '26', '23', '1', '2024-06-06 11:48:17', '1', '2024-06-06 11:48:47', 'sale2', '600.00', '餐边柜');
INSERT INTO `orders` VALUES ('48', '26', '22', '1', '2024-06-06 11:48:23', '0', null, 'sale2', '3500.00', '电视柜');

-- ----------------------------
-- Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '产品编号',
  `pname` varchar(200) NOT NULL COMMENT '产品名称',
  `picture` varchar(200) DEFAULT NULL COMMENT '产品图片',
  `description` varchar(255) DEFAULT NULL COMMENT '产品描述',
  `p_Type` varchar(25) DEFAULT NULL COMMENT '产品类型',
  `unit` varchar(255) DEFAULT NULL COMMENT '产地',
  `price` decimal(10,2) NOT NULL COMMENT '閸烆喕鐜?',
  `stock` int NOT NULL COMMENT '库存',
  `create_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('16', '床', '/images/78b52418-9c7a-424f-beac-9ab9317e9d8f/微信图片_20210705000618.png', '1.8x2米', '家用', '宣城', '3000.00', '97', '2021-07-06 10:30:47');
INSERT INTO `product` VALUES ('17', '沙发', '/images/e667ed51-90c9-4638-9029-e735f33ef746/微信图片_20210706103210.png', '1.8x2米', '家用', '上海', '2500.00', '198', '2021-07-06 10:32:20');
INSERT INTO `product` VALUES ('18', '饭桌', '/images/61252dc0-ac83-40bd-aa91-475127c9856a/微信图片_20210706103532.png', '饭桌6人桌', '家用', '宣城', '3000.00', '91', '2021-07-06 10:35:47');
INSERT INTO `product` VALUES ('19', '衣柜', '/images/39e62277-916b-4973-9da5-ee7fd232ca83/微信图片_20210706104723.png', '三四五门组合衣橱大衣柜转角', '家用', '恢复', '5000.00', '30', '2021-07-06 10:47:37');
INSERT INTO `product` VALUES ('20', '书桌', '/images/e8c031ae-ae25-4122-9f94-2990e1d2b167/微信图片_20210706104838.png', '橡木电脑桌藤编学生写字桌家用北欧双人桌', '家用', '宣城', '500.00', '29', '2021-07-06 10:49:03');
INSERT INTO `product` VALUES ('21', '茶几', '/images/27d376ad-b4c8-4bb9-98d7-ddd46cef8045/微信图片_20210706105013.png', '橡木电脑桌藤编学生写字桌家用北欧双人桌', '家用', '宣城', '800.00', '298', '2021-07-06 10:50:20');
INSERT INTO `product` VALUES ('22', '电视柜', '/images/36438d1e-2128-4276-92e5-5c0b32aac5f6/微信图片_20210706112515.png', '大理石岩板茶几电视柜组合现代简约小户型实木可伸缩轻奢沙发地柜', '家用', '上海', '3500.00', '100', '2021-07-06 11:26:31');
INSERT INTO `product` VALUES ('23', '餐边柜', '/images/c0c854fb-8c44-48b2-b664-1c2395e098bb/微信图片_20210706114625.png', '家用', '家用', '北京', '600.00', '19', '2021-07-06 11:47:46');

-- ----------------------------
-- Table structure for `producttype`
-- ----------------------------
DROP TABLE IF EXISTS `producttype`;
CREATE TABLE `producttype` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '产品类型编号',
  `pid` int NOT NULL COMMENT '产品编号',
  `p_name` varchar(32) NOT NULL COMMENT '产品名称',
  `p_Type` varchar(32) NOT NULL COMMENT '产品类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of producttype
-- ----------------------------

-- ----------------------------
-- Table structure for `sales`
-- ----------------------------
DROP TABLE IF EXISTS `sales`;
CREATE TABLE `sales` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '销售编号',
  `pid` int NOT NULL COMMENT '产品编号',
  `p_name` varchar(32) NOT NULL COMMENT '产品名称',
  `price` double(32,0) NOT NULL COMMENT '产品售价',
  `num` int NOT NULL COMMENT '销售数量/订单数量',
  `order_time` datetime NOT NULL COMMENT '销售日期',
  `uid` int NOT NULL COMMENT '批发商用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sales
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `username` varchar(200) NOT NULL COMMENT '用户名',
  `password` varchar(32) NOT NULL COMMENT '用户密码',
  `sex` varchar(4) DEFAULT NULL COMMENT '性别',
  `role` int NOT NULL COMMENT '用户身份 0管理员 1 普通用户',
  `job` varchar(32) DEFAULT NULL COMMENT '用户职称',
  `tel` varchar(32) DEFAULT NULL COMMENT '联系方式',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `email` varchar(30) DEFAULT NULL COMMENT '电子邮箱',
  `status` int DEFAULT NULL COMMENT '审核状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建的时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'admin', '123456', '男', '0', '系统管理员', '13423456789', '上海', '12876533@qq.com', '1', '2024-02-11 18:39:09');
INSERT INTO `user` VALUES ('2', 'test', '123456', '女', '1', '经理', '13659874528', '浙江', '78513@163.com', '1', '2024-03-12 18:39:29');
INSERT INTO `user` VALUES ('3', 'cgy', '123456', '男', '2', '采购员', '14478965428', '北京', '123@123.com', '1', '2024-03-12 18:39:44');
INSERT INTO `user` VALUES ('4', 'mly', '123456', '女', '0', '系统管理员', '12547896532', '上海', '8763759964@qq.com', '1', '2024-03-12 18:40:01');
INSERT INTO `user` VALUES ('5', '经理', '123456', '男', '1', '管理员', '18879654284', '四川', '123@163.com', '1', '2024-03-13 18:40:20');
INSERT INTO `user` VALUES ('6', 'h1', '123456', '男', '0', '系统管理员', '17758964528', '江西', '7773@123.com', '1', '2024-03-13 19:40:40');
INSERT INTO `user` VALUES ('7', 'niun', '123456', '女', '0', '系统管理员', '14879652487', '广东', '1876533987@qq.com', '1', '2024-03-14 11:40:46');
INSERT INTO `user` VALUES ('8', 'lalal', '123456', '女', '1', '管理员', '13547896428', '广西', '1247543@123.com', '1', '2024-03-14 12:41:25');
INSERT INTO `user` VALUES ('9', 'aa', '123456', '女', '0', '系统管理员', '12365785496', '上海', '765338896@qq.com', '1', '2024-03-15 10:41:50');
INSERT INTO `user` VALUES ('10', 'h123', '123456', '男', '2', '普通用户', '16635487965', '浙江', '9648271235@qq.com', '0', '2024-03-16 15:42:02');
INSERT INTO `user` VALUES ('11', 'h3', '123456', '女', '2', '学生', '17785462439', '重庆', '1047854123@qq.com', '1', '2024-04-01 12:42:29');
INSERT INTO `user` VALUES ('12', '11115', '1223456', '男', '0', '经理', '13985467954', '杭州', '17743@123.com', '1', '2024-04-02 11:42:40');
INSERT INTO `user` VALUES ('13', '1111', '123456', '女', '1', '工人', '19136542874', '新余', 'yan3@163.com', '0', '2024-04-08 17:42:52');
INSERT INTO `user` VALUES ('14', '2w', '123456', '男', '0', '管理员', '16643258964', '广州', 'hua23@123.com', '0', '2024-04-09 18:43:06');
INSERT INTO `user` VALUES ('15', 'h23', '123456', '女', '0', '管理员', '14458763254', '上海', '1277513@163.com', '0', '2024-04-17 18:43:16');
INSERT INTO `user` VALUES ('16', 'hq', '222222', '男', '1', '教师', '12365478965', '北京', '124753@123.com', '0', '2024-04-17 18:44:05');
INSERT INTO `user` VALUES ('17', 'aaa', '123456', '女', '1', '工人', '15647895425', '江西', '768896864@qq.com', '1', '2024-04-18 17:44:20');
INSERT INTO `user` VALUES ('18', 'yu', '123456', '女', '0', '管理员', '12345698741', '浙江', '123@183.com', '1', '2024-04-19 18:44:46');
INSERT INTO `user` VALUES ('19', 'h5', '123456', '女', '0', '销售员', '12345698741', '浙江', '123@187.com', '1', '2024-04-19 19:45:01');
INSERT INTO `user` VALUES ('20', 'h9', '123456', '女', '1', '教师', '12345698741', '浙江', '123@157.com', '1', '2024-04-20 18:45:14');
INSERT INTO `user` VALUES ('21', 'h7', '123', '女', '0', '销售员', '12345698741', '浙江', '123@187.com', '1', '2024-04-20 18:45:30');
INSERT INTO `user` VALUES ('22', 'h2', '123', '女', '0', '经理', '12345698741', '浙江', '123@157.com', '1', '2024-04-21 18:45:47');
INSERT INTO `user` VALUES ('23', 'h4', '123', '女', '1', '教师', '12345698741', '浙江', '123@187.com', '0', '2024-04-21 20:46:07');
INSERT INTO `user` VALUES ('24', 'ht', '123', '女', '1', '教师', '12345698741', '浙江', '123@163.com', '0', '2024-04-22 18:46:15');
INSERT INTO `user` VALUES ('25', 'hhhh', '123', '女', '0', '经理', '12345698741', '浙江', '123@157.com', '1', '2024-04-23 18:46:40');
INSERT INTO `user` VALUES ('26', 'hkk', '123', '女', '0', '经理', '12345698741', '浙江', '123@187.com', '1', '2024-04-23 19:46:53');
INSERT INTO `user` VALUES ('27', 'huu', '123', '女', '0', '销售员', '12345698741', '浙江', '123@183.com', '1', '2024-04-24 18:47:11');
INSERT INTO `user` VALUES ('28', 'lll', '123', '女', '1', '教师', '12345698741', '浙江', '123@163.com', '1', '2024-04-24 18:47:20');
INSERT INTO `user` VALUES ('29', 'hww', '123', '女', '1', '学生', '12345698741', '浙江', '123@157.com', '1', '2024-04-25 18:53:34');
INSERT INTO `user` VALUES ('30', 'hr', '123', '女', '1', '教师', '12345698741', '浙江', '123@187.com', '1', '2024-04-25 18:53:44');
INSERT INTO `user` VALUES ('31', 'hg', '123', '女', '0', '经理', '12345698741', '浙江', '123@187.com', '0', '2024-04-26 18:53:55');
INSERT INTO `user` VALUES ('32', 'hj', '123', '女', '0', '销售员', '12345698741', '浙江', '123@187.com', '1', '2024-04-26 18:54:33');
INSERT INTO `user` VALUES ('33', 'lydia', '133456', '女', '1', '学生', '18745963214', '上海', '1253@128.com', '1', '2024-04-27 12:54:44');
INSERT INTO `user` VALUES ('34', 'niuniu', '123456', '女', '1', '采购部', '12345698741', '浙江', '123@183.com', '1', '2024-04-27 13:54:54');
INSERT INTO `user` VALUES ('35', 'niuni', '123456', '男', '0', '经理', '12345698741', '浙江', '123@183.com', '1', '2024-04-27 18:55:29');
INSERT INTO `user` VALUES ('36', 'hkkk', '123', '女', '1', '教师', '12345698741', '浙江', '123@187.com', '1', '2024-04-28 10:55:37');
INSERT INTO `user` VALUES ('37', 'lolol', '123456', '女', '1', '采购部', '12345698747', '浙江', '123@163.com', '1', '2024-04-28 11:55:47');
INSERT INTO `user` VALUES ('38', 'lolol4', '123456', '女', '1', '采购部', '12345698747', '浙江', '123@163.com', '1', '2024-04-28 12:55:59');
INSERT INTO `user` VALUES ('39', 'hq', '123', '男', '0', '管理员', '12345697485', '浙江', '123@177.com', '1', '2024-04-29 18:56:15');
INSERT INTO `user` VALUES ('40', 'hly', '123', '男', '1', '教师', '12345697485', '上海', '123@177.com', '1', '2024-04-29 22:56:23');
INSERT INTO `user` VALUES ('41', '萝莉', '123456', '女', '0', '销售员', '12345698741', '浙江', '123789@163.com', '1', '2024-04-30 18:56:31');
INSERT INTO `user` VALUES ('42', '萝莉1', '123456', '女', '1', '教师', '12345698741', '浙江', '123789@163.com', '1', '2024-04-30 19:56:41');
INSERT INTO `user` VALUES ('43', 'ls', '123456', '女', '0', '经理', '12345685741', '上海', '12306@126.com', '1', '2024-05-02 18:56:49');
INSERT INTO `user` VALUES ('44', 'h888', '123456', '女', '0', '经理', '12345698741', '浙江', '123@183.com', '0', '2024-05-02 10:56:57');
INSERT INTO `user` VALUES ('45', '4444', '123', '男', '1', '采购部', '17584216486', '上海', '152@163.com', '0', '2024-05-03 18:57:03');
INSERT INTO `user` VALUES ('46', 'qqqq', '123456', '女', '1', '教师', '12345698741', '浙江', '123@187.com', '0', '2024-05-05 18:57:09');
INSERT INTO `user` VALUES ('47', 'nilll', '123456', '女', '0', '部门经理', '12345698741', '浙江', '123@187.com', '0', '2024-05-14 18:57:13');
INSERT INTO `user` VALUES ('48', 'mmm', '123', '男', '1', '部门经理', '12545698741', '上海', '188743@163.com', '0', '2024-05-20 11:43:44');
INSERT INTO `user` VALUES ('49', 'mmw', '123', '男', '1', '部门经理', '12545698741', '上海', '188743@163.com', '0', '2024-05-21 07:43:29');
