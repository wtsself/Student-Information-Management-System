package com.student.service;

import com.student.entity.HealthRecord;
import com.student.mapper.HealthRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 健康信息业务逻辑层
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Service
@Transactional
public class HealthRecordService {
    
    @Autowired
    private HealthRecordMapper healthRecordMapper;
    
    /**
     * 根据学生ID查询健康记录
     * 
     * @param studentId 学生ID
     * @return 健康记录
     */
    public HealthRecord findByStudentId(Long studentId) {
        return healthRecordMapper.findByStudentId(studentId);
    }
    
    /**
     * 根据ID查询健康记录
     * 
     * @param id 健康记录ID
     * @return 健康记录
     */
    public HealthRecord findById(Long id) {
        return healthRecordMapper.findById(id);
    }
    
    /**
     * 查询所有健康记录
     * 
     * @return 健康记录列表
     */
    public List<HealthRecord> findAll() {
        return healthRecordMapper.findAll();
    }
    
    /**
     * 分页查询健康记录
     * 
     * @param page 页码（从1开始）
     * @param size 每页大小
     * @return 健康记录列表
     */
    public List<HealthRecord> findByPage(int page, int size) {
        int offset = (page - 1) * size;
        return healthRecordMapper.findByPage(offset, size);
    }
    
    /**
     * 根据血型查询健康记录
     * 
     * @param bloodType 血型
     * @return 健康记录列表
     */
    public List<HealthRecord> findByBloodType(String bloodType) {
        return healthRecordMapper.findByBloodType(bloodType);
    }
    
    /**
     * 根据体检日期范围查询健康记录
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 健康记录列表
     */
    public List<HealthRecord> findByCheckDateRange(String startDate, String endDate) {
        return healthRecordMapper.findByCheckDateRange(startDate, endDate);
    }
    
    /**
     * 多条件查询健康记录
     * 
     * @param bloodType 血型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 健康记录列表
     */
    public List<HealthRecord> findByConditions(String bloodType, String startDate, String endDate) {
        return healthRecordMapper.findByConditions(bloodType, startDate, endDate);
    }
    
    /**
     * 分页多条件查询健康记录
     * 
     * @param bloodType 血型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param page 页码
     * @param size 每页大小
     * @return 健康记录列表
     */
    public List<HealthRecord> findByConditionsWithPage(String bloodType, String startDate, String endDate, int page, int size) {
        List<HealthRecord> allRecords = healthRecordMapper.findByConditions(bloodType, startDate, endDate);
        int offset = (page - 1) * size;
        int endIndex = Math.min(offset + size, allRecords.size());
        
        if (offset >= allRecords.size()) {
            return List.of();
        }
        
        return allRecords.subList(offset, endIndex);
    }
    
    /**
     * 统计健康记录总数
     * 
     * @return 健康记录总数
     */
    public int countAll() {
        return healthRecordMapper.countAll();
    }
    
    /**
     * 根据条件统计健康记录数量
     * 
     * @param bloodType 血型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 健康记录数量
     */
    public int countByConditions(String bloodType, String startDate, String endDate) {
        return healthRecordMapper.countByConditions(bloodType, startDate, endDate);
    }
    
    /**
     * 创建健康记录
     * 
     * @param healthRecord 健康记录信息
     * @return 是否成功
     */
    public boolean createHealthRecord(HealthRecord healthRecord) {
        // 检查该学生是否已有健康记录
        HealthRecord existingRecord = healthRecordMapper.findByStudentId(healthRecord.getStudentId());
        if (existingRecord != null) {
            throw new RuntimeException("该学生已有健康记录，请使用更新功能");
        }
        
        return healthRecordMapper.insert(healthRecord) > 0;
    }
    
    /**
     * 更新健康记录
     * 
     * @param healthRecord 健康记录信息
     * @return 是否成功
     */
    public boolean updateHealthRecord(HealthRecord healthRecord) {
        // 检查健康记录是否存在
        HealthRecord existingRecord = healthRecordMapper.findById(healthRecord.getId());
        if (existingRecord == null) {
            throw new RuntimeException("健康记录不存在");
        }
        
        return healthRecordMapper.update(healthRecord) > 0;
    }
    
    /**
     * 删除健康记录
     * 
     * @param id 健康记录ID
     * @return 是否成功
     */
    public boolean deleteHealthRecord(Long id) {
        // 检查健康记录是否存在
        HealthRecord healthRecord = healthRecordMapper.findById(id);
        if (healthRecord == null) {
            throw new RuntimeException("健康记录不存在");
        }
        
        return healthRecordMapper.deleteById(id) > 0;
    }
    
