/*
 Navicat Premium Data Transfer

 Source Server         : 112.74.177.170
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 112.74.177.170
 Source Database       : new_mall

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : utf-8

 Date: 05/23/2019 15:21:28 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `info_incre_table`
-- ----------------------------
DROP TABLE IF EXISTS `info_incre_table`;
CREATE TABLE `info_incre_table` (
  `ID` int(11) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `n_address`
-- ----------------------------
DROP TABLE IF EXISTS `n_address`;
CREATE TABLE `n_address` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地址_id',
  `uid` varchar(100) DEFAULT NULL COMMENT '用户_uid',
  `name` varchar(100) DEFAULT NULL COMMENT '收货人姓名',
  `phone` varchar(100) DEFAULT NULL COMMENT '收货人电话号码',
  `province_id` varchar(100) DEFAULT NULL COMMENT '省份_id',
  `province_name` varchar(100) DEFAULT NULL COMMENT '省份_名称',
  `city_id` varchar(100) DEFAULT NULL COMMENT '城市_id',
  `city_name` varchar(100) DEFAULT NULL COMMENT '城市_名称',
  `region_id` varchar(100) DEFAULT NULL COMMENT '地区_id',
  `region_name` varchar(100) DEFAULT NULL COMMENT '地区_名称',
  `street_id` varchar(100) DEFAULT NULL COMMENT '街道_id',
  `street_name` varchar(100) DEFAULT NULL COMMENT '街道_名称',
  `detail_address` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `is_default_address` varchar(10) DEFAULT '0',
  `status` int(11) DEFAULT '0' COMMENT '商品状态: 0是未删除，1是已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='地址表';

-- ----------------------------
--  Table structure for `n_balance_log`
-- ----------------------------
DROP TABLE IF EXISTS `n_balance_log`;
CREATE TABLE `n_balance_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` varchar(20) DEFAULT NULL COMMENT '用户_uid',
  `cashback_to_user_balance` varchar(20) DEFAULT NULL COMMENT '返现到用户的余额',
  `user_balance` varchar(20) DEFAULT NULL COMMENT '用户的余额',
  `cashback_status` varchar(10) DEFAULT '0' COMMENT '返现状态，0：返现失败，1：返现成功',
  `remark` text COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='余额日志表';

-- ----------------------------
--  Table structure for `n_cash_log`
-- ----------------------------
DROP TABLE IF EXISTS `n_cash_log`;
CREATE TABLE `n_cash_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` varchar(20) DEFAULT NULL COMMENT '用户_uid',
  `cash_to_wx_money` varchar(20) DEFAULT NULL COMMENT '提现到微信的金额',
  `cash_fee` varchar(10) DEFAULT NULL COMMENT '提现手续费',
  `user_balance` varchar(20) DEFAULT NULL COMMENT '用户的余额',
  `remark` text COMMENT '备注',
  `cash_status` varchar(10) DEFAULT '0' COMMENT '提现状态，0：提现失败，1：提现成功',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提现日志表';

-- ----------------------------
--  Table structure for `n_dic`
-- ----------------------------
DROP TABLE IF EXISTS `n_dic`;
CREATE TABLE `n_dic` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典表_id',
  `dic_type` varchar(50) NOT NULL COMMENT '字典表_类型',
  `dic_code` varchar(100) NOT NULL COMMENT '字典表_code',
  `dic_name` varchar(200) NOT NULL COMMENT '字典表_名称',
  `dic_remark` varchar(1000) DEFAULT NULL COMMENT '字典表_备注,遵循json字符串格式',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '字典表_创建时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '字典表_更新时间',
  `dic_status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_n_dic_of_dic_code` (`dic_code`),
  KEY `index_n_dic_of_dic_name` (`dic_name`),
  KEY `index_n_dic_of_dic_type` (`dic_type`),
  KEY `index_n_dic_of_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=659004547 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
--  Table structure for `n_food`
-- ----------------------------
DROP TABLE IF EXISTS `n_food`;
CREATE TABLE `n_food` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '食物_id',
  `shop_id` varchar(100) DEFAULT NULL COMMENT '店铺ID',
  `food_type_title` varchar(100) DEFAULT NULL COMMENT '食物类型名称',
  `food_title` varchar(100) DEFAULT NULL COMMENT '食物名称',
  `food_degist` text COMMENT '食物简介',
  `food_price` varchar(200) DEFAULT NULL COMMENT '食物价格',
  `food_head_img_url` text NOT NULL COMMENT '食物招牌图片地址',
  `food_describe_img_url` text NOT NULL COMMENT '食物详情链接，遵循json数组字符串格式',
  `food_options` text NOT NULL COMMENT '食物选项，比如：规格，辣味，咸度等，遵循json数组字符串格式',
  `remark` varchar(200) DEFAULT NULL COMMENT '食物备注',
  `status` int(11) DEFAULT '0' COMMENT '食物状态: 0是已上架，1是已下架',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8 COMMENT='食物表';

-- ----------------------------
--  Table structure for `n_integral_log`
-- ----------------------------
DROP TABLE IF EXISTS `n_integral_log`;
CREATE TABLE `n_integral_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` varchar(20) DEFAULT NULL COMMENT '用户_uid',
  `exchange_to_user_integral` varchar(20) DEFAULT NULL COMMENT '兑换到用户的积分数量',
  `user_integral` varchar(20) DEFAULT NULL COMMENT '用户的积分',
  `exchange_status` varchar(10) DEFAULT '0' COMMENT '兑换状态，0：兑换失败，1：兑换成功',
  `remark` text COMMENT '备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='积分日志表';

-- ----------------------------
--  Table structure for `n_league`
-- ----------------------------
DROP TABLE IF EXISTS `n_league`;
CREATE TABLE `n_league` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` varchar(50) DEFAULT NULL COMMENT '用户uid',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系号码',
  `name` varchar(50) DEFAULT NULL COMMENT '联系姓名',
  `league_type_code` varchar(50) DEFAULT NULL COMMENT '加盟类型，0：我要开店，1：成为服务商，2：成为代理',
  `remark` varchar(50) DEFAULT NULL COMMENT '加盟备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='加盟表';

-- ----------------------------
--  Table structure for `n_luck_draw`
-- ----------------------------
DROP TABLE IF EXISTS `n_luck_draw`;
CREATE TABLE `n_luck_draw` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户uid',
  `wx_order_id` varchar(100) NOT NULL COMMENT '微信订单编号',
  `luck_draw_code` varchar(255) NOT NULL COMMENT '奖品ID',
  `remark` text COMMENT '备注',
  `status` int(11) DEFAULT '0' COMMENT '抽奖状态，0是未发放，1是已发放，2是已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `n_luck_draw_wx_order_id_uindex` (`wx_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `n_order`
-- ----------------------------
DROP TABLE IF EXISTS `n_order`;
CREATE TABLE `n_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单_id',
  `uid` varchar(100) DEFAULT NULL COMMENT '用户_uid',
  `wx_order_id` varchar(100) NOT NULL COMMENT '微信订单编号',
  `order_type` varchar(100) DEFAULT 'payTheBill' COMMENT '订单类型：买单，payTheBill；购买商品：purchaseProduct',
  `foods_id` varchar(100) DEFAULT NULL COMMENT '点餐食物的所有id，使用英文逗号(,)进行分隔',
  `foods_num` text COMMENT '点餐食物的数量，数组的json字符串',
  `transaction_foods_detail` text COMMENT '交易时食物的详情,数组的json字符串',
  `product_id` varchar(100) DEFAULT NULL COMMENT '商品_id',
  `product_num` varchar(100) DEFAULT NULL COMMENT '商品_数量',
  `transaction_product_detail` text COMMENT '交易时的商品信息',
  `address_id` varchar(100) DEFAULT NULL COMMENT '地址_id',
  `express_name` varchar(100) DEFAULT NULL COMMENT '快递名称',
  `express_number` varchar(50) DEFAULT NULL COMMENT '快递编号',
  `shop_id` varchar(10) DEFAULT NULL COMMENT '店铺_id',
  `all_pay_amount` varchar(100) DEFAULT NULL COMMENT '支付总额',
  `pay_money` varchar(100) DEFAULT NULL COMMENT '支付金额',
  `use_balance_monney` varchar(100) DEFAULT NULL COMMENT '使用余额的金额',
  `use_integral_num` varchar(100) DEFAULT NULL COMMENT '使用积分的数量',
  `form_id` varchar(100) DEFAULT NULL COMMENT '当前订单交易的时候的formId',
  `remark` text COMMENT '交易时的备注',
  `status` int(11) DEFAULT '0' COMMENT '订单状态: 0是待支付，1是已支付，2是已发货，3是已完成，4是已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=245 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
--  Table structure for `n_product`
-- ----------------------------
DROP TABLE IF EXISTS `n_product`;
CREATE TABLE `n_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品_id',
  `title` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `degist` varchar(200) DEFAULT NULL COMMENT '商品简介',
  `stock` varchar(100) DEFAULT '0' COMMENT '商品库存, 默认为0',
  `head_img_url` varchar(1000) DEFAULT NULL COMMENT '商品头链接',
  `describe_img_url` text COMMENT '商品详情链接，数组的json字符串',
  `price` varchar(10) DEFAULT '10000' COMMENT '商品价格',
  `integral` varchar(10) DEFAULT '0' COMMENT '商品所需积分',
  `category` varchar(100) DEFAULT NULL COMMENT '商品类别',
  `deduction` varchar(100) DEFAULT NULL COMMENT '折扣',
  `descript` varchar(200) DEFAULT NULL COMMENT '商品描述',
  `status` int(11) DEFAULT '0' COMMENT '商品状态: 0是已上架，1是已下架',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2896 DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
--  Table structure for `n_shop`
-- ----------------------------
DROP TABLE IF EXISTS `n_shop`;
CREATE TABLE `n_shop` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '店铺_id',
  `shop_title` varchar(100) NOT NULL COMMENT '店铺名称',
  `shop_degist` varchar(500) NOT NULL COMMENT '店铺简介',
  `shop_phone` varchar(100) NOT NULL COMMENT '店铺电话',
  `shop_address` varchar(100) NOT NULL COMMENT '店铺地址',
  `shop_lon` varchar(100) NOT NULL COMMENT '店铺经度',
  `shop_lat` varchar(100) NOT NULL COMMENT '店铺纬度',
  `shop_head_img_url` varchar(100) NOT NULL COMMENT '店铺招牌图片地址',
  `shop_describe_img_url` text NOT NULL COMMENT '店铺详情链接，遵循json数组字符串格式',
  `shop_minimum` varchar(100) DEFAULT '99',
  `shop_discount_id` varchar(100) DEFAULT NULL COMMENT '折扣ID，平台与商家确定的折扣',
  `shop_business_hours_start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '营业时间的开始时间',
  `shop_business_hours_end_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '营业时间的结束时间',
  `shop_remark` varchar(1000) DEFAULT NULL COMMENT '店铺表_备注,',
  `shop_status` int(11) NOT NULL DEFAULT '0' COMMENT '店铺表_状态，0是待签约，1是已签约',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '店铺表_创建时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '店铺表_更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='店铺表';

-- ----------------------------
--  Table structure for `n_user`
-- ----------------------------
DROP TABLE IF EXISTS `n_user`;
CREATE TABLE `n_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户_id',
  `open_id` varchar(50) DEFAULT NULL COMMENT '微信的open_id',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '用户昵称',
  `gender` int(11) DEFAULT NULL COMMENT '性别',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `province` varchar(100) DEFAULT NULL COMMENT '省份',
  `country` varchar(100) DEFAULT NULL COMMENT '国家',
  `language` varchar(100) DEFAULT NULL COMMENT '用户的语言',
  `avatar_url` varchar(500) DEFAULT NULL COMMENT '用户头像',
  `balance` varchar(100) DEFAULT NULL COMMENT '余额',
  `integral` varchar(100) DEFAULT NULL COMMENT '积分',
  `user_source` varchar(100) DEFAULT 'miniProgram' COMMENT '微信用户来源，miniProgram是小程序用户，publicNumber是公众号用户',
  `auto_cash_to_wx_flag` varchar(10) DEFAULT '0' COMMENT '自动将余额提现到微信零钱，0：否，1：是',
  `shop_id` varchar(100) DEFAULT NULL COMMENT '店铺ID，存在即代表当前用户属于商家',
  `user_remark` varchar(100) DEFAULT NULL COMMENT '用户_备注',
  `gray_status` int(11) DEFAULT '0' COMMENT '灰度用户状态，0是正常登陆用户，1是不需要登陆的用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_更新时间',
  PRIMARY KEY (`id`),
  KEY `index_n_user_of_id` (`id`),
  KEY `index_n_user_of_nick_name` (`nick_name`),
  KEY `index_n_user_of_open_id` (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='用户表';

SET FOREIGN_KEY_CHECKS = 1;
