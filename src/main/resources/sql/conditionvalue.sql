/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : goal

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2015-01-15 20:23:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `conditionvalue`
-- ----------------------------
DROP TABLE IF EXISTS `conditionvalue`;
CREATE TABLE `conditionvalue` (
  `valueId` int(11) NOT NULL COMMENT '条件值Id',
  `conditionId` int(11) NOT NULL COMMENT '条件Id',
  `requireDesc` varchar(512) default NULL COMMENT '条件描述',
  `requireValue` varchar(64) default NULL COMMENT '条件满足值',
  `updateTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY  (`valueId`,`conditionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='条件值';

-- ----------------------------
-- Records of conditionvalue
-- ----------------------------
