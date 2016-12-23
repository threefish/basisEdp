/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : basisedp

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2016-12-23 17:38:14
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
INSERT INTO `sys_alarm_option` VALUES ('CPU', '100', '1,2,', null);
INSERT INTO `sys_alarm_option` VALUES ('DISK', '80', '2,', null);
INSERT INTO `sys_alarm_option` VALUES ('JVM', '80', '1,2,', null);
INSERT INTO `sys_alarm_option` VALUES ('NetWork', '80', '1,', null);
INSERT INTO `sys_alarm_option` VALUES ('RAM', '80', '1,2,', null);
INSERT INTO `sys_alarm_option` VALUES ('SWAP', '80', '2,', null);

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
) ENGINE=InnoDB AUTO_INCREMENT=7756 DEFAULT CHARSET=utf8mb4;

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
  `menu_icon` varchar(50) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `menu_type` int(11) DEFAULT NULL,
  `short_no` int(11) DEFAULT '0',
  `description` varchar(100) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '运维监控', '', 'fa-television', '\0', '0', '0', '服务器监控', null, '2016-12-22 14:44:20');
INSERT INTO `sys_menu` VALUES ('2', '1', '主机性能监控', '/monitor/apm/dashboard', 'fa-dashboard', '\0', '0', '0', '主机性能监控', null, '2016-12-22 15:25:13');
INSERT INTO `sys_menu` VALUES ('3', '1', '服务监控', '', 'fa-eye', '\0', '0', '3', '服务器监控', null, '2016-12-22 17:14:21');
INSERT INTO `sys_menu` VALUES ('5', '8', '用户管理', '/userAccount/manager', 'fa-user', '\0', '0', '7', '消息类型', null, '2016-12-22 14:20:33');
INSERT INTO `sys_menu` VALUES ('6', '8', '角色管理', '/userRole/inde', 'fa-lock', '\0', '0', '8', '角色管理', null, '2016-12-22 14:45:02');
INSERT INTO `sys_menu` VALUES ('8', '0', '系统控制', '', 'fa-cogs', '\0', '0', '1', '新闻类型', null, '2016-12-22 14:44:44');
INSERT INTO `sys_menu` VALUES ('12', '8', '菜单管理', '/sysMenu/index', 'fa-th-list', '\0', '0', '5', '菜单管理', null, '2016-12-22 13:33:40');
INSERT INTO `sys_menu` VALUES ('14', '8', '组织机构管理', '/org/index', 'fa-users', '\0', '0', '6', '组织机构管理', null, '2016-12-22 14:20:19');
INSERT INTO `sys_menu` VALUES ('15', '1', '定时任务管理', '/admin/jobs', 'fa-tasks', '\0', '1', '2', '任务监控', '2016-11-25 17:14:47', '2016-12-22 14:47:16');
INSERT INTO `sys_menu` VALUES ('16', '1', 'Druid 监控', '/monitor/db/dashboard', 'fa-hdd-o', '\0', '1', '1', '', '2016-12-08 10:35:13', '2016-12-23 15:18:46');
INSERT INTO `sys_menu` VALUES ('18', '26', '测试1', '', 'fa-th-large', '\0', '1', '0', '', '2016-12-21 13:44:52', '2016-12-22 17:15:42');
INSERT INTO `sys_menu` VALUES ('19', '26', '测试2', '', '', '\0', '1', '0', '', '2016-12-21 13:44:57', '2016-12-22 17:15:55');
INSERT INTO `sys_menu` VALUES ('20', '21', '测试3', '', 'fa-th-large', '\0', '1', '0', '', '2016-12-21 13:45:03', '2016-12-22 17:16:17');
INSERT INTO `sys_menu` VALUES ('21', '18', '测试1.1', '', 'fa-film', '\0', '1', '0', '', '2016-12-21 14:47:35', '2016-12-22 14:50:44');
INSERT INTO `sys_menu` VALUES ('22', '18', '测试1.2', '', null, '\0', '1', '1', '', '2016-12-22 14:23:30', null);
INSERT INTO `sys_menu` VALUES ('23', '18', '测试1.3', '', null, '\0', '1', '2', '', '2016-12-22 14:23:37', null);
INSERT INTO `sys_menu` VALUES ('24', '18', '测试1.4', '', null, '\0', '1', '3', '', '2016-12-22 14:23:43', null);
INSERT INTO `sys_menu` VALUES ('25', '18', '测试1.5', '', null, '\0', '1', '4', '', '2016-12-22 14:23:49', null);
INSERT INTO `sys_menu` VALUES ('26', '0', '测试菜单', '', 'fa-glass', '\0', '1', '2', '', '2016-12-22 17:15:12', '2016-12-22 17:15:31');

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
INSERT INTO `sys_quartzjob` VALUES ('2', 'APM任务', 'com.sgaop.task.ApmJob', '*/5 * * * * ?', '系统默认任务', '1', 'DEFAULT', 'NORMAL', '0', '6da64b5bd2ee-74a93f7d-0679-410d-8925-ac361c165253');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_useraccount
-- ----------------------------
INSERT INTO `sys_useraccount` VALUES ('1', 'admin', 'f316dd0616a0a35c7298c8d2638269f98e32de74bc304928ce7bcd9b4cbc6914', '\0', '2016-11-09 15:23:12', 'd533301091f141cfabb4034886fe10f2');
INSERT INTO `sys_useraccount` VALUES ('2', 'test', '54347e23ec893f95a7ecd20d4d364db7269f5ce8d3dea155068533d53fe3380e', '\0', '2016-11-24 17:38:13', '8dd2c68e00df47bf9004b54923fcddbe');
INSERT INTO `sys_useraccount` VALUES ('3', '123', '9fdb97ac315249213c606d156ab5930615cf8841752e900bda43868e2ad10ca4', '\0', '2016-12-16 16:56:57', '2bc5d58736c941e7a636e0046194783b');
INSERT INTO `sys_useraccount` VALUES ('4', '呵呵', 'be73df764d9ca804259dc44bdab37afb8b3a7390d475dd9bcaf0e18f11db4290', '', '2016-12-16 16:57:19', '0c8c7f5b0904413497cdf68b181d3ab5');

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
