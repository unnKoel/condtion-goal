/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : goal

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2015-01-15 20:23:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `event`
-- ----------------------------
DROP TABLE IF EXISTS `event`;
CREATE TABLE `event` (
  `eventNo` char(8) NOT NULL COMMENT '事件号',
  `eventName` varchar(16) NOT NULL COMMENT '事件名称',
  `eventExplain` varchar(1024) NOT NULL COMMENT '事件说明',
  `paramters` varchar(1024) NOT NULL COMMENT '参数列表',
  `updateTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY  (`eventNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='事件';

-- ----------------------------
-- Records of event
-- ----------------------------
