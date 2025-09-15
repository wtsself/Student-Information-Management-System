package com.student.mapper;

import com.student.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程数据访问层
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Mapper
public interface CourseMapper {
    
    /**
     * 根据课程代码查询课程
     * 
     * @param courseCode 课程代码
     * @return 课程信息
     */
    Course findByCourseCode(@Param("courseCode") String courseCode);
    
    /**
     * 根据ID查询课程
     * 
     * @param id 课程ID
     * @return 课程信息
     */
    Course findById(@Param("id") Long id);
    
    /**
     * 查询所有课程
     * 
     * @return 课程列表
     */
    List<Course> findAll();
    
    /**
     * 分页查询课程
     * 
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 课程列表
     */
    List<Course> findByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据课程名称模糊查询课程
     * 
     * @param courseName 课程名称
     * @return 课程列表
     */
    List<Course> findByCourseNameLike(@Param("courseName") String courseName);
    
    /**
     * 根据教师查询课程
     * 
     * @param teacher 教师
     * @return 课程列表
     */
    List<Course> findByTeacher(@Param("teacher") String teacher);
    
    /**
     * 根据学期查询课程
     * 
     * @param semester 学期
     * @return 课程列表
     */
    List<Course> findBySemester(@Param("semester") String semester);
    
    /**
     * 多条件查询课程
     * 
     * @param courseCode 课程代码
     * @param courseName 课程名称
     * @param teacher 教师
     * @param semester 学期
     * @return 课程列表
     */
    List<Course> findByConditions(@Param("courseCode") String courseCode,
                                   @Param("courseName") String courseName,
                                   @Param("teacher") String teacher,
                                   @Param("semester") String semester);
    
    /**
     * 统计课程总数
     * 
     * @return 课程总数
     */
    int countAll();
    
    /**
     * 根据条件统计课程数量
     * 
     * @param courseCode 课程代码
     * @param courseName 课程名称
     * @param teacher 教师
     * @param semester 学期
     * @return 课程数量
     */
    int countByConditions(@Param("courseCode") String courseCode,
                          @Param("courseName") String courseName,
                          @Param("teacher") String teacher,
                          @Param("semester") String semester);
    
    /**
     * 插入课程
     * 
     * @param course 课程信息
     * @return 影响行数
     */
    int insert(Course course);
    
    /**
     * 更新课程
     * 
     * @param course 课程信息
     * @return 影响行数
     */
    int update(Course course);
    
    /**
     * 根据ID删除课程
     * 
     * @param id 课程ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 批量删除课程
     * 
     * @param ids 课程ID列表
     * @return 影响行数
     */
    int deleteByIds(@Param("ids") List<Long> ids);
}
