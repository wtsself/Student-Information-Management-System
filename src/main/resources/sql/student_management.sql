/*
 Navicat Premium Dump SQL

 Source Server         : myself
 Source Server Type    : MySQL
 Source Server Version : 80043 (8.0.43)
 Source Host           : localhost:3306
 Source Schema         : student_management

 Target Server Type    : MySQL
 Target Server Version : 80043 (8.0.43)
 File Encoding         : 65001

 Date: 15/09/2025 17:32:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for courses
-- ----------------------------
DROP TABLE IF EXISTS `courses`;
CREATE TABLE `courses`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '课程ID',
  `course_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程代码',
  `course_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '课程名称',
  `credits` decimal(3, 1) NOT NULL COMMENT '学分',
  `teacher` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '授课教师',
  `semester` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学期',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `course_code`(`course_code` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '课程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of courses
-- ----------------------------
INSERT INTO `courses` VALUES (1, 'CS101', '计算机基础', 8.0, '张教授', '2023-2024-1', '2025-09-15 11:31:48', '2025-09-15 14:58:20');
INSERT INTO `courses` VALUES (2, 'CS102', '数据结构', 4.0, '李教授', '2023-2024-1', '2025-09-15 11:31:48', '2025-09-15 11:31:48');
INSERT INTO `courses` VALUES (3, 'CS103', '算法设计', 4.0, '王教授', '2023-2024-2', '2025-09-15 11:31:48', '2025-09-15 11:31:48');
INSERT INTO `courses` VALUES (4, 'MATH101', '高等数学', 5.0, '赵教授', '2023-2024-1', '2025-09-15 11:31:48', '2025-09-15 11:31:48');
INSERT INTO `courses` VALUES (5, 'ENG101', '大学英语', 3.0, '刘老师', '2023-2024-1', '2025-09-15 11:31:48', '2025-09-15 11:31:48');
INSERT INTO `courses` VALUES (6, 'CS505', '数字逻辑ABC', 8.0, '吴太松博士', '2025-1', '2025-09-15 16:59:09', '2025-09-15 17:01:02');

-- ----------------------------
-- Table structure for health_records
-- ----------------------------
DROP TABLE IF EXISTS `health_records`;
CREATE TABLE `health_records`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '健康记录ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `height` decimal(5, 2) NULL DEFAULT NULL COMMENT '身高(cm)',
  `weight` decimal(5, 2) NULL DEFAULT NULL COMMENT '体重(kg)',
  `blood_type` enum('A','B','AB','O') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '血型',
  `vision_left` decimal(3, 1) NULL DEFAULT NULL COMMENT '左眼视力',
  `vision_right` decimal(3, 1) NULL DEFAULT NULL COMMENT '右眼视力',
  `blood_pressure_systolic` int NULL DEFAULT NULL COMMENT '收缩压',
  `blood_pressure_diastolic` int NULL DEFAULT NULL COMMENT '舒张压',
  `heart_rate` int NULL DEFAULT NULL COMMENT '心率',
  `medical_history` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '病史',
  `allergies` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '过敏史',
  `emergency_contact` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '紧急联系人',
  `emergency_phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '紧急联系电话',
  `check_date` date NULL DEFAULT NULL COMMENT '体检日期',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `student_id`(`student_id` ASC) USING BTREE,
  CONSTRAINT `health_records_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '健康信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of health_records
-- ----------------------------
INSERT INTO `health_records` VALUES (1, 1, 155.50, 65.20, 'A', 5.0, 5.0, 115, 75, 72, '', '花粉', '父亲', '13810000001', '2025-01-02', '2025-09-15 15:12:53', '2025-09-15 15:47:55');
INSERT INTO `health_records` VALUES (2, 2, 162.30, 52.80, 'B', 4.8, 4.9, 110, 70, 80, '', '', '母亲', '13810000002', '2025-07-28', '2025-09-15 15:12:53', '2025-09-15 15:48:04');
INSERT INTO `health_records` VALUES (3, 3, 178.10, 70.50, 'O', 5.2, 5.1, 118, 78, 89, '', '青霉素', '哥哥', '13810000003', '2025-09-01', '2025-09-15 15:12:53', '2025-09-15 15:48:11');
INSERT INTO `health_records` VALUES (4, 4, 160.50, 50.10, 'AB', 4.7, 4.8, 112, 72, 76, NULL, NULL, '姐姐', '13810000004', '2023-10-01', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `health_records` VALUES (5, 5, 172.00, 62.00, 'A', 5.0, 5.0, 120, 80, 74, '哮喘', NULL, '父亲', '13810000005', '2023-10-01', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `health_records` VALUES (6, 6, 165.50, 54.30, 'O', 4.9, 4.8, 110, 70, 78, NULL, NULL, '母亲', '13810000006', '2023-10-01', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `health_records` VALUES (7, 7, 181.20, 75.60, 'B', 5.0, 5.0, 118, 76, 72, NULL, '花粉', '父亲', '13810000007', '2023-10-01', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `health_records` VALUES (8, 8, 158.70, 48.00, 'A', 4.6, 4.7, 108, 68, 80, NULL, NULL, '母亲', '13810000008', '2023-10-01', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `health_records` VALUES (9, 9, 177.30, 68.20, 'AB', 5.1, 5.0, 115, 75, 70, NULL, NULL, '父亲', '13810000009', '2023-10-01', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `health_records` VALUES (10, 10, 163.80, 52.60, 'O', 4.8, 4.9, 112, 72, 76, NULL, NULL, '母亲', '13810000010', '2023-10-01', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `health_records` VALUES (11, 52, 273.00, 100.00, 'A', 5.0, 4.0, 213, 213, 97, '无', '对空气过敏', '哥哥', '13810000003', '2025-09-01', '2025-09-15 17:13:31', '2025-09-15 17:16:26');

-- ----------------------------
-- Table structure for scores
-- ----------------------------
DROP TABLE IF EXISTS `scores`;
CREATE TABLE `scores`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '成绩ID',
  `student_id` bigint NOT NULL COMMENT '学生ID',
  `course_id` bigint NOT NULL COMMENT '课程ID',
  `score` decimal(5, 2) NULL DEFAULT NULL COMMENT '成绩',
  `grade_point` decimal(3, 2) NULL DEFAULT NULL COMMENT '绩点',
  `exam_type` enum('期末考试','期中考试','平时成绩','总评') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '总评' COMMENT '考试类型',
  `semester` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学期',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_student_course`(`student_id` ASC, `course_id` ASC, `exam_type` ASC, `semester` ASC) USING BTREE,
  INDEX `course_id`(`course_id` ASC) USING BTREE,
  CONSTRAINT `scores_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `scores_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '成绩表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of scores
-- ----------------------------
INSERT INTO `scores` VALUES (1, 1, 1, 100.00, 4.00, '总评', '2025-1', '2025-09-15 14:50:08', '2025-09-15 14:58:15');
INSERT INTO `scores` VALUES (2, 1, 1, 85.00, 3.50, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (3, 1, 2, 78.00, 3.00, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (4, 1, 4, 90.00, 4.00, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (5, 1, 5, 82.00, 3.20, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (6, 1, 3, 88.00, 3.80, '总评', '2023-2024-2', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (7, 2, 1, 75.00, 2.50, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (8, 2, 2, 80.00, 3.00, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (9, 2, 4, 68.00, 2.00, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (10, 2, 5, 85.00, 3.50, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (11, 2, 3, 77.00, 2.70, '总评', '2023-2024-2', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (12, 3, 1, 92.00, 4.00, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (13, 3, 2, 88.00, 3.80, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (14, 3, 4, 79.00, 3.00, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (15, 3, 5, 83.00, 3.30, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (16, 3, 3, 91.00, 4.00, '总评', '2023-2024-2', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (17, 4, 1, 65.00, 1.50, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (18, 4, 2, 72.00, 2.20, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (19, 4, 4, 70.00, 2.00, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (20, 4, 5, 60.00, 1.00, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (21, 4, 3, 68.00, 2.00, '总评', '2023-2024-2', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (22, 5, 1, 88.00, 3.80, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (23, 5, 2, 84.00, 3.50, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (24, 5, 4, 90.00, 4.00, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (25, 5, 5, 76.00, 2.50, '总评', '2023-2024-1', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (26, 5, 3, 87.00, 3.70, '总评', '2023-2024-2', '2025-09-15 15:12:54', '2025-09-15 15:12:54');
INSERT INTO `scores` VALUES (27, 52, 6, 30.00, 0.00, '总评', '2025-1', '2025-09-15 17:04:49', '2025-09-15 17:07:30');

-- ----------------------------
-- Table structure for students
-- ----------------------------
DROP TABLE IF EXISTS `students`;
CREATE TABLE `students`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '学生ID',
  `student_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学号',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '姓名',
  `gender` enum('男','女') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '性别',
  `birth_date` date NULL DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '身份证号',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '家庭住址',
  `major` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '专业',
  `class_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '班级',
  `enrollment_date` date NULL DEFAULT NULL COMMENT '入学日期',
  `status` enum('在读','毕业','退学','休学') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '在读' COMMENT '学籍状态',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `student_no`(`student_no` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of students
-- ----------------------------
INSERT INTO `students` VALUES (1, '2023001', '张三', '女', NULL, '110101200503150001', '13800138001', 'zhangsan@example.com', '北京市朝阳区', '计算机科学与技术', '计科2023-1班', NULL, '在读', '2025-09-15 11:31:48', '2025-09-15 14:58:31');
INSERT INTO `students` VALUES (2, '2023002', '李四八', '女', NULL, '110101200507220002', '13800138002', 'lisi@example.com', '上海市浦东新区', '软件工程', '软工2023-1班', NULL, '在读', '2025-09-15 11:31:48', '2025-09-15 15:00:41');
INSERT INTO `students` VALUES (3, '2023003', '王五', '男', '2005-01-10', '110101200501100003', '13800138003', 'wangwu@example.com', '广州市天河区', '计算机科学与技术', '计科2023-1班', '2023-09-01', '在读', '2025-09-15 11:31:48', '2025-09-15 11:31:48');
INSERT INTO `students` VALUES (4, '2023004', '赵六', '女', '2005-11-05', '110101200511050004', '13800138004', 'zhaoliu@example.com', '深圳市南山区', '软件工程', '软工2023-1班', '2023-09-01', '在读', '2025-09-15 11:31:48', '2025-09-15 11:31:48');
INSERT INTO `students` VALUES (5, '2023005', '钱七', '男', '2005-05-18', '110101200505180005', '13800138005', 'qianqi@example.com', '杭州市西湖区', '计算机科学与技术', '计科2023-1班', '2023-09-01', '在读', '2025-09-15 11:31:48', '2025-09-15 11:31:48');
INSERT INTO `students` VALUES (6, '2023006', '孙八', '女', '2005-02-12', '110101200502120006', '13800138006', 'sunba@example.com', '南京市鼓楼区', '软件工程', '软工2023-1班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (7, '2023007', '周九和', '男', '2025-09-30', '110101200508090007', '13800138007', 'zhoujiu@example.com', '武汉市洪山区', '计算机科学与技术', '计科2023-2班', NULL, '在读', '2025-09-15 15:12:53', '2025-09-15 15:22:34');
INSERT INTO `students` VALUES (8, '2023008', '吴十', '女', '2005-04-28', '110101200504280008', '13800138008', 'wushi@example.com', '成都市武侯区', '软件工程', '软工2023-1班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (9, '2023009', '郑一一', '男', '2005-09-19', '110101200509190009', '13800138009', 'zheng11@example.com', '重庆市渝中区', '计算机科学与技术', '计科2023-2班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (10, '2023010', '王一二', '女', '2005-12-02', '110101200512020010', '13800138010', 'wang12@example.com', '苏州市吴中区', '软件工程', '软工2023-2班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (11, '2023011', '冯一三', '男', '2005-06-06', '110101200506060011', '13800138011', 'feng13@example.com', '天津市和平区', '计算机科学与技术', '计科2023-2班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (12, '2023012', '陈一四', '女', '2005-01-30', '110101200501300012', '13800138012', 'chen14@example.com', '厦门市思明区', '软件工程', '软工2023-2班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (13, '2023013', '褚一五', '男', '2005-03-25', '110101200503250013', '13800138013', 'chu15@example.com', '合肥市庐阳区', '计算机科学与技术', '计科2023-2班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (14, '2023014', '卫一六', '女', '2005-07-14', '110101200507140014', '13800138014', 'wei16@example.com', '长沙市岳麓区', '软件工程', '软工2023-2班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (15, '2023015', '蒋一七', '男', '2005-05-03', '110101200505030015', '13800138015', 'jiang17@example.com', '郑州市金水区', '计算机科学与技术', '计科2023-3班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (16, '2023016', '沈一八', '女', '2005-11-21', '110101200511210016', '13800138016', 'shen18@example.com', '青岛市市南区', '软件工程', '软工2023-2班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (17, '2023017', '韩一九', '男', '2005-08-30', '110101200508300017', '13800138017', 'han19@example.com', '大连市中山区', '计算机科学与技术', '计科2023-3班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (18, '2023018', '杨二零', '女', '2005-10-10', '110101200510100018', '13800138018', 'yang20@example.com', '福州市台江区', '软件工程', '软工2023-2班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (19, '2023019', '朱二一', '男', '2005-09-05', '110101200509050019', '13800138019', 'zhu21@example.com', '济南市历下区', '计算机科学与技术', '计科2023-3班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (20, '2023020', '秦二二', '女', '2005-04-17', '110101200504170020', '13800138020', 'qin22@example.com', '石家庄市长安区', '软件工程', '软工2023-3班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (21, '2023021', '尤二三', '男', '2005-12-29', '110101200512290021', '13800138021', 'you23@example.com', '南宁市青秀区', '计算机科学与技术', '计科2023-3班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (22, '2023022', '许二四', '女', '2005-01-09', '110101200501090022', '13800138022', 'xu24@example.com', '昆明市五华区', '软件工程', '软工2023-3班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (23, '2023023', '何二五', '男', '2005-06-13', '110101200506130023', '13800138023', 'he25@example.com', '南昌市西湖区', '计算机科学与技术', '计科2023-3班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (24, '2023024', '吕二六', '女', '2005-02-26', '110101200502260024', '13800138024', 'lv26@example.com', '贵阳市云岩区', '软件工程', '软工2023-3班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (25, '2023025', '施二七', '男', '2005-07-08', '110101200507080025', '13800138025', 'shi27@example.com', '兰州市城关区', '计算机科学与技术', '计科2023-3班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (26, '2023026', '张二八', '女', '2005-03-19', '110101200503190026', '13800138026', 'zhang28@example.com', '西安市碑林区', '软件工程', '软工2023-3班', '2023-09-01', '毕业', '2025-09-15 15:12:53', '2025-09-15 15:40:32');
INSERT INTO `students` VALUES (27, '2023027', '孔二九', '男', '2005-05-15', '110101200505150027', '13800138027', 'kong29@example.com', '哈尔滨市道里区', '计算机科学与技术', '计科2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (28, '2023028', '曹三十', '女', '2005-09-25', '110101200509250028', '13800138028', 'cao30@example.com', '太原市小店区', '软件工程', '软工2023-3班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (29, '2023029', '严三一', '男', '2005-11-18', '110101200511180029', '13800138029', 'yan31@example.com', '长春市南关区', '计算机科学与技术', '计科2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (30, '2023030', '华三二', '女', '2005-06-07', '110101200506070030', '13800138030', 'hua32@example.com', '呼和浩特市新城区', '软件工程', '软工2023-3班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (31, '2023031', '金三三', '男', '2005-08-01', '110101200508010031', '13800138031', 'jin33@example.com', '银川市兴庆区', '计算机科学与技术', '计科2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (32, '2023032', '魏三四', '女', '2005-04-12', '110101200504120032', '13800138032', 'wei34@example.com', '乌鲁木齐市天山区', '软件工程', '软工2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (33, '2023033', '陶三五', '男', '2005-10-28', '110101200510280033', '13800138033', 'tao35@example.com', '拉萨市城关区', '计算机科学与技术', '计科2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (34, '2023034', '姜三六', '女', '2005-01-18', '110101200501180034', '13800138034', 'jiang36@example.com', '海口市龙华区', '软件工程', '软工2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (35, '2023035', '戚三七', '男', '2005-03-11', '110101200503110035', '13800138035', 'qi37@example.com', '三亚市吉阳区', '计算机科学与技术', '计科2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (36, '2023036', '谢三八', '女', '2005-12-07', '110101200512070036', '13800138036', 'xie38@example.com', '珠海市香洲区', '软件工程', '软工2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (37, '2023037', '邹三九', '男', '2005-09-03', '110101200509030037', '13800138037', 'zou39@example.com', '汕头市龙湖区', '计算机科学与技术', '计科2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (38, '2023038', '喻四零', '女', '2005-02-24', '110101200502240038', '13800138038', 'yu40@example.com', '佛山市禅城区', '软件工程', '软工2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (39, '2023039', '柏四一', '男', '2005-07-29', '110101200507290039', '13800138039', 'bai41@example.com', '湛江市赤坎区', '计算机科学与技术', '计科2023-5班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (40, '2023040', '水四二', '女', '2005-06-23', '110101200506230040', '13800138040', 'shui42@example.com', '茂名市电白区', '软件工程', '软工2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (41, '2023041', '窦四三', '男', '2005-05-16', '110101200505160041', '13800138041', 'dou43@example.com', '汕尾市城区', '计算机科学与技术', '计科2023-5班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (42, '2023042', '章四四', '女', '2005-11-11', '110101200511110042', '13800138042', 'zhang44@example.com', '揭阳市榕城区', '软件工程', '软工2023-4班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (43, '2023043', '云四五', '男', '2005-03-06', '110101200503060043', '13800138043', 'yun45@example.com', '梅州市梅江区', '计算机科学与技术', '计科2023-5班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (44, '2023044', '苏四六', '女', '2005-01-27', '110101200501270044', '13800138044', 'su46@example.com', '汕头市濠江区', '软件工程', '软工2023-5班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (45, '2023045', '潘四七', '男', '2005-09-21', '110101200509210045', '13800138045', 'pan47@example.com', '漳州市芗城区', '计算机科学与技术', '计科2023-5班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (46, '2023046', '葛四八', '女', '2005-04-14', '110101200504140046', '13800138046', 'ge48@example.com', '泉州市丰泽区', '软件工程', '软工2023-5班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (47, '2023047', '范四九', '男', '2005-08-12', '110101200508120047', '13800138047', 'fan49@example.com', '莆田市城厢区', '计算机科学与技术', '计科2023-5班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (48, '2023048', '彭五零', '女', '2005-02-09', '110101200502090048', '13800138048', 'peng50@example.com', '龙岩市新罗区', '软件工程', '软工2023-5班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (49, '2023049', '鲁五一', '男', '2005-12-19', '110101200512190049', '13800138049', 'lu51@example.com', '莆田市涵江区', '计算机科学与技术', '计科2023-5班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (50, '2023050', '韦五二', '女', '2005-10-02', '110101200510020050', '13800138050', 'wei52@example.com', '泉州市鲤城区', '软件工程', '软工2023-5班', '2023-09-01', '在读', '2025-09-15 15:12:53', '2025-09-15 15:12:53');
INSERT INTO `students` VALUES (51, '20230251', '嗷嗷嗷', '女', '2005-03-19', '110101200503190026', '13800138026', 'zhang28@example.com', '蜀山区安徽大学磬苑校区枫园', '软件工程', '软工2023-1班', '2023-09-01', '在读', '2025-09-15 16:35:36', '2025-09-15 16:35:36');
INSERT INTO `students` VALUES (52, '2022304547', '吴太松ABC', '女', '2025-09-30', '110101200508090007', '13800138026', 'zhang28@example.com', '安徽淮南', '软件工程', '软工2025-1班', '2023-09-01', '休学', '2025-09-15 16:50:41', '2025-09-15 16:52:21');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `role` enum('ADMIN','USER') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'USER' COMMENT '角色：ADMIN-管理员，USER-普通用户',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '电话',
  `created_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `status` tinyint NULL DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES (1, 'admin', '$2a$10$gvnk54971k4U0ntIY0MNu.umMDJeNs2XHznFDxoE.8EdX/5IlxbmW', 'ADMIN', 'admin@example.com', NULL, '2025-09-15 11:31:48', '2025-09-15 14:02:01', 1);
INSERT INTO `users` VALUES (3, 'asd', '$2a$10$josEqEZ9iDwq911Ujo.0jOqH/yoc387NroqlHKzguxZJqkdH5XARy', 'ADMIN', '453251390@qq.com', '13800138007', '2025-09-15 15:49:55', '2025-09-15 15:53:36', 1);
INSERT INTO `users` VALUES (4, '2023003', '$2a$10$bEqwl0gAkFStRDbZb18wrOtcjr6vqJGGuPP2pYBxzqJG1t4s7SlwG', 'USER', '453251390@qq.com', '13800138007', '2025-09-15 15:54:46', '2025-09-15 15:54:46', 1);
INSERT INTO `users` VALUES (6, '哎哎哎', '$2a$10$zFN9/QKY92VBaqlRaz0OQOhhdB32CjD4EN6E5oRjhRRBk3TnLhm1K', 'USER', '453251390@qq.com', '18928358494', '2025-09-15 17:24:06', '2025-09-15 17:24:06', 1);

SET FOREIGN_KEY_CHECKS = 1;
