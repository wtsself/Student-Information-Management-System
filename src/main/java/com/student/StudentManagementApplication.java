package com.student;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 学生信息管理系统启动类
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@SpringBootApplication
@MapperScan("com.student.mapper")
public class StudentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentManagementApplication.class, args);
        System.out.println("=================================");
        System.out.println("学生信息管理系统启动成功！");
        System.out.println("访问地址：http://localhost:8080/student-management");
        System.out.println("请使用注册功能创建新账号");
        System.out.println("=================================");
    }
}
