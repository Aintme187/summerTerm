/*
 Navicat Premium Data Transfer

 Source Server         : summer-term
 Source Server Type    : MySQL
 Source Server Version : 80039
 Source Host           : 47.94.206.220:3306
 Source Schema         : teach_service

 Target Server Type    : MySQL
 Target Server Version : 80039
 File Encoding         : 65001

 Date: 25/08/2024 21:05:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE DATABASE IF NOT EXISTS teach_service;
USE teach_service;

-- ----------------------------
-- Table structure for Schedule
-- ----------------------------
DROP TABLE IF EXISTS `Schedule`;
CREATE TABLE `Schedule`  (
  `id` bigint NOT NULL,
  `courseId` bigint NULL DEFAULT NULL,
  `week` bigint NULL DEFAULT NULL,
  `sectionBegin` bigint NULL DEFAULT NULL,
  `sectionEnd` bigint NULL DEFAULT NULL,
  `room` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `Schedule___fk1`(`courseId` ASC) USING BTREE,
  CONSTRAINT `Schedule___fk1` FOREIGN KEY (`courseId`) REFERENCES `course` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for assignment
-- ----------------------------
DROP TABLE IF EXISTS `assignment`;
CREATE TABLE `assignment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作业名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作业描述',
  `begin_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作业文件',
  `course_id` bigint NULL DEFAULT NULL,
  `file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `assignment_course_fk`(`course_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for assistant
-- ----------------------------
DROP TABLE IF EXISTS `assistant`;
CREATE TABLE `assistant`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint NULL DEFAULT NULL,
  `course_id` bigint NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `assistant_student_fk`(`student_id` ASC) USING BTREE,
  INDEX `assistant_course_FK`(`course_id` ASC) USING BTREE,
  CONSTRAINT `assistant_course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `curriculum_id` bigint NOT NULL,
  `enrollment` int NULL DEFAULT NULL COMMENT '上课人数',
  `capacity` int NULL DEFAULT NULL COMMENT '容量',
  `teacher_id` bigint NOT NULL,
  `dept_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '系名称',
  `week_begin` int NULL DEFAULT NULL COMMENT '开始周次',
  `week_end` int NULL DEFAULT NULL COMMENT '结束周次',
  `day` int NULL DEFAULT NULL COMMENT '周几',
  `section_begin` int NULL DEFAULT NULL COMMENT '开始节次',
  `section_end` int NULL DEFAULT NULL COMMENT '结束节次',
  `room` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '教室',
  `category_id` bigint NULL DEFAULT NULL,
  `teacher_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `clbum_curriculum_fk`(`curriculum_id` ASC) USING BTREE,
  INDEX `course_sys_user_FK`(`teacher_id` ASC) USING BTREE,
  CONSTRAINT `course_curriculum_fk` FOREIGN KEY (`curriculum_id`) REFERENCES `curriculum` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for curriculum
-- ----------------------------
DROP TABLE IF EXISTS `curriculum`;
CREATE TABLE `curriculum`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '课程名称',
  `credit` double NULL DEFAULT NULL COMMENT '学分',
  `introduction` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '简介',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for material
-- ----------------------------
DROP TABLE IF EXISTS `material`;
CREATE TABLE `material`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `time` datetime NULL DEFAULT NULL COMMENT '上传时间',
  `course_id` bigint NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `size` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '大小',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `material_course_FK`(`course_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sc
-- ----------------------------
DROP TABLE IF EXISTS `sc`;
CREATE TABLE `sc`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `student_id` bigint NULL DEFAULT NULL,
  `course_id` bigint NULL DEFAULT NULL,
  `score` decimal(3, 1) NULL DEFAULT NULL,
  `status` int NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sc_course_fk`(`course_id` ASC) USING BTREE,
  INDEX `sc_sys_user_fk`(`student_id` ASC) USING BTREE,
  CONSTRAINT `sc_course_fk` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for submission
-- ----------------------------
DROP TABLE IF EXISTS `submission`;
CREATE TABLE `submission`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `submission_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '作业文件',
  `time` datetime NULL DEFAULT NULL COMMENT '提交时间',
  `status` bit(1) NULL DEFAULT NULL COMMENT '作业状态',
  `score` int NULL DEFAULT NULL COMMENT '分数',
  `feedback_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '批改文件',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '评语',
  `assignment_id` bigint NULL DEFAULT NULL,
  `student_id` bigint NULL DEFAULT NULL,
  `file_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `student_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `submit_assignment_fk`(`assignment_id` ASC) USING BTREE,
  INDEX `submit_student_fk`(`student_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
