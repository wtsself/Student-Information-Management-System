-- 创建数据库
CREATE DATABASE IF NOT EXISTS student_management DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE student_management;

-- 用户表（管理员和普通用户）
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    role ENUM('ADMIN', 'USER') NOT NULL DEFAULT 'USER' COMMENT '角色：ADMIN-管理员，USER-普通用户',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '电话',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    status TINYINT DEFAULT 1 COMMENT '状态：1-启用，0-禁用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 学生表
CREATE TABLE IF NOT EXISTS students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '学生ID',
    student_no VARCHAR(20) NOT NULL UNIQUE COMMENT '学号',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender ENUM('男', '女') NOT NULL COMMENT '性别',
    birth_date DATE COMMENT '出生日期',
    id_card VARCHAR(18) COMMENT '身份证号',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    address TEXT COMMENT '家庭住址',
    major VARCHAR(100) COMMENT '专业',
    class_name VARCHAR(50) COMMENT '班级',
    enrollment_date DATE COMMENT '入学日期',
    status ENUM('在读', '毕业', '退学', '休学') DEFAULT '在读' COMMENT '学籍状态',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- 课程表
CREATE TABLE IF NOT EXISTS courses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '课程ID',
    course_code VARCHAR(20) NOT NULL UNIQUE COMMENT '课程代码',
    course_name VARCHAR(100) NOT NULL COMMENT '课程名称',
    credits DECIMAL(3,1) NOT NULL COMMENT '学分',
    teacher VARCHAR(50) COMMENT '授课教师',
    semester VARCHAR(20) COMMENT '学期',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- 成绩表
CREATE TABLE IF NOT EXISTS scores (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '成绩ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    course_id BIGINT NOT NULL COMMENT '课程ID',
    score DECIMAL(5,2) COMMENT '成绩',
    grade_point DECIMAL(3,2) COMMENT '绩点',
    exam_type ENUM('期末考试', '期中考试', '平时成绩', '总评') DEFAULT '总评' COMMENT '考试类型',
    semester VARCHAR(20) COMMENT '学期',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE,
    UNIQUE KEY uk_student_course (student_id, course_id, exam_type, semester)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成绩表';

-- 健康信息表
CREATE TABLE IF NOT EXISTS health_records (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '健康记录ID',
    student_id BIGINT NOT NULL COMMENT '学生ID',
    height DECIMAL(5,2) COMMENT '身高(cm)',
    weight DECIMAL(5,2) COMMENT '体重(kg)',
    blood_type ENUM('A', 'B', 'AB', 'O') COMMENT '血型',
    vision_left DECIMAL(3,1) COMMENT '左眼视力',
    vision_right DECIMAL(3,1) COMMENT '右眼视力',
    blood_pressure_systolic INT COMMENT '收缩压',
    blood_pressure_diastolic INT COMMENT '舒张压',
    heart_rate INT COMMENT '心率',
    medical_history TEXT COMMENT '病史',
    allergies TEXT COMMENT '过敏史',
    emergency_contact VARCHAR(50) COMMENT '紧急联系人',
    emergency_phone VARCHAR(20) COMMENT '紧急联系电话',
    check_date DATE COMMENT '体检日期',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康信息表';

-- 插入初始管理员用户
INSERT INTO users (username, password, role, email, status) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ADMIN', 'admin@example.com', 1);

-- 插入示例课程数据
INSERT INTO courses (course_code, course_name, credits, teacher, semester) VALUES 
('CS101', '计算机基础', 3.0, '张教授', '2023-2024-1'),
('CS102', '数据结构', 4.0, '李教授', '2023-2024-1'),
('CS103', '算法设计', 4.0, '王教授', '2023-2024-2'),
('MATH101', '高等数学', 5.0, '赵教授', '2023-2024-1'),
('ENG101', '大学英语', 3.0, '刘老师', '2023-2024-1');

-- 插入示例学生数据
INSERT INTO students (student_no, name, gender, birth_date, id_card, phone, email, address, major, class_name, enrollment_date, status) VALUES 
('2023001', '张三', '男', '2005-03-15', '110101200503150001', '13800138001', 'zhangsan@example.com', '北京市朝阳区', '计算机科学与技术', '计科2023-1班', '2023-09-01', '在读'),
('2023002', '李四', '女', '2005-07-22', '110101200507220002', '13800138002', 'lisi@example.com', '上海市浦东新区', '软件工程', '软工2023-1班', '2023-09-01', '在读'),
('2023003', '王五', '男', '2005-01-10', '110101200501100003', '13800138003', 'wangwu@example.com', '广州市天河区', '计算机科学与技术', '计科2023-1班', '2023-09-01', '在读'),
('2023004', '赵六', '女', '2005-11-05', '110101200511050004', '13800138004', 'zhaoliu@example.com', '深圳市南山区', '软件工程', '软工2023-1班', '2023-09-01', '在读'),
('2023005', '钱七', '男', '2005-05-18', '110101200505180005', '13800138005', 'qianqi@example.com', '杭州市西湖区', '计算机科学与技术', '计科2023-1班', '2023-09-01', '在读');
