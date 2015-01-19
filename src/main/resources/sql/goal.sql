/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : goal

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2015-01-15 20:24:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `goal`
-- ----------------------------
DROP TABLE IF EXISTS `goal`;
CREATE TABLE `goal` (
  `gkey` varchar(16) NOT NULL COMMENT '目标key',
  `conditionId` int(11) NOT NULL COMMENT '条件Id',
  `gname` varchar(64) NOT NULL COMMENT '目标名称',
  `award` int(11) default NULL COMMENT '目标奖励',
  `updateTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY  (`gkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='目标';

-- ----------------------------
-- Records of goal
-- ----------------------------
