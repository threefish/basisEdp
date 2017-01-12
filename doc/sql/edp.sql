/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : edp

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2017-01-12 16:25:19
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
) ENGINE=InnoDB AUTO_INCREMENT=8122 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_apm_alarm
-- ----------------------------
INSERT INTO `sys_apm_alarm` VALUES ('7887', 'NetWork', '2016-12-29 17:55:45', '流量警告:当前 NetWork 使用率 1914.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1914', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7888', 'NetWork', '2016-12-29 17:59:25', '流量警告:当前 NetWork 使用率 305.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '305', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7889', 'NetWork', '2016-12-29 17:59:30', '流量警告:当前 NetWork 使用率 2057.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2057', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7890', 'NetWork', '2016-12-29 18:04:00', '流量警告:当前 NetWork 使用率 658.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '658', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7891', 'NetWork', '2016-12-29 18:04:05', '流量警告:当前 NetWork 使用率 1701.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1701', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7892', 'NetWork', '2016-12-29 18:07:40', '流量警告:当前 NetWork 使用率 2010.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2010', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7893', 'NetWork', '2016-12-29 18:11:35', '流量警告:当前 NetWork 使用率 1546.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1546', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7894', 'NetWork', '2016-12-29 18:14:35', '流量警告:当前 NetWork 使用率 2605.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2605', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7895', 'NetWork', '2016-12-29 18:15:40', '流量警告:当前 NetWork 使用率 1004.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1004', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7896', 'NetWork', '2016-12-29 18:20:35', '流量警告:当前 NetWork 使用率 2386.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2386', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7897', 'NetWork', '2016-12-29 18:22:15', '流量警告:当前 NetWork 使用率 1089.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1089', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7898', 'NetWork', '2016-12-29 18:59:40', '流量警告:当前 NetWork 使用率 2383.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2383', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7899', 'NetWork', '2016-12-29 19:07:10', '流量警告:当前 NetWork 使用率 1681.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1681', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7900', 'NetWork', '2016-12-29 19:31:55', '流量警告:当前 NetWork 使用率 1199.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1199', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7901', 'NetWork', '2016-12-30 09:45:10', '流量警告:当前 NetWork 使用率 211.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '211', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7902', 'NetWork', '2016-12-30 09:45:20', '流量警告:当前 NetWork 使用率 114.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '114', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7903', 'NetWork', '2016-12-30 09:47:35', '流量警告:当前 NetWork 使用率 179.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '179', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7904', 'NetWork', '2016-12-30 09:49:35', '流量警告:当前 NetWork 使用率 339.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '339', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7905', 'NetWork', '2016-12-30 09:49:40', '流量警告:当前 NetWork 使用率 421.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '421', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7906', 'NetWork', '2016-12-30 09:49:55', '流量警告:当前 NetWork 使用率 621.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '621', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7907', 'NetWork', '2016-12-30 09:51:10', '流量警告:当前 NetWork 使用率 82.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '82', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7908', 'NetWork', '2016-12-30 09:52:40', '流量警告:当前 NetWork 使用率 185.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '185', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7909', 'NetWork', '2016-12-30 10:26:50', '流量警告:当前 NetWork 使用率 584.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '584', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7910', 'NetWork', '2016-12-30 10:26:55', '流量警告:当前 NetWork 使用率 1163.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1163', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7911', 'NetWork', '2016-12-30 10:32:15', '流量警告:当前 NetWork 使用率 81.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '81', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7912', 'NetWork', '2016-12-30 10:34:45', '流量警告:当前 NetWork 使用率 409.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '409', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7913', 'NetWork', '2016-12-30 10:34:55', '流量警告:当前 NetWork 使用率 565.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '565', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7914', 'NetWork', '2016-12-30 10:35:00', '流量警告:当前 NetWork 使用率 134.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '134', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7915', 'NetWork', '2016-12-30 10:35:05', '流量警告:当前 NetWork 使用率 453.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '453', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7916', 'NetWork', '2016-12-30 10:35:10', '流量警告:当前 NetWork 使用率 88.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '88', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7917', 'NetWork', '2016-12-30 10:35:20', '流量警告:当前 NetWork 使用率 81.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '81', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7918', 'NetWork', '2016-12-30 10:36:50', '流量警告:当前 NetWork 使用率 750.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '750', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7919', 'NetWork', '2016-12-30 10:37:00', '流量警告:当前 NetWork 使用率 1442.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1442', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7920', 'NetWork', '2016-12-30 10:37:05', '流量警告:当前 NetWork 使用率 1487.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1487', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7921', 'NetWork', '2016-12-30 10:37:15', '流量警告:当前 NetWork 使用率 1522.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1522', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7922', 'NetWork', '2016-12-30 10:37:20', '流量警告:当前 NetWork 使用率 790.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '790', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7923', 'NetWork', '2016-12-30 10:37:30', '流量警告:当前 NetWork 使用率 1112.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1112', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7924', 'NetWork', '2016-12-30 10:37:55', '流量警告:当前 NetWork 使用率 900.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '900', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7925', 'NetWork', '2016-12-30 10:38:05', '流量警告:当前 NetWork 使用率 177.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '177', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7926', 'NetWork', '2016-12-30 10:38:25', '流量警告:当前 NetWork 使用率 918.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '918', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7927', 'NetWork', '2016-12-30 10:38:35', '流量警告:当前 NetWork 使用率 900.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '900', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7928', 'NetWork', '2016-12-30 10:38:45', '流量警告:当前 NetWork 使用率 285.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '285', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7929', 'NetWork', '2016-12-30 10:39:00', '流量警告:当前 NetWork 使用率 85.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '85', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7930', 'NetWork', '2016-12-30 10:39:05', '流量警告:当前 NetWork 使用率 137.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '137', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7931', 'NetWork', '2016-12-30 10:39:15', '流量警告:当前 NetWork 使用率 112.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '112', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7932', 'NetWork', '2016-12-30 10:39:20', '流量警告:当前 NetWork 使用率 82.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '82', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7933', 'NetWork', '2016-12-30 10:39:30', '流量警告:当前 NetWork 使用率 85.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '85', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7934', 'NetWork', '2016-12-30 10:40:15', '流量警告:当前 NetWork 使用率 91.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '91', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7935', 'NetWork', '2016-12-30 10:40:25', '流量警告:当前 NetWork 使用率 213.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '213', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7936', 'NetWork', '2016-12-30 10:40:35', '流量警告:当前 NetWork 使用率 1597.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1597', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7937', 'NetWork', '2016-12-30 10:40:40', '流量警告:当前 NetWork 使用率 273.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '273', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7938', 'NetWork', '2016-12-30 10:40:45', '流量警告:当前 NetWork 使用率 309.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '309', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7939', 'NetWork', '2016-12-30 10:41:00', '流量警告:当前 NetWork 使用率 2654.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2654', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7940', 'NetWork', '2016-12-30 10:41:05', '流量警告:当前 NetWork 使用率 1495.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1495', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7941', 'NetWork', '2016-12-30 10:41:10', '流量警告:当前 NetWork 使用率 1172.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1172', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7942', 'NetWork', '2016-12-30 10:41:20', '流量警告:当前 NetWork 使用率 1116.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1116', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7943', 'NetWork', '2016-12-30 10:41:25', '流量警告:当前 NetWork 使用率 1210.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1210', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7944', 'NetWork', '2016-12-30 10:41:35', '流量警告:当前 NetWork 使用率 781.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '781', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7945', 'NetWork', '2016-12-30 10:41:40', '流量警告:当前 NetWork 使用率 1049.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1049', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7946', 'NetWork', '2016-12-30 10:41:50', '流量警告:当前 NetWork 使用率 1186.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1186', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7947', 'NetWork', '2016-12-30 10:41:55', '流量警告:当前 NetWork 使用率 1068.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1068', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7948', 'NetWork', '2016-12-30 10:42:00', '流量警告:当前 NetWork 使用率 1126.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1126', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7949', 'NetWork', '2016-12-30 10:42:05', '流量警告:当前 NetWork 使用率 578.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '578', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7950', 'NetWork', '2016-12-30 10:43:15', '流量警告:当前 NetWork 使用率 98.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '98', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7951', 'NetWork', '2016-12-30 10:43:30', '流量警告:当前 NetWork 使用率 99.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '99', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7952', 'NetWork', '2016-12-30 10:43:40', '流量警告:当前 NetWork 使用率 99.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '99', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7953', 'NetWork', '2016-12-30 10:43:45', '流量警告:当前 NetWork 使用率 200.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '200', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7954', 'NetWork', '2016-12-30 10:43:50', '流量警告:当前 NetWork 使用率 197.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '197', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7955', 'NetWork', '2016-12-30 11:10:15', '流量警告:当前 NetWork 使用率 1368.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1368', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7956', 'NetWork', '2016-12-30 11:27:00', '流量警告:当前 NetWork 使用率 187.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '187', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7957', 'NetWork', '2016-12-30 11:27:05', '流量警告:当前 NetWork 使用率 1193.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1193', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7958', 'NetWork', '2016-12-30 11:30:00', '流量警告:当前 NetWork 使用率 81.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '81', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7959', 'NetWork', '2016-12-30 11:32:10', '流量警告:当前 NetWork 使用率 134.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '134', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7960', 'NetWork', '2016-12-30 11:32:15', '流量警告:当前 NetWork 使用率 108.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '108', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7961', 'NetWork', '2016-12-30 11:37:35', '流量警告:当前 NetWork 使用率 112.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '112', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7962', 'NetWork', '2016-12-30 12:38:25', '流量警告:当前 NetWork 使用率 85.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '85', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7963', 'NetWork', '2016-12-30 12:41:35', '流量警告:当前 NetWork 使用率 194.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '194', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7964', 'NetWork', '2016-12-30 12:42:15', '流量警告:当前 NetWork 使用率 273.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '273', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7965', 'NetWork', '2016-12-30 12:42:40', '流量警告:当前 NetWork 使用率 311.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '311', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7966', 'NetWork', '2016-12-30 12:43:05', '流量警告:当前 NetWork 使用率 119.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '119', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7967', 'NetWork', '2016-12-30 13:00:25', '流量警告:当前 NetWork 使用率 109.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '109', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7968', 'NetWork', '2016-12-30 13:16:05', '流量警告:当前 NetWork 使用率 88.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '88', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7969', 'NetWork', '2016-12-30 13:33:00', '流量警告:当前 NetWork 使用率 950.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '950', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7970', 'NetWork', '2016-12-30 13:33:05', '流量警告:当前 NetWork 使用率 200.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '200', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7971', 'NetWork', '2016-12-30 13:33:10', '流量警告:当前 NetWork 使用率 87.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '87', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7972', 'NetWork', '2016-12-30 13:33:15', '流量警告:当前 NetWork 使用率 131.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '131', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7973', 'NetWork', '2016-12-30 13:54:40', '流量警告:当前 NetWork 使用率 132.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '132', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7974', 'NetWork', '2016-12-30 13:54:45', '流量警告:当前 NetWork 使用率 139.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '139', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7975', 'NetWork', '2016-12-30 13:54:55', '流量警告:当前 NetWork 使用率 2133.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2133', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7976', 'NetWork', '2016-12-30 13:55:50', '流量警告:当前 NetWork 使用率 1598.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1598', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7977', 'NetWork', '2016-12-30 13:56:05', '流量警告:当前 NetWork 使用率 2052.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2052', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7978', 'NetWork', '2016-12-30 14:00:00', '流量警告:当前 NetWork 使用率 2289.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2289', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7979', 'NetWork', '2016-12-30 14:00:00', '流量警告:当前 NetWork 使用率 83.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '83', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7980', 'NetWork', '2016-12-30 14:04:20', '流量警告:当前 NetWork 使用率 222.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '222', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7981', 'NetWork', '2016-12-30 14:04:25', '流量警告:当前 NetWork 使用率 2256.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2256', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7982', 'NetWork', '2016-12-30 14:07:20', '流量警告:当前 NetWork 使用率 1126.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1126', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7983', 'NetWork', '2016-12-30 14:07:25', '流量警告:当前 NetWork 使用率 468.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '468', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7984', 'NetWork', '2016-12-30 14:14:00', '流量警告:当前 NetWork 使用率 2163.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2163', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7985', 'NetWork', '2016-12-30 14:18:05', '流量警告:当前 NetWork 使用率 2481.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2481', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7986', 'NetWork', '2016-12-30 14:19:05', '流量警告:当前 NetWork 使用率 776.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '776', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7987', 'NetWork', '2016-12-30 14:39:00', '流量警告:当前 NetWork 使用率 2462.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2462', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7988', 'NetWork', '2016-12-30 14:43:45', '流量警告:当前 NetWork 使用率 2089.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2089', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7989', 'NetWork', '2016-12-30 14:43:45', '流量警告:当前 NetWork 使用率 88.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '88', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7990', 'NetWork', '2016-12-30 14:50:50', '流量警告:当前 NetWork 使用率 2139.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2139', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7991', 'NetWork', '2016-12-30 14:50:50', '流量警告:当前 NetWork 使用率 94.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '94', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7992', 'NetWork', '2016-12-30 15:40:25', '流量警告:当前 NetWork 使用率 953.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '953', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7993', 'NetWork', '2016-12-30 15:42:10', '流量警告:当前 NetWork 使用率 134.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '134', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7994', 'NetWork', '2016-12-30 15:45:00', '流量警告:当前 NetWork 使用率 882.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '882', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7995', 'NetWork', '2016-12-30 15:49:15', '流量警告:当前 NetWork 使用率 1218.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1218', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7996', 'NetWork', '2016-12-30 15:51:35', '流量警告:当前 NetWork 使用率 1028.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1028', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7997', 'NetWork', '2016-12-30 16:01:05', '流量警告:当前 NetWork 使用率 765.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '765', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7998', 'NetWork', '2016-12-30 16:01:10', '流量警告:当前 NetWork 使用率 1986.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1986', '80');
INSERT INTO `sys_apm_alarm` VALUES ('7999', 'NetWork', '2016-12-30 16:03:55', '流量警告:当前 NetWork 使用率 802.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '802', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8000', 'NetWork', '2016-12-30 16:04:00', '流量警告:当前 NetWork 使用率 972.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '972', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8001', 'NetWork', '2016-12-30 16:04:05', '流量警告:当前 NetWork 使用率 1129.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1129', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8002', 'NetWork', '2016-12-30 16:04:10', '流量警告:当前 NetWork 使用率 771.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '771', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8003', 'NetWork', '2016-12-30 16:04:15', '流量警告:当前 NetWork 使用率 978.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '978', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8004', 'NetWork', '2016-12-30 16:04:20', '流量警告:当前 NetWork 使用率 1199.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1199', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8005', 'NetWork', '2016-12-30 16:04:25', '流量警告:当前 NetWork 使用率 623.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '623', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8006', 'NetWork', '2016-12-30 16:04:30', '流量警告:当前 NetWork 使用率 761.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '761', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8007', 'NetWork', '2016-12-30 16:04:35', '流量警告:当前 NetWork 使用率 470.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '470', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8008', 'NetWork', '2016-12-30 16:06:30', '流量警告:当前 NetWork 使用率 1877.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1877', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8009', 'NetWork', '2016-12-30 16:10:10', '流量警告:当前 NetWork 使用率 2188.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2188', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8010', 'NetWork', '2016-12-30 16:10:10', '流量警告:当前 NetWork 使用率 84.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '84', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8011', 'NetWork', '2016-12-30 16:10:45', '流量警告:当前 NetWork 使用率 176.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '176', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8012', 'NetWork', '2016-12-30 16:14:25', '流量警告:当前 NetWork 使用率 2002.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2002', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8013', 'NetWork', '2016-12-30 16:18:20', '流量警告:当前 NetWork 使用率 930.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '930', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8014', 'NetWork', '2016-12-30 16:18:25', '流量警告:当前 NetWork 使用率 2041.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2041', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8015', 'NetWork', '2016-12-30 16:18:55', '流量警告:当前 NetWork 使用率 218.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '218', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8016', 'NetWork', '2016-12-30 16:19:45', '流量警告:当前 NetWork 使用率 194.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '194', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8017', 'NetWork', '2016-12-30 16:21:40', '流量警告:当前 NetWork 使用率 646.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '646', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8018', 'NetWork', '2016-12-30 16:21:45', '流量警告:当前 NetWork 使用率 651.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '651', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8019', 'NetWork', '2016-12-30 16:24:10', '流量警告:当前 NetWork 使用率 1805.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1805', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8020', 'NetWork', '2016-12-30 16:25:25', '流量警告:当前 NetWork 使用率 94.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '94', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8021', 'NetWork', '2016-12-30 16:25:30', '流量警告:当前 NetWork 使用率 186.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '186', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8022', 'NetWork', '2016-12-30 16:26:45', '流量警告:当前 NetWork 使用率 125.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '125', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8023', 'NetWork', '2016-12-30 16:26:50', '流量警告:当前 NetWork 使用率 170.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '170', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8024', 'NetWork', '2016-12-30 16:27:20', '流量警告:当前 NetWork 使用率 132.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '132', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8025', 'NetWork', '2016-12-30 16:27:25', '流量警告:当前 NetWork 使用率 468.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '468', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8026', 'NetWork', '2016-12-30 16:27:40', '流量警告:当前 NetWork 使用率 1832.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1832', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8027', 'NetWork', '2016-12-30 16:28:20', '流量警告:当前 NetWork 使用率 537.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '537', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8028', 'NetWork', '2016-12-30 16:29:15', '流量警告:当前 NetWork 使用率 153.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '153', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8029', 'NetWork', '2016-12-30 16:29:25', '流量警告:当前 NetWork 使用率 578.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '578', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8030', 'NetWork', '2016-12-30 16:30:20', '流量警告:当前 NetWork 使用率 127.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '127', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8031', 'NetWork', '2016-12-30 16:30:30', '流量警告:当前 NetWork 使用率 613.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '613', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8032', 'NetWork', '2016-12-30 16:32:00', '流量警告:当前 NetWork 使用率 97.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '97', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8033', 'NetWork', '2016-12-30 16:32:25', '流量警告:当前 NetWork 使用率 209.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '209', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8034', 'NetWork', '2016-12-30 16:32:30', '流量警告:当前 NetWork 使用率 519.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '519', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8035', 'NetWork', '2016-12-30 16:35:55', '流量警告:当前 NetWork 使用率 725.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '725', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8036', 'NetWork', '2016-12-30 16:39:05', '流量警告:当前 NetWork 使用率 180.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '180', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8037', 'NetWork', '2016-12-30 16:39:25', '流量警告:当前 NetWork 使用率 2069.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2069', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8038', 'NetWork', '2016-12-30 16:43:20', '流量警告:当前 NetWork 使用率 185.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '185', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8039', 'NetWork', '2016-12-30 16:43:25', '流量警告:当前 NetWork 使用率 1981.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1981', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8040', 'NetWork', '2016-12-30 16:43:30', '流量警告:当前 NetWork 使用率 187.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '187', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8041', 'NetWork', '2016-12-30 16:43:35', '流量警告:当前 NetWork 使用率 416.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '416', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8042', 'NetWork', '2016-12-30 16:43:40', '流量警告:当前 NetWork 使用率 185.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '185', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8043', 'NetWork', '2016-12-30 16:47:00', '流量警告:当前 NetWork 使用率 1116.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1116', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8044', 'NetWork', '2016-12-30 16:52:05', '流量警告:当前 NetWork 使用率 2530.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2530', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8045', 'NetWork', '2016-12-30 16:52:05', '流量警告:当前 NetWork 使用率 83.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '83', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8046', 'NetWork', '2016-12-30 16:57:00', '流量警告:当前 NetWork 使用率 644.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '644', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8047', 'NetWork', '2016-12-30 16:57:05', '流量警告:当前 NetWork 使用率 1048.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1048', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8048', 'NetWork', '2016-12-30 17:03:30', '流量警告:当前 NetWork 使用率 2354.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2354', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8049', 'NetWork', '2016-12-30 17:08:11', '流量警告:当前 NetWork 使用率 521.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '521', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8050', 'NetWork', '2016-12-30 17:15:30', '流量警告:当前 NetWork 使用率 1742.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1742', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8051', 'NetWork', '2016-12-30 17:22:40', '流量警告:当前 NetWork 使用率 84.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '84', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8052', 'NetWork', '2016-12-30 17:22:45', '流量警告:当前 NetWork 使用率 1620.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1620', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8053', 'NetWork', '2016-12-30 17:26:00', '流量警告:当前 NetWork 使用率 1652.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1652', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8054', 'NetWork', '2016-12-30 17:29:15', '流量警告:当前 NetWork 使用率 1037.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1037', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8055', 'NetWork', '2016-12-30 17:32:30', '流量警告:当前 NetWork 使用率 160.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '160', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8056', 'NetWork', '2016-12-30 17:32:35', '流量警告:当前 NetWork 使用率 270.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '270', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8057', 'NetWork', '2016-12-30 17:32:40', '流量警告:当前 NetWork 使用率 134.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '134', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8058', 'NetWork', '2016-12-30 17:32:45', '流量警告:当前 NetWork 使用率 120.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '120', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8059', 'NetWork', '2016-12-30 17:32:50', '流量警告:当前 NetWork 使用率 104.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '104', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8060', 'NetWork', '2016-12-30 17:32:55', '流量警告:当前 NetWork 使用率 236.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '236', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8061', 'NetWork', '2016-12-30 17:33:00', '流量警告:当前 NetWork 使用率 93.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '93', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8062', 'NetWork', '2016-12-30 17:34:00', '流量警告:当前 NetWork 使用率 2786.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2786', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8063', 'NetWork', '2016-12-30 17:34:00', '流量警告:当前 NetWork 使用率 100.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '100', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8064', 'NetWork', '2016-12-30 17:39:25', '流量警告:当前 NetWork 使用率 789.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '789', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8065', 'NetWork', '2016-12-30 17:43:15', '流量警告:当前 NetWork 使用率 1856.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1856', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8066', 'NetWork', '2016-12-30 17:46:55', '流量警告:当前 NetWork 使用率 1981.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1981', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8067', 'NetWork', '2016-12-30 17:50:45', '流量警告:当前 NetWork 使用率 1875.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1875', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8068', 'NetWork', '2016-12-30 17:59:55', '流量警告:当前 NetWork 使用率 1593.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1593', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8069', 'NetWork', '2016-12-30 18:03:00', '流量警告:当前 NetWork 使用率 1560.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1560', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8070', 'NetWork', '2016-12-30 18:10:25', '流量警告:当前 NetWork 使用率 1487.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1487', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8071', 'NetWork', '2016-12-30 18:16:55', '流量警告:当前 NetWork 使用率 2534.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2534', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8072', 'NetWork', '2016-12-30 18:16:55', '流量警告:当前 NetWork 使用率 95.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '95', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8073', 'NetWork', '2016-12-30 18:21:45', '流量警告:当前 NetWork 使用率 740.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '740', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8074', 'NetWork', '2016-12-30 18:26:00', '流量警告:当前 NetWork 使用率 2065.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2065', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8075', 'NetWork', '2016-12-30 18:30:00', '流量警告:当前 NetWork 使用率 847.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '847', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8076', 'NetWork', '2016-12-30 18:34:32', '流量警告:当前 NetWork 使用率 115.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '115', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8077', 'NetWork', '2016-12-30 18:34:50', '流量警告:当前 NetWork 使用率 198.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '198', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8078', 'NetWork', '2016-12-30 18:34:55', '流量警告:当前 NetWork 使用率 313.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '313', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8079', 'NetWork', '2016-12-30 18:35:00', '流量警告:当前 NetWork 使用率 114.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '114', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8080', 'NetWork', '2016-12-30 18:39:10', '流量警告:当前 NetWork 使用率 1040.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1040', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8081', 'NetWork', '2016-12-30 18:39:15', '流量警告:当前 NetWork 使用率 1213.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1213', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8082', 'NetWork', '2016-12-30 18:43:35', '流量警告:当前 NetWork 使用率 1566.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1566', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8083', 'NetWork', '2016-12-30 18:43:40', '流量警告:当前 NetWork 使用率 1235.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1235', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8084', 'NetWork', '2016-12-30 18:49:05', '流量警告:当前 NetWork 使用率 711.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '711', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8085', 'NetWork', '2016-12-30 18:52:30', '流量警告:当前 NetWork 使用率 1653.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1653', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8086', 'NetWork', '2016-12-30 18:52:35', '流量警告:当前 NetWork 使用率 372.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '372', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8087', 'NetWork', '2016-12-30 18:56:25', '流量警告:当前 NetWork 使用率 2189.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2189', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8088', 'NetWork', '2016-12-30 19:04:35', '流量警告:当前 NetWork 使用率 878.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '878', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8089', 'NetWork', '2016-12-30 19:08:50', '流量警告:当前 NetWork 使用率 2162.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2162', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8090', 'NetWork', '2016-12-30 19:13:05', '流量警告:当前 NetWork 使用率 1058.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1058', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8091', 'NetWork', '2016-12-30 19:18:10', '流量警告:当前 NetWork 使用率 1015.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1015', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8092', 'NetWork', '2016-12-30 19:21:30', '流量警告:当前 NetWork 使用率 2792.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2792', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8093', 'NetWork', '2016-12-30 19:26:58', '流量警告:当前 NetWork 使用率 537.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '537', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8094', 'NetWork', '2016-12-30 19:27:23', '流量警告:当前 NetWork 使用率 250.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '250', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8095', 'NetWork', '2016-12-30 19:36:30', '流量警告:当前 NetWork 使用率 919.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '919', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8096', 'NetWork', '2016-12-30 19:40:50', '流量警告:当前 NetWork 使用率 1903.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1903', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8097', 'NetWork', '2016-12-30 19:44:30', '流量警告:当前 NetWork 使用率 1790.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1790', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8098', 'NetWork', '2016-12-30 19:51:36', '流量警告:当前 NetWork 使用率 252.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '252', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8099', 'NetWork', '2016-12-30 19:55:20', '流量警告:当前 NetWork 使用率 933.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '933', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8100', 'NetWork', '2016-12-30 19:59:50', '流量警告:当前 NetWork 使用率 2019.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2019', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8101', 'NetWork', '2016-12-30 20:03:45', '流量警告:当前 NetWork 使用率 2508.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2508', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8102', 'NetWork', '2016-12-30 20:08:40', '流量警告:当前 NetWork 使用率 1928.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1928', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8103', 'NetWork', '2016-12-30 20:13:06', '流量警告:当前 NetWork 使用率 171.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '171', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8104', 'NetWork', '2016-12-30 20:19:25', '流量警告:当前 NetWork 使用率 1755.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1755', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8105', 'NetWork', '2016-12-30 20:19:25', '流量警告:当前 NetWork 使用率 83.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '83', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8106', 'NetWork', '2016-12-30 20:22:55', '流量警告:当前 NetWork 使用率 379.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '379', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8107', 'NetWork', '2016-12-30 20:23:00', '流量警告:当前 NetWork 使用率 1917.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1917', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8108', 'NetWork', '2016-12-30 20:27:25', '流量警告:当前 NetWork 使用率 1913.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1913', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8109', 'NetWork', '2016-12-30 20:31:10', '流量警告:当前 NetWork 使用率 2587.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2587', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8110', 'NetWork', '2016-12-30 20:31:10', '流量警告:当前 NetWork 使用率 108.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '108', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8111', 'NetWork', '2016-12-30 20:47:45', '流量警告:当前 NetWork 使用率 2129.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2129', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8112', 'NetWork', '2016-12-30 20:47:45', '流量警告:当前 NetWork 使用率 92.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '92', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8113', 'NetWork', '2016-12-30 20:51:55', '流量警告:当前 NetWork 使用率 1663.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '1663', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8114', 'NetWork', '2016-12-30 20:55:10', '流量警告:当前 NetWork 使用率 2259.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2259', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8115', 'SWAP', '2016-12-30 20:56:30', '内存警告:当前 SWAP 使用率 81.00,高于预警值 80.00', '127.0.0.1', '内存警告', 'SWAP', '81', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8116', 'NetWork', '2016-12-30 20:59:35', '流量警告:当前 NetWork 使用率 2930.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '2930', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8117', 'NetWork', '2016-12-30 20:59:35', '流量警告:当前 NetWork 使用率 120.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '120', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8118', 'NetWork', '2017-01-12 16:19:00', '流量警告:当前 NetWork 使用率 281.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '281', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8119', 'NetWork', '2017-01-12 16:23:00', '流量警告:当前 NetWork 使用率 670.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '670', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8120', 'NetWork', '2017-01-12 16:23:10', '流量警告:当前 NetWork 使用率 125.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '125', '80');
INSERT INTO `sys_apm_alarm` VALUES ('8121', 'NetWork', '2017-01-12 16:23:35', '流量警告:当前 NetWork 使用率 216.00,高于预警值 80.00', '127.0.0.1', '流量警告', 'NetWork', '216', '80');

