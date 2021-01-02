/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 02/01/2021 16:24:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for other_table_0
-- ----------------------------
DROP TABLE IF EXISTS `other_table_0`;
CREATE TABLE `other_table_0`  (
  `id` bigint(0) NOT NULL,
  `test_column` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of other_table_0
-- ----------------------------
INSERT INTO `other_table_0` VALUES (2, 2);

-- ----------------------------
-- Table structure for other_table_1
-- ----------------------------
DROP TABLE IF EXISTS `other_table_1`;
CREATE TABLE `other_table_1`  (
  `id` bigint(0) NOT NULL,
  `test_column` int(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of other_table_1
-- ----------------------------
INSERT INTO `other_table_1` VALUES (1, 1);

-- ----------------------------
-- Table structure for table_a
-- ----------------------------
DROP TABLE IF EXISTS `table_a`;
CREATE TABLE `table_a`  (
  `id` int(0) NOT NULL,
  `tb_a_col_1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `tb_a_col_2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `tb_a_col_3` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_a
-- ----------------------------
INSERT INTO `table_a` VALUES (1, 'tab_a_col_1_val', 'tab_a_col_2_val', 'tab_a_col_3_val');

-- ----------------------------
-- Table structure for table_b
-- ----------------------------
DROP TABLE IF EXISTS `table_b`;
CREATE TABLE `table_b`  (
  `id` int(0) NOT NULL,
  `tb_b_col_1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `tb_b_col_2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  `tb_b_col_3` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of table_b
-- ----------------------------
INSERT INTO `table_b` VALUES (1, 'tab_b_col_1_val', 'tab_b_col_2_val', 'tab_b_col_3_val');

SET FOREIGN_KEY_CHECKS = 1;