    /**
     * 根据学生ID删除健康记录
     * 
     * @param studentId 学生ID
     * @return 是否成功
     */
    public boolean deleteByStudentId(Long studentId) {
        return healthRecordMapper.deleteByStudentId(studentId) > 0;
    }
    
    /**
     * 批量删除健康记录
     * 
     * @param ids 健康记录ID列表
     * @return 是否成功
     */
    public boolean deleteHealthRecords(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        
        return healthRecordMapper.deleteByIds(ids) > 0;
    }
    
    /**
     * 获取所有血型列表
     * 
     * @return 血型列表
     */
    public List<String> getAllBloodTypes() {
        List<HealthRecord> records = healthRecordMapper.findAll();
        return records.stream()
                .map(HealthRecord::getBloodType)
                .filter(bloodType -> bloodType != null && !bloodType.isEmpty())
                .distinct()
                .sorted()
                .toList();
    }
    
    /**
     * 获取健康统计信息
     * 
     * @return 健康统计信息
     */
    public HealthStatistics getHealthStatistics() {
        List<HealthRecord> records = healthRecordMapper.findAll();
        
        HealthStatistics stats = new HealthStatistics();
        stats.setTotalRecords(records.size());
        
        // 血型统计
        long aType = records.stream().filter(r -> "A".equals(r.getBloodType())).count();
        long bType = records.stream().filter(r -> "B".equals(r.getBloodType())).count();
        long abType = records.stream().filter(r -> "AB".equals(r.getBloodType())).count();
        long oType = records.stream().filter(r -> "O".equals(r.getBloodType())).count();
        
        stats.setBloodTypeA(aType);
        stats.setBloodTypeB(bType);
        stats.setBloodTypeAB(abType);
        stats.setBloodTypeO(oType);
        
        // BMI统计
        long normalBMI = records.stream().filter(r -> "正常".equals(r.getBMICategory())).count();
        long overweightBMI = records.stream().filter(r -> "偏胖".equals(r.getBMICategory())).count();
        long underweightBMI = records.stream().filter(r -> "偏瘦".equals(r.getBMICategory())).count();
        long obeseBMI = records.stream().filter(r -> "肥胖".equals(r.getBMICategory())).count();
        
        stats.setNormalBMI(normalBMI);
        stats.setOverweightBMI(overweightBMI);
        stats.setUnderweightBMI(underweightBMI);
        stats.setObeseBMI(obeseBMI);
        
        return stats;
    }
    
    /**
     * 健康统计信息内部类
     */
    public static class HealthStatistics {
        private int totalRecords;
        private long bloodTypeA;
        private long bloodTypeB;
        private long bloodTypeAB;
        private long bloodTypeO;
        private long normalBMI;
        private long overweightBMI;
        private long underweightBMI;
        private long obeseBMI;
        
        // Getters and Setters
        public int getTotalRecords() { return totalRecords; }
        public void setTotalRecords(int totalRecords) { this.totalRecords = totalRecords; }
        
        public long getBloodTypeA() { return bloodTypeA; }
        public void setBloodTypeA(long bloodTypeA) { this.bloodTypeA = bloodTypeA; }
        
        public long getBloodTypeB() { return bloodTypeB; }
        public void setBloodTypeB(long bloodTypeB) { this.bloodTypeB = bloodTypeB; }
        
        public long getBloodTypeAB() { return bloodTypeAB; }
        public void setBloodTypeAB(long bloodTypeAB) { this.bloodTypeAB = bloodTypeAB; }
        
        public long getBloodTypeO() { return bloodTypeO; }
        public void setBloodTypeO(long bloodTypeO) { this.bloodTypeO = bloodTypeO; }
        
        public long getNormalBMI() { return normalBMI; }
        public void setNormalBMI(long normalBMI) { this.normalBMI = normalBMI; }
        
        public long getOverweightBMI() { return overweightBMI; }
        public void setOverweightBMI(long overweightBMI) { this.overweightBMI = overweightBMI; }
        
        public long getUnderweightBMI() { return underweightBMI; }
        public void setUnderweightBMI(long underweightBMI) { this.underweightBMI = underweightBMI; }
        
        public long getObeseBMI() { return obeseBMI; }
        public void setObeseBMI(long obeseBMI) { this.obeseBMI = obeseBMI; }
    }
}
