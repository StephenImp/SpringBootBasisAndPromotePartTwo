/*
 Navicat Premium Data Transfer

 Source Server         : 10.100.101.40-dev
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : 10.100.101.40:3306
 Source Schema         : test_demo

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 15/10/2020 10:41:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for task
-- ----------------------------
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`  (
  `task_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `task_cron` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'cron表达式',
  `task_class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类路径',
  `task_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `task_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `task_enable` int(2) NOT NULL COMMENT '1/0   启动/关闭',
  PRIMARY KEY (`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of task
-- ----------------------------
INSERT INTO `task` VALUES ('1', '0/5 * * * * ?', 'com.cn.demoApp.demotest.scheduledb.LogTask', '日志打印', NULL, 1);
INSERT INTO `task` VALUES ('2', '0/10 * * * * ?', 'com.cn.demoApp.demotest.scheduledb.TestTask', '测试任务', NULL, 1);

SET FOREIGN_KEY_CHECKS = 1;
