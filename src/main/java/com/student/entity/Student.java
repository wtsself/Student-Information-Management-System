package com.student.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

/**
 * 学生实体类
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    
    /**
     * 学生ID
     */
    private Long id;
    
    /**
     * 学号
     */
    @NotBlank(message = "学号不能为空")
    @Size(max = 20, message = "学号长度不能超过20个字符")
    private String studentNo;
    
    /**
     * 姓名
     */
    @NotBlank(message = "姓名不能为空")
    @Size(max = 50, message = "姓名长度不能超过50个字符")
    private String name;
    
    /**
     * 性别
     */
    @NotBlank(message = "性别不能为空")
    private String gender;
    
    /**
     * 出生日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    
    /**
     * 身份证号
     */
    @Pattern(regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$", 
             message = "身份证号格式不正确")
    private String idCard;
    
    /**
     * 联系电话
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;
    
    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    private String email;
    
    /**
     * 家庭住址
     */
    private String address;
    
    /**
     * 专业
     */
    @Size(max = 100, message = "专业名称长度不能超过100个字符")
    private String major;
    
    /**
     * 班级
     */
    @Size(max = 50, message = "班级名称长度不能超过50个字符")
    private String className;
    
    /**
     * 入学日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate enrollmentDate;
    
    /**
     * 学籍状态
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
    
    /**
     * 获取状态显示名称
     */
    public String getStatusDisplayName() {
        switch (status) {
            case "在读": return "在读";
            case "毕业": return "毕业";
            case "退学": return "退学";
            case "休学": return "休学";
            default: return "未知";
        }
    }
    
    /**
     * 计算年龄
     */
    public Integer getAge() {
        if (birthDate == null) {
            return null;
        }
        return LocalDate.now().getYear() - birthDate.getYear();
    }
}
