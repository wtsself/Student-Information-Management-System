package com.student.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 成绩实体类
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    
    /**
     * 成绩ID
     */
    private Long id;
    
    /**
     * 学生ID
     */
    @NotNull(message = "学生ID不能为空")
    private Long studentId;
    
    /**
     * 课程ID
     */
    @NotNull(message = "课程ID不能为空")
    private Long courseId;
    
    /**
     * 成绩
     */
    @DecimalMin(value = "0.0", message = "成绩不能小于0")
    @DecimalMax(value = "100.0", message = "成绩不能大于100")
    private BigDecimal score;
    
    /**
     * 绩点
     */
    @DecimalMin(value = "0.0", message = "绩点不能小于0")
    @DecimalMax(value = "5.0", message = "绩点不能大于5")
    private BigDecimal gradePoint;
    
    /**
     * 考试类型
     */
    private String examType;
    
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
    
    // 关联对象
    /**
     * 学生信息
     */
    private Student student;
    
    /**
     * 课程信息
     */
    private Course course;
    
    /**
     * 获取考试类型显示名称
     */
    public String getExamTypeDisplayName() {
        switch (examType) {
            case "期末考试": return "期末考试";
            case "期中考试": return "期中考试";
            case "平时成绩": return "平时成绩";
            case "总评": return "总评";
            default: return "未知";
        }
    }
    
    /**
     * 获取成绩等级
     */
    public String getGradeLevel() {
        if (score == null) {
            return "未评分";
        }
        
        double scoreValue = score.doubleValue();
        if (scoreValue >= 90) {
            return "优秀";
        } else if (scoreValue >= 80) {
            return "良好";
        } else if (scoreValue >= 70) {
            return "中等";
        } else if (scoreValue >= 60) {
            return "及格";
        } else {
            return "不及格";
        }
    }
    
    /**
     * 计算绩点（基于成绩）
     */
    public BigDecimal calculateGradePoint() {
        if (score == null) {
            return BigDecimal.ZERO;
        }
        
        double scoreValue = score.doubleValue();
        if (scoreValue >= 90) {
            return new BigDecimal("4.0");
        } else if (scoreValue >= 80) {
            return new BigDecimal("3.0");
        } else if (scoreValue >= 70) {
            return new BigDecimal("2.0");
        } else if (scoreValue >= 60) {
            return new BigDecimal("1.0");
        } else {
            return BigDecimal.ZERO;
        }
    }
}
