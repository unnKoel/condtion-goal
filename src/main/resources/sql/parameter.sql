/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : goal

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2015-01-15 20:24:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `parameter`
-- ----------------------------
DROP TABLE IF EXISTS `parameter`;
CREATE TABLE `parameter` (
  `name` varchar(32) NOT NULL COMMENT '参数名',
  `chinaName` varchar(16) default NULL COMMENT '中文名',
  `type` char(1) NOT NULL COMMENT '参数类型(1:int, 2:string,3:boolean 4:query)',
  `operate` varchar(2) NOT NULL COMMENT '比较类型（>,=,<,>=,<=）',
  `change` char(1) NOT NULL COMMENT '变化类型（1：递增 2：替换）',
  `relativeParams` varchar(1024) default NULL COMMENT '关联参数',
  `updateTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY  (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='参数';

-- ----------------------------
-- Records of parameter
-- ----------------------------
