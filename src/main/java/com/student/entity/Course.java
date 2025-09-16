package com.student.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 课程实体类
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    
    /**
     * 课程ID
     */
    private Long id;
    
    /**
     * 课程代码
     */
    @NotBlank(message = "课程代码不能为空")
    @Size(max = 20, message = "课程代码长度不能超过20个字符")
    private String courseCode;
    
    /**
     * 课程名称
     */
    @NotBlank(message = "课程名称不能为空")
    @Size(max = 100, message = "课程名称长度不能超过100个字符")
    private String courseName;
    
    /**
     * 学分
     */
    @DecimalMin(value = "0.5", message = "学分不能小于0.5")
    @DecimalMax(value = "10.0", message = "学分不能大于10.0")
    private BigDecimal credits;
    
    /**
     * 授课教师
     */
    @Size(max = 50, message = "教师姓名长度不能超过50个字符")
    private String teacher;
    
    /**
     * 学期
     */
    @Size(max = 20, message = "学期长度不能超过20个字符")
    private String semester;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
}
