package com.student.mapper;

import com.student.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学生数据访问层
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Mapper
public interface StudentMapper {
    
    /**
     * 根据学号查询学生
     * 
     * @param studentNo 学号
     * @return 学生信息
     */
    Student findByStudentNo(@Param("studentNo") String studentNo);
    
    /**
     * 根据ID查询学生
     * 
     * @param id 学生ID
     * @return 学生信息
     */
    Student findById(@Param("id") Long id);
    
    /**
     * 查询所有学生
     * 
     * @return 学生列表
     */
    List<Student> findAll();
    
    /**
     * 分页查询学生
     * 
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 学生列表
     */
    List<Student> findByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据姓名模糊查询学生
     * 
     * @param name 姓名
     * @return 学生列表
     */
    List<Student> findByNameLike(@Param("name") String name);
    
    /**
     * 根据专业查询学生
     * 
     * @param major 专业
     * @return 学生列表
     */
    List<Student> findByMajor(@Param("major") String major);
    
    /**
     * 根据班级查询学生
     * 
     * @param className 班级
     * @return 学生列表
     */
    List<Student> findByClassName(@Param("className") String className);
    
    /**
     * 根据学籍状态查询学生
     * 
     * @param status 学籍状态
     * @return 学生列表
     */
    List<Student> findByStatus(@Param("status") String status);
    
    /**
     * 多条件查询学生
     * 
     * @param studentNo 学号
     * @param name 姓名
     * @param major 专业
     * @param className 班级
     * @param status 学籍状态
     * @return 学生列表
     */
    List<Student> findByConditions(@Param("studentNo") String studentNo,
                                   @Param("name") String name,
                                   @Param("major") String major,
                                   @Param("className") String className,
                                   @Param("status") String status);
    
    /**
     * 统计学生总数
     * 
     * @return 学生总数
     */
    int countAll();
    
    /**
     * 根据条件统计学生数量
     * 
     * @param studentNo 学号
     * @param name 姓名
     * @param major 专业
     * @param className 班级
     * @param status 学籍状态
     * @return 学生数量
     */
    int countByConditions(@Param("studentNo") String studentNo,
                          @Param("name") String name,
                          @Param("major") String major,
                          @Param("className") String className,
                          @Param("status") String status);
    
    /**
     * 插入学生
     * 
     * @param student 学生信息
     * @return 影响行数
     */
    int insert(Student student);
    
    /**
     * 更新学生
     * 
     * @param student 学生信息
     * @return 影响行数
     */
    int update(Student student);
    
    /**
     * 根据ID删除学生
     * 
     * @param id 学生ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除学生
     * 
     * @param ids 学生ID列表
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);
    
    /**
     * 查询本月新增学生数量
     * 
     * @return 本月新增学生数量
     */
    int countNewStudentsThisMonth();
}
