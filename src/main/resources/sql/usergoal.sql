/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50087
Source Host           : localhost:3306
Source Database       : goal

Target Server Type    : MYSQL
Target Server Version : 50087
File Encoding         : 65001

Date: 2015-01-15 20:24:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `usergoal`
-- ----------------------------
DROP TABLE IF EXISTS `usergoal`;
CREATE TABLE `usergoal` (
  `id` int(11) NOT NULL COMMENT '目标Id',
  `gkey` varchar(16) NOT NULL COMMENT '目标key',
  `gstatus` char(1) default NULL COMMENT '目标状态(0:条件不足，1:领取，2:已领取)',
  `touchTime` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP COMMENT '目标完成时间',
  `getTime` timestamp NOT NULL default '0000-00-00 00:00:00' COMMENT '目标奖励领取时间',
  PRIMARY KEY  (`id`,`gkey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户目标';

-- ----------------------------
-- Records of usergoal
-- ----------------------------