-- ----------------------------
-- Table structure for sys_department
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `short_no` int(11) DEFAULT NULL,
  `short_name` varchar(50) DEFAULT NULL,
  `depticon` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  `create_userid` int(11) DEFAULT NULL,
  `update_userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES ('1', '0', '\0', '总裁室', '0', '', 'fa-music', '刚回家就更好', '2016-12-26 22:29:15', '2016-12-29 19:33:15', '1', '0');
INSERT INTO `sys_department` VALUES ('4', '1', '\0', '总裁秘书部', '0', '秘书部', 'fa-music', '是个电饭锅', '2016-12-26 22:41:31', '2016-12-27 10:02:16', '2', '0');
INSERT INTO `sys_department` VALUES ('6', '0', '\0', '销售部', '2', '', 'fa-glass', '', '2016-12-27 10:02:34', '2016-12-27 10:02:46', '1', '0');
INSERT INTO `sys_department` VALUES ('7', '0', '\0', '技术部', '3', '', 'fa-th', '', '2016-12-27 10:02:57', null, '1', '0');
INSERT INTO `sys_department` VALUES ('8', '0', '\0', '售后维护部', '4', '', 'fa-th', '', '2016-12-27 10:03:11', null, '1', '0');
INSERT INTO `sys_department` VALUES ('9', '7', '\0', '研发部', '0', '', 'fa-th-list', '', '2016-12-27 10:03:25', '2016-12-27 10:03:47', '1', '0');
INSERT INTO `sys_department` VALUES ('10', '0', '\0', '办公室', '1', '', 'fa-crosshairs', '', '2016-12-27 10:04:11', null, '1', '0');
INSERT INTO `sys_department` VALUES ('11', '7', '\0', '测试部', '0', '', 'fa-file-o', '', '2016-12-27 10:04:38', null, '1', '0');

-- ----------------------------
-- Table structure for sys_file_attach
-- ----------------------------
DROP TABLE IF EXISTS `sys_file_attach`;
CREATE TABLE `sys_file_attach` (
  `attachid` int(11) NOT NULL AUTO_INCREMENT,
  `attachtype` varchar(255) DEFAULT NULL,
  `attachfilename` varchar(255) DEFAULT NULL,
  `savedfilename` varchar(255) DEFAULT NULL,
  `savedpath` varchar(255) DEFAULT NULL,
  `filesize` bigint(20) DEFAULT NULL,
  `adduser` varchar(255) DEFAULT NULL,
  `addtime` datetime DEFAULT NULL,
  PRIMARY KEY (`attachid`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_file_attach
-- ----------------------------
INSERT INTO `sys_file_attach` VALUES ('1', '', 'TMPSNAPSHOT1479895941382.jpg', 'B0I9pCX7Bi_TMPSNAPSHOT1479895941382.jpg', 'upload\\web\\1\\2017\\0105\\1410\\B0I9pCX7Bi_1483596624775.jpg', '0', 'admin', '2017-01-05 14:10:24');
INSERT INTO `sys_file_attach` VALUES ('2', '', '1.png', 'mLxuVYB2HE_1.png', 'upload\\web\\1\\2017\\0105\\1412\\mLxuVYB2HE_1483596753292.png', '0', 'admin', '2017-01-05 14:12:33');
INSERT INTO `sys_file_attach` VALUES ('3', '', 'destination_1.png', 'LzXsPu2JD1_destination_1.png', 'upload\\web\\1\\2017\\0105\\1413\\LzXsPu2JD1_destination_1483596810093.png', '0', 'admin', '2017-01-05 14:13:30');
INSERT INTO `sys_file_attach` VALUES ('4', '', 'logo.png', 'Ic2o12QRw1_logo.png', 'upload\\web\\1\\2017\\0105\\1423\\Ic2o12QRw1_1483597437430.png', '0', 'admin', '2017-01-05 14:23:57');
INSERT INTO `sys_file_attach` VALUES ('5', '', '操.png', 'LhsmbT3V5b_map1.png', 'upload\\web\\1\\2017\\0105\\1423\\LhsmbT3V5b_1483597437435.png', '0', 'admin', '2017-01-05 14:23:57');
INSERT INTO `sys_file_attach` VALUES ('6', '', 'map2.png', 'sAWEi9ADCL_map2.png', 'upload\\web\\1\\2017\\0105\\1423\\sAWEi9ADCL_1483597437434.png', '0', 'admin', '2017-01-05 14:23:57');
INSERT INTO `sys_file_attach` VALUES ('7', '', '1.png', 'u1s1RKqOrZ_1.png', 'upload\\web\\1\\2017\\0105\\1429\\u1s1RKqOrZ_1483597794314.png', '0', 'admin', '2017-01-05 14:29:54');
INSERT INTO `sys_file_attach` VALUES ('8', '', '1.png', 'bV4WdOxPYH_1.png', 'upload\\web\\1\\2017\\0105\\1436\\bV4WdOxPYH_1483598165468.png', '0', 'admin', '2017-01-05 14:36:05');
INSERT INTO `sys_file_attach` VALUES ('9', 'news', '5.png', 'oW5oXd9i5P_5.png', 'upload\\web\\1\\2017\\0105\\1440\\oW5oXd9i5P_1483598448806.png', '0', 'admin', '2017-01-05 14:40:48');
INSERT INTO `sys_file_attach` VALUES ('10', '', '4qa6g.jpg', 'zjfA7iflzZ_4qa6g.jpg', 'upload\\web\\1\\2017\\0105\\1526\\zjfA7iflzZ_4qa6g.jpg', '0', 'admin', '2017-01-05 15:26:26');
INSERT INTO `sys_file_attach` VALUES ('11', '', 'zMyrp.jpg', 'drGSHRk7UC_zMyrp.jpg', 'upload\\web\\1\\2017\\0105\\1526\\drGSHRk7UC_zMyrp.jpg', '0', 'admin', '2017-01-05 15:26:26');
INSERT INTO `sys_file_attach` VALUES ('12', '', 'Wdfd4.jpg', 'Xg5O73YVd5_Wdfd4.jpg', 'upload\\web\\1\\2017\\0105\\1527\\Xg5O73YVd5_Wdfd4.jpg', '0', 'admin', '2017-01-05 15:27:36');
INSERT INTO `sys_file_attach` VALUES ('13', 'news', '7y4Yj.jpg', 'JTcc3h7fqd_7y4Yj.jpg', 'upload\\web\\1\\2017\\0105\\1529\\JTcc3h7fqd_7y4Yj.jpg', '2173', 'admin', '2017-01-05 15:29:57');
INSERT INTO `sys_file_attach` VALUES ('14', 'news', 'i7E6W.jpg', 'k9y7dMmooy_i7E6W.jpg', 'upload\\web\\1\\2017\\0105\\1610\\k9y7dMmooy_i7E6W.jpg', '32158', 'admin', '2017-01-05 16:10:45');
INSERT INTO `sys_file_attach` VALUES ('15', 'news', 'ICeiV.jpg', '8Lzew9Vncr_ICeiV.jpg', 'upload\\web\\1\\2017\\0105\\1614\\8Lzew9Vncr_ICeiV.jpg', '33866', 'admin', '2017-01-05 16:14:53');
INSERT INTO `sys_file_attach` VALUES ('16', 'news', 'AAt1X.jpg', 'nCaFVsNfao_AAt1X.jpg', 'upload\\web\\1\\2017\\0106\\1915\\4tJgPZNJG7_kih2j.jpg', '33866', 'admin', '2017-01-05 16:17:32');
INSERT INTO `sys_file_attach` VALUES ('17', '', 'Jj63o.jpg', 'wB04hK4ceO_Jj63o.jpg', 'privateupload\\web\\1\\2017\\0110\\1438\\wB04hK4ceO_Jj63o.jpg', '0', 'admin', '2017-01-10 14:38:27');
INSERT INTO `sys_file_attach` VALUES ('18', '', 'KLREA.jpg', 'LGvFph93r8_KLREA.jpg', 'privateupload\\web\\1\\2017\\0110\\1438\\LGvFph93r8_KLREA.jpg', '0', 'admin', '2017-01-10 14:38:49');
INSERT INTO `sys_file_attach` VALUES ('19', '', 'qbHDe.jpg', 'GYXrr0iqXR_qbHDe.jpg', 'privateupload\\web\\1\\2017\\0110\\1440\\GYXrr0iqXR_qbHDe.jpg', '0', 'admin', '2017-01-10 14:40:59');
INSERT INTO `sys_file_attach` VALUES ('20', 'news', 'x.png', '60CBHmFw30_x.png', 'publiceupload\\web\\1\\2017\\0112\\1422\\60CBHmFw30_1484202169872.png', '0', 'admin', '2017-01-12 14:22:49');
INSERT INTO `sys_file_attach` VALUES ('21', 'news', 'x1.png', 'iR4YLIZAmT_x1.png', 'publiceupload\\web\\1\\2017\\0112\\1425\\iR4YLIZAmT_1484202320131.png', '0', 'admin', '2017-01-12 14:25:20');
INSERT INTO `sys_file_attach` VALUES ('22', 'news', 'x1.png', 'kbCJMNotS7_x1.png', 'publiceupload\\web\\1\\2017\\0112\\1426\\kbCJMNotS7_1484202382409.png', '0', 'admin', '2017-01-12 14:26:22');
INSERT INTO `sys_file_attach` VALUES ('23', 'news', 'x1.png', '8pmJSrK1kk_x1.png', 'publiceupload\\web\\1\\2017\\0112\\1427\\8pmJSrK1kk_1484202421218.png', '0', 'admin', '2017-01-12 14:27:01');
INSERT INTO `sys_file_attach` VALUES ('24', 'news', 'x1.png', 'u6M709wrUH_x1.png', 'publiceupload\\web\\1\\2017\\0112\\1427\\u6M709wrUH_1484202452126.png', '0', 'admin', '2017-01-12 14:27:32');
INSERT INTO `sys_file_attach` VALUES ('25', 'news', 'x1.png', 'AFClPO5WQT_x1.png', 'publiceupload\\web\\1\\2017\\0112\\1427\\AFClPO5WQT_1484202469573.png', '0', 'admin', '2017-01-12 14:27:49');
INSERT INTO `sys_file_attach` VALUES ('26', 'news', 'x1.png', 'jtlkTtjFl9_x1.png', 'publiceupload\\web\\1\\2017\\0112\\1429\\jtlkTtjFl9_1484202692200.png', '0', 'admin', '2017-01-12 14:31:32');
INSERT INTO `sys_file_attach` VALUES ('27', 'news', 'x1.png', 'gpnTi3QQCd_x1.png', 'publiceupload\\web\\1\\2017\\0112\\1431\\gpnTi3QQCd_1484202724212.png', '14149', 'admin', '2017-01-12 14:32:07');
INSERT INTO `sys_file_attach` VALUES ('28', 'news', 'x.png', '5sepTFfGbK_x.png', 'publiceupload\\web\\1\\2017\\0112\\1432\\5sepTFfGbK_1484202775937.png', '0', 'admin', '2017-01-12 14:32:55');
INSERT INTO `sys_file_attach` VALUES ('29', 'news', 'x1.png', 'KqMo2ByWao_x1.png', 'publiceupload\\web\\1\\2017\\0112\\1433\\KqMo2ByWao_1484202794816.png', '14149', 'admin', '2017-01-12 14:33:14');
INSERT INTO `sys_file_attach` VALUES ('30', 'news', 'demo.zip', 'qkbG0AqWit_demo.zip', 'publiceupload\\web\\1\\2017\\0112\\1433\\qkbG0AqWit_1484202836164.zip', '0', 'admin', '2017-01-12 14:33:56');
INSERT INTO `sys_file_attach` VALUES ('31', 'news', 'VID_20170111_131252.mp4', 'q7Pt5sjwuv_VID_20170111_131252.mp4', 'publiceupload\\web\\1\\2017\\0112\\1451\\q7Pt5sjwuv_VID_20170111_1484203873317.mp4', '0', 'admin', '2017-01-12 14:51:13');
INSERT INTO `sys_file_attach` VALUES ('32', 'news', 'VID_20170111_131252.mp4', 'FkVdwEbQr4_VID_20170111_131252.mp4', 'publiceupload\\web\\1\\2017\\0112\\1451\\FkVdwEbQr4_VID_20170111_1484203901069.mp4', '0', 'admin', '2017-01-12 14:51:41');
INSERT INTO `sys_file_attach` VALUES ('33', 'news', 'VID_20170111_131252.mp4', 'SCx2EFDcmp_VID_20170111_131252.mp4', 'publiceupload\\web\\1\\2017\\0112\\1500\\SCx2EFDcmp_VID_20170111_1484204437616.mp4', '0', 'admin', '2017-01-12 15:00:37');
INSERT INTO `sys_file_attach` VALUES ('34', 'news', 'VID_20170111_131252.mp4', '7Y0l539HL3_VID_20170111_131252.mp4', 'publiceupload\\web\\1\\2017\\0112\\1504\\7Y0l539HL3_VID_20170111_1484204641298.mp4', '0', 'admin', '2017-01-12 15:04:01');
INSERT INTO `sys_file_attach` VALUES ('35', 'news', 'VID_20170111_131252.mp4', 'MHJ0FTT6Ux_VID_20170111_131252.mp4', 'publiceupload\\web\\1\\2017\\0112\\1505\\MHJ0FTT6Ux_VID_20170111_1484204736422.mp4', '0', 'admin', '2017-01-12 15:05:36');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `menu_name` varchar(100) DEFAULT NULL,
  `menu_target` varchar(100) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `menu_icon` varchar(50) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `menu_type` int(11) DEFAULT NULL,
  `short_no` int(11) DEFAULT '0',
  `description` varchar(100) DEFAULT NULL,
  `can_delect` bit(1) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '运维监控', '', 'sys.yw', 'fa-television', '\0', '0', '0', '服务器监控', '\0', null, '2016-12-27 12:52:23');
INSERT INTO `sys_menu` VALUES ('2', '1', '主机性能监控', '/monitor/apm/dashboard', 'sys.yw.apm', 'fa-dashboard', '\0', '0', '0', '主机性能监控', '\0', null, '2016-12-27 12:52:47');
INSERT INTO `sys_menu` VALUES ('5', '8', '用户管理', '/sysAccount/manager', null, 'fa-user', '\0', '0', '3', '消息类型', '\0', null, '2016-12-27 11:05:33');
INSERT INTO `sys_menu` VALUES ('6', '8', '角色管理', '/sysRole/manager', null, 'fa-lock', '\0', '0', '4', '角色管理', '\0', null, '2016-12-27 11:08:24');
INSERT INTO `sys_menu` VALUES ('8', '0', '系统控制', '', null, 'fa-cogs', '\0', '0', '1', '新闻类型', '\0', null, '2016-12-22 14:44:44');
INSERT INTO `sys_menu` VALUES ('12', '8', '菜单管理', '/sysMenu/index', null, 'fa-eye', '\0', '0', '2', '菜单管理', '\0', null, '2016-12-27 11:00:57');
INSERT INTO `sys_menu` VALUES ('14', '8', '组织架构管理', '/sysOrg/index', null, 'fa-users', '\0', '0', '0', '组织机构管理', '\0', null, '2016-12-26 21:46:50');
INSERT INTO `sys_menu` VALUES ('15', '1', '定时任务管理', '/sysJobs/index', null, 'fa-tasks', '\0', '0', '2', '任务监控', '\0', '2016-11-25 17:14:47', '2016-12-26 20:20:55');
INSERT INTO `sys_menu` VALUES ('16', '1', 'Druid 监控', '/monitor/db/dashboard', null, 'fa-hdd-o', '\0', '0', '1', '', '\0', '2016-12-08 10:35:13', '2016-12-23 15:18:46');
INSERT INTO `sys_menu` VALUES ('28', '8', '部门管理', '/sysDept/index', null, 'fa-users', '\0', '0', '1', '', '\0', '2016-12-27 09:59:40', null);
INSERT INTO `sys_menu` VALUES ('29', '2', '调整告警监控', '', 'sys.yw.apm.update', 'fa-pencil-square-o', '\0', '1', '0', '', '\0', null, '2016-12-29 19:09:26');

-- ----------------------------
-- Table structure for sys_organization
-- ----------------------------
DROP TABLE IF EXISTS `sys_organization`;
CREATE TABLE `sys_organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `short_no` int(11) DEFAULT NULL,
  `short_name` varchar(50) DEFAULT NULL,
  `orgicon` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `ct` datetime DEFAULT NULL,
  `ut` datetime DEFAULT NULL,
  `create_userid` int(11) DEFAULT NULL,
  `update_userid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_organization
-- ----------------------------
INSERT INTO `sys_organization` VALUES ('1', '0', '\0', '测试组织机构', '0', '测试', 'fa-music', '刚回家就更好', '2016-12-26 22:29:15', '2016-12-27 10:17:29', '1', '0');
INSERT INTO `sys_organization` VALUES ('4', '1', '\0', '成都市分部', '0', '分部1', 'fa-music', '是个电饭锅', '2016-12-26 22:41:31', '2016-12-27 10:17:55', '2', '0');
INSERT INTO `sys_organization` VALUES ('5', '1', '\0', '北京市分部', '0', '上的发生的', 'fa-music', '是个电饭锅', '2016-12-26 22:41:39', '2016-12-27 10:18:24', '2', '0');

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
INSERT INTO `sys_quartzjob` VALUES ('1', '测试任务', 'com.sgaop.task.TestJob', '*/5 * * * * ?', '系统默认任务', '1', 'DEFAULT', 'NONE', '1', '6da64b5bd2ee-6f830c9a-871c-40df-bd85-6b8c8d854b69');
INSERT INTO `sys_quartzjob` VALUES ('2', 'APM任务', 'com.sgaop.task.ApmJob', '*/5 * * * * ?', '系统默认任务', '1', 'DEFAULT', 'NORMAL', '0', '6da64b5bd2ee-f9f73ba9-c558-4f67-97a3-9d6e7f341e0c');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) NOT NULL,
  `role_code` varchar(50) NOT NULL,
  `short_no` int(11) DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '超级管理员', 'admin', '1', '\0');
INSERT INTO `sys_role` VALUES ('2', '普通用户', 'user', '2', '\0');

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` int(11) NOT NULL,
  `menu_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('1', '1');
INSERT INTO `sys_role_menu` VALUES ('1', '2');
INSERT INTO `sys_role_menu` VALUES ('1', '5');
INSERT INTO `sys_role_menu` VALUES ('1', '6');
INSERT INTO `sys_role_menu` VALUES ('1', '8');
INSERT INTO `sys_role_menu` VALUES ('1', '12');
INSERT INTO `sys_role_menu` VALUES ('1', '14');
INSERT INTO `sys_role_menu` VALUES ('1', '15');
INSERT INTO `sys_role_menu` VALUES ('1', '16');
INSERT INTO `sys_role_menu` VALUES ('1', '18');
INSERT INTO `sys_role_menu` VALUES ('1', '19');
INSERT INTO `sys_role_menu` VALUES ('1', '26');
INSERT INTO `sys_role_menu` VALUES ('1', '28');
INSERT INTO `sys_role_menu` VALUES ('1', '29');
INSERT INTO `sys_role_menu` VALUES ('1', '30');
INSERT INTO `sys_role_menu` VALUES ('1', '31');
INSERT INTO `sys_role_menu` VALUES ('2', '8');
INSERT INTO `sys_role_menu` VALUES ('2', '14');
INSERT INTO `sys_role_menu` VALUES ('2', '28');

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
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_useraccount
-- ----------------------------
INSERT INTO `sys_useraccount` VALUES ('1', 'admin', '676f115f130fafbb34beabb6f0d22e2f82574fa9b828237f978e4dfeb4db6737', '\0', '2016-11-09 15:23:12', 'd6185bb2174f4a15b7ee911397824755');
INSERT INTO `sys_useraccount` VALUES ('23', 'newsadmin', 'a2eb727e55ce73ac2e2d68ca7081295f1750da6021cb0516175635f307a6f0fa', '\0', '2017-01-10 14:51:02', '30d8cc0be3604229bdf399354fe4cbcd');

-- ----------------------------
-- Table structure for sys_useraccount_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_useraccount_role`;
CREATE TABLE `sys_useraccount_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_useraccount_role
-- ----------------------------
INSERT INTO `sys_useraccount_role` VALUES ('1', '1');
INSERT INTO `sys_useraccount_role` VALUES ('23', '3');
