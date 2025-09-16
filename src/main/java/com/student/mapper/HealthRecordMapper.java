package com.student.mapper;

import com.student.entity.HealthRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 健康信息数据访问层
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Mapper
public interface HealthRecordMapper {
    
    /**
     * 根据学生ID查询健康记录
     * 
     * @param studentId 学生ID
     * @return 健康记录
     */
    HealthRecord findByStudentId(@Param("studentId") Long studentId);
    
    /**
     * 根据ID查询健康记录
     * 
     * @param id 健康记录ID
     * @return 健康记录
     */
    HealthRecord findById(@Param("id") Long id);
    
    /**
     * 查询所有健康记录
     * 
     * @return 健康记录列表
     */
    List<HealthRecord> findAll();
    
    /**
     * 分页查询健康记录
     * 
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 健康记录列表
     */
    List<HealthRecord> findByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据血型查询健康记录
     * 
     * @param bloodType 血型
     * @return 健康记录列表
     */
    List<HealthRecord> findByBloodType(@Param("bloodType") String bloodType);
    
    /**
     * 根据体检日期范围查询健康记录
     * 
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 健康记录列表
     */
    List<HealthRecord> findByCheckDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);
    
    /**
     * 多条件查询健康记录
     * 
     * @param bloodType 血型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 健康记录列表
     */
    List<HealthRecord> findByConditions(@Param("bloodType") String bloodType,
                                        @Param("startDate") String startDate,
                                        @Param("endDate") String endDate);
    
    /**
     * 统计健康记录总数
     * 
     * @return 健康记录总数
     */
    int countAll();
    
    /**
     * 根据条件统计健康记录数量
     * 
     * @param bloodType 血型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 健康记录数量
     */
    int countByConditions(@Param("bloodType") String bloodType,
                          @Param("startDate") String startDate,
                          @Param("endDate") String endDate);
    
    /**
     * 插入健康记录
     * 
     * @param healthRecord 健康记录信息
     * @return 影响行数
     */
    int insert(HealthRecord healthRecord);
    
    /**
     * 更新健康记录
     * 
     * @param healthRecord 健康记录信息
     * @return 影响行数
     */
    int update(HealthRecord healthRecord);
    
    /**
     * 根据ID删除健康记录
     * 
     * @param id 健康记录ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据学生ID删除健康记录
     * 
     * @param studentId 学生ID
     * @return 影响行数
     */
    int deleteByStudentId(@Param("studentId") Long studentId);
    
    /**
     * 批量删除健康记录
     * 
     * @param ids 健康记录ID列表
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}
