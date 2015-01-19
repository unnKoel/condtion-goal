/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : goal

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2015-01-15 20:24:08
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `requestevent`
-- ----------------------------
DROP TABLE IF EXISTS `requestevent`;
CREATE TABLE `requestevent` (
  `url` varchar(128) NOT NULL COMMENT '请求URL',
  `eventNo` char(8) NOT NULL COMMENT '触发的事件',
  `paramName` varchar(16) NOT NULL COMMENT '参数名称',
  `paramValue` varchar(16) NOT NULL COMMENT '参数值',
  `requestFlag` char(1) NOT NULL COMMENT '是否为请求参数',
  PRIMARY KEY  (`url`,`eventNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='请求事件';

-- ----------------------------
-- Records of requestevent
-- ----------------------------
