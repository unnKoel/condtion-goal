/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : goal

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2015-01-15 20:23:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `condition`
-- ----------------------------
DROP TABLE IF EXISTS `condition`;
CREATE TABLE `condition` (
  `conditionId` int(11) NOT NULL COMMENT '条件类别Id',
  `eventNo` varchar(8) NOT NULL COMMENT '关联事件号',
  `name` varchar(64) NOT NULL COMMENT '条件中文名',
  `identifyParams` varchar(1024) NOT NULL COMMENT '条件标识参数，通过参数标识出条件',
  `updateTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY  (`conditionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='条件';

-- ----------------------------
-- Records of condition
-- ----------------------------
