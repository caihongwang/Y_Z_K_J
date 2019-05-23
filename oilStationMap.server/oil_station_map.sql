/*
 Navicat Premium Data Transfer

 Source Server         : 112.74.177.170
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 112.74.177.170
 Source Database       : oil_station_map

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : utf-8

 Date: 05/23/2019 15:22:18 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `o_comments`
-- ----------------------------
DROP TABLE IF EXISTS `o_comments`;
CREATE TABLE `o_comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '意见_id',
  `uid` int(11) NOT NULL,
  `comments` text NOT NULL COMMENT '意见_意见内容',
  `remark` varchar(200) DEFAULT NULL COMMENT '意见_备注',
  `STATUS` int(11) DEFAULT '0' COMMENT '意见_状态，0是未处理，1是已处理',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '意见_更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '意见_创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='意见';

-- ----------------------------
--  Table structure for `o_custom_message_history`
-- ----------------------------
DROP TABLE IF EXISTS `o_custom_message_history`;
CREATE TABLE `o_custom_message_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `open_id` varchar(100) NOT NULL COMMENT '用户的唯一标识openId',
  `mini_program_id` varchar(100) DEFAULT NULL COMMENT '小程序的唯一标示id',
  `mini_program_name` varchar(100) DEFAULT NULL COMMENT '小程序名称',
  `custom_message_type` varchar(100) NOT NULL COMMENT '客服消息内容种类',
  `custom_message_code` varchar(100) NOT NULL COMMENT '客服消息编码',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=462 DEFAULT CHARSET=utf8 COMMENT='客服消息访问历史记录';

-- ----------------------------
--  Table structure for `o_dic`
-- ----------------------------
DROP TABLE IF EXISTS `o_dic`;
CREATE TABLE `o_dic` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典表_id',
  `dic_type` varchar(50) NOT NULL COMMENT '字典表_类型',
  `dic_code` varchar(100) NOT NULL COMMENT '字典表_code',
  `dic_name` varchar(200) NOT NULL COMMENT '字典表_名称',
  `dic_remark` varchar(1000) DEFAULT NULL COMMENT '字典表_备注,遵循json字符串格式',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '字典表_创建时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '字典表_更新时间',
  `dic_status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `index_o_dic_of_id` (`id`),
  KEY `index_o_dic_of_dic_type` (`dic_type`),
  KEY `index_o_dic_of_dic_code` (`dic_code`),
  KEY `index_o_dic_of_dic_name` (`dic_name`)
) ENGINE=InnoDB AUTO_INCREMENT=94377 DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
--  Table structure for `o_league`
-- ----------------------------
DROP TABLE IF EXISTS `o_league`;
CREATE TABLE `o_league` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` varchar(50) DEFAULT NULL COMMENT '用户uid',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系号码',
  `name` varchar(50) DEFAULT NULL COMMENT '联系姓名',
  `league_type_code` varchar(50) DEFAULT NULL COMMENT '加盟类型，0：我要开店，1：成为服务商，2：成为代理',
  `remark` varchar(50) DEFAULT NULL COMMENT '加盟备注',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='加盟表';

-- ----------------------------
--  Table structure for `o_oil_station`
-- ----------------------------
DROP TABLE IF EXISTS `o_oil_station`;
CREATE TABLE `o_oil_station` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '加油站ID',
  `oil_station_code` varchar(100) NOT NULL COMMENT '加油站编码',
  `oil_station_name` varchar(100) NOT NULL COMMENT '加油站名称',
  `oil_station_area_spell` varchar(100) DEFAULT NULL COMMENT '加油站地区拼音',
  `oil_station_area_name` varchar(45) DEFAULT NULL COMMENT '加油站地区名称',
  `oil_station_adress` varchar(500) NOT NULL COMMENT '加油站地址',
  `oil_station_brand_name` varchar(100) DEFAULT NULL COMMENT '加油站品牌名称',
  `oil_station_type` varchar(100) NOT NULL COMMENT '加油站类型：加盟店，民营店',
  `oil_station_discount` varchar(100) DEFAULT NULL COMMENT '加油站折扣店',
  `oil_station_exhaust` varchar(100) DEFAULT NULL COMMENT '加油站油品排放',
  `oil_station_position` varchar(100) NOT NULL COMMENT '加油站经纬度位置（经度,纬度）',
  `oil_station_lon` varchar(100) NOT NULL COMMENT '加油站经度',
  `oil_station_lat` varchar(100) NOT NULL COMMENT '加油站纬度',
  `oil_station_pay_type` varchar(100) DEFAULT NULL COMMENT '加油站支付方式',
  `oil_station_price` text NOT NULL COMMENT '加油站油价，使用json格式进行存储',
  `oil_station_distance` varchar(100) DEFAULT NULL COMMENT '加油站距离',
  `is_manual_modify` int(11) DEFAULT '0' COMMENT '是否手动更新，0为false,不手动更新即自动更新数据；1为true,人为手动更新数据',
  `is_hire` int(11) DEFAULT '0',
  `oil_station_wx_payment_code_img_url` varchar(1000) DEFAULT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间\r\n	',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `share_title` varchar(200) DEFAULT '油价又要涨了!赶紧快来看看您附近的加油站的油价吧，秒慢无...',
  PRIMARY KEY (`id`),
  KEY `index_o_oil_station_of_id` (`id`),
  KEY `index_o_oil_station_of_oil_station_code` (`oil_station_code`),
  KEY `index_o_oil_station_of_oil_station_name` (`oil_station_name`),
  KEY `index_o_oil_station_of_oil_station_lon` (`oil_station_lon`),
  KEY `index_o_oil_station_of_oil_station_lat` (`oil_station_lat`)
) ENGINE=InnoDB AUTO_INCREMENT=329804 DEFAULT CHARSET=utf8 COMMENT='加油站基本信息';

-- ----------------------------
--  Table structure for `o_oil_station_operator`
-- ----------------------------
DROP TABLE IF EXISTS `o_oil_station_operator`;
CREATE TABLE `o_oil_station_operator` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '操作编码',
  `uid` int(11) DEFAULT NULL COMMENT '操作用户',
  `oil_station_code` varchar(100) DEFAULT NULL COMMENT '加油站编码',
  `red_packet_total` varchar(100) DEFAULT '0' COMMENT '红包金额',
  `operator` varchar(100) DEFAULT NULL COMMENT '操作，目前分为 添加油站 和 纠正油价',
  `status` int(11) DEFAULT '0' COMMENT '状态，0,待审核;1,待发放;-1,已拒绝;2,已发放;',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1223295 DEFAULT CHARSET=utf8 COMMENT='加油站操作';

-- ----------------------------
--  Table structure for `o_red_packet_draw_cash_history`
-- ----------------------------
DROP TABLE IF EXISTS `o_red_packet_draw_cash_history`;
CREATE TABLE `o_red_packet_draw_cash_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `draw_cash_money` varchar(45) NOT NULL COMMENT '提现金额',
  `remark` varchar(500) DEFAULT NULL,
  `status` int(11) DEFAULT '1' COMMENT '提现记录_状态，0是无效提现，1是有效提现',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包提现记录';

-- ----------------------------
--  Table structure for `o_red_packet_history`
-- ----------------------------
DROP TABLE IF EXISTS `o_red_packet_history`;
CREATE TABLE `o_red_packet_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `operator_id` int(11) DEFAULT NULL COMMENT '操作ID',
  `red_packet_money` varchar(45) NOT NULL COMMENT '红包金额',
  `remark` varchar(500) DEFAULT NULL,
  `status` int(11) DEFAULT '0' COMMENT '红包领取记录_状态，0是未正常发送红包(未领取)，1是正常发送红包(已领取)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='红包领取记录';

-- ----------------------------
--  Table structure for `o_user`
-- ----------------------------
DROP TABLE IF EXISTS `o_user`;
CREATE TABLE `o_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户_id',
  `open_id` varchar(50) DEFAULT NULL COMMENT '微信的open_id',
  `nick_name` varchar(100) DEFAULT NULL COMMENT '用户昵称',
  `gender` int(11) DEFAULT NULL COMMENT '性别',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `province` varchar(100) DEFAULT NULL COMMENT '省份',
  `country` varchar(100) DEFAULT NULL COMMENT '国家',
  `LANGUAGE` varchar(100) DEFAULT NULL COMMENT '用户的语言',
  `avatar_url` varchar(500) DEFAULT NULL COMMENT '用户头像',
  `recommend_uid` int(11) DEFAULT NULL,
  `user_remark` varchar(100) DEFAULT NULL COMMENT '用户_备注',
  `source` varchar(200) DEFAULT 'gh_417c90af3488',
  `gray_status` int(11) DEFAULT '0' COMMENT '灰度用户状态，0是正常登陆用户，1是不需要登陆的用户',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户_更新时间',
  PRIMARY KEY (`id`),
  KEY `index_o_user_of_id` (`id`),
  KEY `index_o_user_of_open_id` (`open_id`),
  KEY `index_o_user_of_nick_name` (`nick_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4183 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Table structure for `o_user_form_mapping`
-- ----------------------------
DROP TABLE IF EXISTS `o_user_form_mapping`;
CREATE TABLE `o_user_form_mapping` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL COMMENT '用户ID',
  `form_id` varchar(200) NOT NULL COMMENT '给当前用户发送模板消息唯一标识的formId',
  `form_id_status` int(11) DEFAULT '0' COMMENT '是否使用当前formId,0是未使用，1是已使用',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户与微信的模板消息的映射表';

-- ----------------------------
--  Procedure structure for `updateOilStationPrice_by_Procedure`
-- ----------------------------
DROP PROCEDURE IF EXISTS `updateOilStationPrice_by_Procedure`;
delimiter ;;
CREATE DEFINER=`root`@`%` PROCEDURE `updateOilStationPrice_by_Procedure`(
  IN  province           VARCHAR(300),
  IN  newOilStationPrice TEXT,
  IN  price_0            DECIMAL(10, 2),
  IN  price_92           DECIMAL(10, 2),
  IN  price_95           DECIMAL(10, 2),
  IN  price_98           DECIMAL(10, 2),
  OUT updateNum          INT
)
BEGIN
    DECLARE done INT DEFAULT 0;
    DECLARE row_id INT;
    DECLARE row_oilStationPrice TEXT;
    -- 这个语句声明一个光标保存记录集。也可以在子程序中定义多个光标，但是一个块中的每一个光标必须有唯一的名字
    DECLARE cur CURSOR FOR SELECT
                             id                AS "id",
                             oil_station_price AS "oilStationPrice"
                           FROM o_oil_station
                           WHERE oil_station_adress LIKE
                                 concat('%', province, '%');
    DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done = 1;
    OPEN cur;
    REPEAT -- 循环
      FETCH cur
      INTO row_id, row_oilStationPrice; -- 这个语句用指定的打开光标读取下一行（如果有下一行的话），并且前进光标指针
      IF NOT done
      THEN
        -- 更新 0#柴油 价格
        IF price_0 IS NOT NULL
        THEN
          SET row_oilStationPrice = JSON_REPLACE(
              row_oilStationPrice,
              SUBSTRING_INDEX(
                  REPLACE(JSON_SEARCH(row_oilStationPrice, 'all', '柴油'), '"',
                          ''), '.', 1),
              JSON_EXTRACT(newOilStationPrice,
                           SUBSTRING_INDEX(REPLACE(
                                               JSON_SEARCH(newOilStationPrice,
                                                           'all', '柴油'), '"',
                                               ''), '.', 1))
          );
          -- 随机 0#柴油 油价
          SET @price_0_new = 0;
          SET @randNum = (SELECT round(RAND()*0.1, 2));
          IF @randNum > 0.05
          THEN
            SET @price_0_new = round((price_0 - @randNum), 2);
          ELSE
            SET @price_0_new = round((price_0 + @randNum), 2);
          END IF;
          SET @price_0_str = concat(price_0, "");
          SET @price_0_new_str = concat(@price_0_new, "");
          SET row_oilStationPrice = REPLACE(row_oilStationPrice, @price_0_str,
                                            @price_0_new_str);
        END IF;

        -- 更新 92#汽油 价格
        IF price_92 IS NOT NULL
        THEN
          SET row_oilStationPrice = JSON_REPLACE(
              row_oilStationPrice,
              SUBSTRING_INDEX(
                  REPLACE(JSON_SEARCH(row_oilStationPrice, 'all', '92'), '"',
                          ''), '.', 1),
              JSON_EXTRACT(newOilStationPrice,
                           SUBSTRING_INDEX(REPLACE(
                                               JSON_SEARCH(newOilStationPrice,
                                                           'all', '92'), '"',
                                               ''), '.', 1))
          );
          -- 随机 92#汽油 油价
          SET @price_92_new = 0;
          SET @randNum = (SELECT round(RAND()*0.1, 2));
          IF @randNum > 0.05
          THEN
            SET @price_92_new = round((price_92 - @randNum), 2);
          ELSE
            SET @price_92_new = round((price_92 + @randNum), 2);
          END IF;
          SET @price_92_str = concat(price_92, "");
          SET @price_92_new_str = concat(@price_92_new, "");
          SET row_oilStationPrice = REPLACE(row_oilStationPrice, @price_92_str,
                                            @price_92_new_str);
        END IF;

        -- 更新 95#汽油 价格
        IF price_95 IS NOT NULL
        THEN
          SET row_oilStationPrice = JSON_REPLACE(
              row_oilStationPrice,
              SUBSTRING_INDEX(
                  REPLACE(JSON_SEARCH(row_oilStationPrice, 'all', '95'), '"',
                          ''), '.', 1),
              JSON_EXTRACT(newOilStationPrice,
                           SUBSTRING_INDEX(REPLACE(
                                               JSON_SEARCH(newOilStationPrice,
                                                           'all', '95'), '"',
                                               ''), '.', 1))
          );
          -- 随机 95#汽油 油价
          SET @price_95_new = 0;
          SET @randNum = (SELECT round(RAND()*0.1, 2));
          IF @randNum > 0.05
          THEN
            SET @price_95_new = round((price_95 - @randNum), 2);
          ELSE
            SET @price_95_new = round((price_95 + @randNum), 2);
          END IF;
          SET @price_95_str = concat(price_95, "");
          SET @price_95_new_str = concat(@price_95_new, "");
          SET row_oilStationPrice = REPLACE(row_oilStationPrice, @price_95_str,
                                            @price_95_new_str);
        END IF;

        -- 更新 98#汽油 价格
        IF price_98 IS NOT NULL
        THEN
          SET row_oilStationPrice = JSON_REPLACE(
              row_oilStationPrice,
              SUBSTRING_INDEX(
                  REPLACE(JSON_SEARCH(row_oilStationPrice, 'all', '98'), '"',
                          ''), '.', 1),
              JSON_EXTRACT(newOilStationPrice,
                           SUBSTRING_INDEX(REPLACE(
                                               JSON_SEARCH(newOilStationPrice,
                                                           'all', '98'), '"',
                                               ''), '.', 1))
          );
          -- 随机 98#汽油 油价
          SET @price_98_new = 0;
          SET @randNum = (SELECT round(RAND()*0.1, 2));
          IF @randNum > 0.05
          THEN
            SET @price_98_new = round((price_98 - @randNum), 2);
          ELSE
            SET @price_98_new = round((price_98 + @randNum), 2);
          END IF;
          SET @price_98_str = concat(price_98, "");
          SET @price_98_new_str = concat(@price_98_new, "");
          SET row_oilStationPrice = REPLACE(row_oilStationPrice, @price_98_str,
                                            @price_98_new_str);
        END IF;

#         SELECT
#           price_0,
#           @price_0_new,
#           price_92,
#           @price_92_new,
#           price_95,
#           @price_95_new,
#           price_98,
#           @price_98_new,
#           updateNum,
#           row_oilStationPrice;

        -- 更新 油价
        UPDATE o_oil_station
        SET
          oil_station_price = row_oilStationPrice,
          update_time       = CURRENT_TIMESTAMP
        WHERE id = row_id and id not in (320124);

      END IF;
    UNTIL done END REPEAT; -- 结束循环
    CLOSE cur; -- 这个语句关闭先前打开的光标。

    -- 返回值
    SELECT count(id)
    INTO updateNum
    FROM o_oil_station
    WHERE oil_station_adress LIKE concat('%', province, '%');
    SELECT updateNum;
  END
 ;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
