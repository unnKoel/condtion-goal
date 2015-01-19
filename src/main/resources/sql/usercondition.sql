/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : goal

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2015-01-15 20:24:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `usercondition`
-- ----------------------------
DROP TABLE IF EXISTS `usercondition`;
CREATE TABLE `usercondition` (
  `userId` int(11) NOT NULL COMMENT '用户Id',
  `conditionId` int(11) NOT NULL COMMENT '条件Id',
  `conditionValue` varchar(16) NOT NULL COMMENT '条件值',
  PRIMARY KEY  (`userId`,`conditionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户条件';

-- ----------------------------
-- Records of usercondition
-- ----------------------------
