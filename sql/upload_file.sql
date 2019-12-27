/*
 Navicat Premium Data Transfer

 Source Server         : localhost1
 Source Server Type    : MySQL
 Source Server Version : 80017
 Source Host           : localhost:3306
 Source Schema         : prihealth

 Target Server Type    : MySQL
 Target Server Version : 80017
 File Encoding         : 65001

 Date: 10/12/2019 13:23:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for upload_file
-- ----------------------------
DROP TABLE IF EXISTS `upload_file`;
CREATE TABLE `upload_file` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `display_name` varchar(200) DEFAULT NULL COMMENT '文件展示名称',
  `file_url` varchar(200) DEFAULT NULL COMMENT '文件相对路径',
  `related_key` varchar(50) DEFAULT NULL COMMENT '关联的业务ID',
  `file_type` tinyint(4) DEFAULT NULL COMMENT '1图片 2普通文件',
  `file_from` tinyint(2) DEFAULT '1' COMMENT '文件来源，1:上传(属于自己的文件) 	2:引用其它订单的附件(非属于自己的文件)，默认为1',
  `use_type` tinyint(2) DEFAULT NULL COMMENT '文件用途类型，选项为：4、评估报告的订单，其它类型的值可能暂时为空，因为默认一个订单就对应一种附件',
  `create_user` varchar(50) DEFAULT NULL COMMENT '创建人',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(50) DEFAULT NULL COMMENT '更新人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2654968 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
