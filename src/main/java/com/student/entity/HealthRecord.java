package com.student.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

/**
 * 健康信息实体类
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthRecord {
    
    /**
     * 健康记录ID
     */
    private Long id;
    
    /**
     * 学生ID
     */
    @NotNull(message = "学生ID不能为空")
    private Long studentId;
    
    /**
     * 身高(cm)
     */
    @DecimalMin(value = "50.0", message = "身高不能小于50cm")
    @DecimalMax(value = "250.0", message = "身高不能大于250cm")
    private BigDecimal height;
    
    /**
     * 体重(kg)
     */
    @DecimalMin(value = "10.0", message = "体重不能小于10kg")
    @DecimalMax(value = "200.0", message = "体重不能大于200kg")
    private BigDecimal weight;
    
    /**
     * 血型
     */
    private String bloodType;
    
    /**
     * 左眼视力
     */
    @DecimalMin(value = "0.1", message = "左眼视力不能小于0.1")
    @DecimalMax(value = "2.0", message = "左眼视力不能大于2.0")
    private BigDecimal visionLeft;
    
    /**
     * 右眼视力
     */
    @DecimalMin(value = "0.1", message = "右眼视力不能小于0.1")
    @DecimalMax(value = "2.0", message = "右眼视力不能大于2.0")
    private BigDecimal visionRight;
    
    /**
     * 收缩压
     */
    @DecimalMin(value = "60", message = "收缩压不能小于60")
    @DecimalMax(value = "250", message = "收缩压不能大于250")
    private Integer bloodPressureSystolic;
    
    /**
     * 舒张压
     */
    @DecimalMin(value = "40", message = "舒张压不能小于40")
    @DecimalMax(value = "150", message = "舒张压不能大于150")
    private Integer bloodPressureDiastolic;
    
    /**
     * 心率
     */
    @DecimalMin(value = "40", message = "心率不能小于40")
    @DecimalMax(value = "200", message = "心率不能大于200")
    private Integer heartRate;
    
    /**
     * 病史
     */
    @Size(max = 1000, message = "病史描述不能超过1000个字符")
    private String medicalHistory;
    
    /**
     * 过敏史
     */
    @Size(max = 1000, message = "过敏史描述不能超过1000个字符")
    private String allergies;
    
    /**
     * 紧急联系人
     */
    @Size(max = 50, message = "紧急联系人姓名不能超过50个字符")
    private String emergencyContact;
    
    /**
     * 紧急联系电话
     */
    @Size(max = 20, message = "紧急联系电话不能超过20个字符")
    private String emergencyPhone;
    
    /**
     * 体检日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate checkDate;
    
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
     * 获取血型显示名称
     */
    public String getBloodTypeDisplayName() {
        switch (bloodType) {
            case "A": return "A型";
            case "B": return "B型";
            case "AB": return "AB型";
            case "O": return "O型";
            default: return "未知";
        }
    }
    
    /**
     * 计算BMI指数
     */
    public BigDecimal calculateBMI() {
        if (height == null || weight == null || height.doubleValue() <= 0) {
            return null;
        }
        
        double heightInMeters = height.doubleValue() / 100.0;
        double bmi = weight.doubleValue() / (heightInMeters * heightInMeters);
        return new BigDecimal(bmi).setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 获取BMI等级
     */
    public String getBMICategory() {
        BigDecimal bmi = calculateBMI();
        if (bmi == null) {
            return "无法计算";
        }
        
        double bmiValue = bmi.doubleValue();
        if (bmiValue < 18.5) {
            return "偏瘦";
        } else if (bmiValue < 24) {
            return "正常";
        } else if (bmiValue < 28) {
            return "偏胖";
        } else {
            return "肥胖";
        }
    }
    
    /**
     * 获取血压等级
     */
    public String getBloodPressureCategory() {
        if (bloodPressureSystolic == null || bloodPressureDiastolic == null) {
            return "未测量";
        }
        
        int systolic = bloodPressureSystolic;
        int diastolic = bloodPressureDiastolic;
        
        if (systolic < 90 || diastolic < 60) {
            return "低血压";
        } else if (systolic < 120 && diastolic < 80) {
            return "正常";
        } else if (systolic < 140 && diastolic < 90) {
            return "正常高值";
        } else if (systolic < 160 && diastolic < 100) {
            return "高血压1级";
        } else if (systolic < 180 && diastolic < 110) {
            return "高血压2级";
        } else {
            return "高血压3级";
        }
    }
}
