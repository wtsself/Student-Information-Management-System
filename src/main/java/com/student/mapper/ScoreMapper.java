package com.student.mapper;

import com.student.entity.Score;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 成绩数据访问层
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Mapper
public interface ScoreMapper {
    
    /**
     * 根据ID查询成绩
     * 
     * @param id 成绩ID
     * @return 成绩信息
     */
    Score findById(@Param("id") Long id);
    
    /**
     * 根据学生ID和课程ID查询成绩
     * 
     * @param studentId 学生ID
     * @param courseId 课程ID
     * @return 成绩信息
     */
    Score findByStudentAndCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
    
    /**
     * 查询所有成绩
     * 
     * @return 成绩列表
     */
    List<Score> findAll();
    
    /**
     * 分页查询成绩
     * 
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 成绩列表
     */
    List<Score> findByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据学生ID查询成绩
     * 
     * @param studentId 学生ID
     * @return 成绩列表
     */
    List<Score> findByStudentId(@Param("studentId") Long studentId);
    
    /**
     * 根据课程ID查询成绩
     * 
     * @param courseId 课程ID
     * @return 成绩列表
     */
    List<Score> findByCourseId(@Param("courseId") Long courseId);
    
    /**
     * 根据学期查询成绩
     * 
     * @param semester 学期
     * @return 成绩列表
     */
    List<Score> findBySemester(@Param("semester") String semester);
    
    /**
     * 根据考试类型查询成绩
     * 
     * @param examType 考试类型
     * @return 成绩列表
     */
    List<Score> findByExamType(@Param("examType") String examType);
    
    /**
     * 多条件查询成绩
     * 
     * @param studentId 学生ID
     * @param courseId 课程ID
     * @param semester 学期
     * @param examType 考试类型
     * @return 成绩列表
     */
    List<Score> findByConditions(@Param("studentId") Long studentId,
                                  @Param("courseId") Long courseId,
                                  @Param("semester") String semester,
                                  @Param("examType") String examType);
    
    /**
     * 查询学生成绩统计信息
     * 
     * @param studentId 学生ID
     * @return 成绩统计信息
     */
    List<Score> findStudentScoreStats(@Param("studentId") Long studentId);
    
    /**
     * 查询课程成绩统计信息
     * 
     * @param courseId 课程ID
     * @return 成绩统计信息
     */
    List<Score> findCourseScoreStats(@Param("courseId") Long courseId);
    
    /**
     * 统计成绩总数
     * 
     * @return 成绩总数
     */
    int countAll();
    
    /**
     * 根据条件统计成绩数量
     * 
     * @param studentId 学生ID
     * @param courseId 课程ID
     * @param semester 学期
     * @param examType 考试类型
     * @return 成绩数量
     */
    int countByConditions(@Param("studentId") Long studentId,
                          @Param("courseId") Long courseId,
                          @Param("semester") String semester,
                          @Param("examType") String examType);
    
    /**
     * 插入成绩
     * 
     * @param score 成绩信息
     * @return 影响行数
     */
    int insert(Score score);
    
    /**
     * 更新成绩
     * 
     * @param score 成绩信息
     * @return 影响行数
     */
    int update(Score score);
    
    /**
     * 根据ID删除成绩
     * 
     * @param id 成绩ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 根据学生ID删除成绩
     * 
     * @param studentId 学生ID
     * @return 影响行数
     */
    int deleteByStudentId(@Param("studentId") Long studentId);
    
    /**
     * 根据课程ID删除成绩
     * 
     * @param courseId 课程ID
     * @return 影响行数
     */
    int deleteByCourseId(@Param("courseId") Long courseId);
    
    /**
     * 批量删除成绩
     * 
     * @param ids 成绩ID列表
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}
