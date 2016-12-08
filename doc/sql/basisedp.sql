/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : basisedp

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2016-12-08 17:36:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_alarm_option
-- ----------------------------
DROP TABLE IF EXISTS `sys_alarm_option`;
CREATE TABLE `sys_alarm_option` (
  `alarmType` varchar(10) NOT NULL,
  `percent` double DEFAULT NULL,
  `listenerTypes` varchar(100) DEFAULT NULL,
  `listeners` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`alarmType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_alarm_option
-- ----------------------------
INSERT INTO `sys_alarm_option` VALUES ('CPU', '60', '1,2,', '123');
INSERT INTO `sys_alarm_option` VALUES ('DISK', '80', '2,', '123');
INSERT INTO `sys_alarm_option` VALUES ('JVM', '80', '1,2,', '123');
INSERT INTO `sys_alarm_option` VALUES ('NetWork', '80', '1,', '123');
INSERT INTO `sys_alarm_option` VALUES ('RAM', '80', '1,2,', '123');
INSERT INTO `sys_alarm_option` VALUES ('SWAP', '80', '2,', '123');

-- ----------------------------
-- Table structure for sys_apm_alarm
-- ----------------------------
DROP TABLE IF EXISTS `sys_apm_alarm`;
CREATE TABLE `sys_apm_alarm` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `alarmType` varchar(255) DEFAULT NULL,
  `alarmTime` datetime DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `device` varchar(255) DEFAULT NULL,
  `alarmUsage` double DEFAULT NULL,
  `point` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=915 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_apm_alarm
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `menu_name` varchar(100) DEFAULT NULL,
  `menu_target` varchar(100) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `menu_type` int(11) DEFAULT NULL,
  `short_no` int(11) DEFAULT '0',
  `description` varchar(100) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '运维监控', '', '\0', '0', '1', '服务器监控', null, '2016-12-08 10:33:11');
INSERT INTO `sys_menu` VALUES ('2', '1', '主机性能监控', '/monitor/apm/index', '\0', '0', '2', '主机性能监控', null, null);
INSERT INTO `sys_menu` VALUES ('3', '1', '服务监控', '/druid', '\0', '0', '3', '服务器监控', null, null);
INSERT INTO `sys_menu` VALUES ('5', '8', '用户管理', '/userAccount/manager', '\0', '0', '7', '消息类型', null, null);
INSERT INTO `sys_menu` VALUES ('6', '8', '角色管理', '/userRole/inde', '\0', '0', '8', '角色管理', null, '2016-11-22 18:34:38');
INSERT INTO `sys_menu` VALUES ('8', '0', '系统控制', '', '\0', '0', '4', '新闻类型', null, null);
INSERT INTO `sys_menu` VALUES ('12', '8', '菜单管理', '/sysMenu/index', '\0', '0', '5', '菜单管理', null, null);
INSERT INTO `sys_menu` VALUES ('14', '8', '组织机构管理', '/org/index', '\0', '0', '6', '组织机构管理', null, '2016-11-20 14:55:49');
INSERT INTO `sys_menu` VALUES ('15', '1', '定时任务管理', '/admin/jobs', '\0', '1', '0', '任务监控', '2016-11-25 17:14:47', '2016-12-08 10:34:52');
INSERT INTO `sys_menu` VALUES ('16', '1', '数据库状态监控', '', '\0', '1', '0', '', '2016-12-08 10:35:13', null);

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', 'main.index:logout');
INSERT INTO `sys_permission` VALUES ('2', 'main/index:index');
INSERT INTO `sys_permission` VALUES ('3', 'product.edit');
INSERT INTO `sys_permission` VALUES ('4', 'product.delete');
INSERT INTO `sys_permission` VALUES ('5', 'space');

-- ----------------------------
-- Table structure for sys_quartzjob
-- ----------------------------
DROP TABLE IF EXISTS `sys_quartzjob`;
CREATE TABLE `sys_quartzjob` (
  `id` int(11) NOT NULL,
  `job_name` varchar(50) DEFAULT NULL,
  `job_klass` varchar(150) DEFAULT NULL,
  `job_corn` varchar(50) DEFAULT NULL,
  `job_desc` varchar(150) DEFAULT NULL,
  `job_short` int(8) DEFAULT NULL,
  `job_group` varchar(50) DEFAULT NULL,
  `job_status` varchar(50) DEFAULT NULL,
  `job_type` int(8) DEFAULT NULL,
  `job_run_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_quartzjob
-- ----------------------------
INSERT INTO `sys_quartzjob` VALUES ('1', '测试任务', 'com.sgaop.task.TestJob', '*/5 * * * * ?', '系统默认任务', '1', 'DEFAULT', 'NONE', '1', '6da64b5bd2ee-705ea7b7-1054-420a-9538-56bc518bbaac');
INSERT INTO `sys_quartzjob` VALUES ('2', 'APM任务', 'com.sgaop.task.ApmJob', '*/5 * * * * ?', '系统默认任务', '1', 'DEFAULT', 'NORMAL', '0', '6da64b5bd2ee-c9145a30-8f07-4af7-929f-dfbf2779be62');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员');
INSERT INTO `sys_role` VALUES ('2', '普通用户');

-- ----------------------------
-- Table structure for sys_rolepermission
-- ----------------------------
DROP TABLE IF EXISTS `sys_rolepermission`;
CREATE TABLE `sys_rolepermission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL,
  `permission_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_rolepermission
-- ----------------------------
INSERT INTO `sys_rolepermission` VALUES ('1', '1', '1');
INSERT INTO `sys_rolepermission` VALUES ('2', '1', '2');
INSERT INTO `sys_rolepermission` VALUES ('3', '1', '3');
INSERT INTO `sys_rolepermission` VALUES ('4', '1', '4');
INSERT INTO `sys_rolepermission` VALUES ('5', '2', '1');
INSERT INTO `sys_rolepermission` VALUES ('6', '1', '5');

-- ----------------------------
-- Table structure for sys_useraccount
-- ----------------------------
DROP TABLE IF EXISTS `sys_useraccount`;
CREATE TABLE `sys_useraccount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(50) DEFAULT NULL,
  `userPass` varchar(500) DEFAULT NULL,
  `locked` bit(1) DEFAULT b'0',
  `createTime` datetime DEFAULT NULL,
  `salt` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_useraccount
-- ----------------------------
INSERT INTO `sys_useraccount` VALUES ('1', 'admin', 'e78f0e47afe68f563c314097f30e7845e9a96e4e5cda5ea4d82c14150b0cc577', '\0', '2016-11-09 15:23:12', '334ee3a373164b3f872ee16694bf1c77');
INSERT INTO `sys_useraccount` VALUES ('2', 'test', 'e78f0e47afe68f563c314097f30e7845e9a96e4e5cda5ea4d82c14150b0cc577', '\0', '2016-11-24 17:38:13', '334ee3a373164b3f872ee16694bf1c77');

-- ----------------------------
-- Table structure for sys_useraccountrole
-- ----------------------------
DROP TABLE IF EXISTS `sys_useraccountrole`;
CREATE TABLE `sys_useraccountrole` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_useraccountrole
-- ----------------------------
INSERT INTO `sys_useraccountrole` VALUES ('1', '1', '1');
INSERT INTO `sys_useraccountrole` VALUES ('2', '2', '2');
INSERT INTO `sys_useraccountrole` VALUES ('3', '1', '2');

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `uid` int(11) NOT NULL,
  `nickName` varchar(255) NOT NULL COMMENT '昵称',
  `realName` varchar(255) DEFAULT NULL COMMENT '真实姓名',
  `age` int(3) DEFAULT NULL,
  `sex` int(1) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `email` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `qq` varchar(255) DEFAULT NULL,
  `sign` varchar(255) DEFAULT NULL COMMENT '签名',
  `adderess` varchar(255) DEFAULT NULL COMMENT '地址',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `score` int(11) DEFAULT NULL COMMENT '积分',
  `last_login_ip` varchar(50) DEFAULT NULL COMMENT '最后登录IP',
  `last_login_time` date DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`uid`),
  CONSTRAINT `pk_userid` FOREIGN KEY (`uid`) REFERENCES `sys_useraccount` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
