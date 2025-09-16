package com.student.service;

import com.student.entity.Course;
import com.student.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程业务逻辑层
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Service
@Transactional
public class CourseService {
    
    @Autowired
    private CourseMapper courseMapper;
    
    /**
     * 根据课程代码查询课程
     * 
     * @param courseCode 课程代码
     * @return 课程信息
     */
    public Course findByCourseCode(String courseCode) {
        return courseMapper.findByCourseCode(courseCode);
    }
    
    /**
     * 根据ID查询课程
     * 
     * @param id 课程ID
     * @return 课程信息
     */
    public Course findById(Long id) {
        return courseMapper.findById(id);
    }
    
    /**
     * 查询所有课程
     * 
     * @return 课程列表
     */
    public List<Course> findAll() {
        return courseMapper.findAll();
    }
    
    /**
     * 分页查询课程
     * 
     * @param page 页码（从1开始）
     * @param size 每页大小
     * @return 课程列表
     */
    public List<Course> findByPage(int page, int size) {
        int offset = (page - 1) * size;
        return courseMapper.findByPage(offset, size);
    }
    
    /**
     * 根据课程名称模糊查询课程
     * 
     * @param courseName 课程名称
     * @return 课程列表
     */
    public List<Course> findByCourseNameLike(String courseName) {
        return courseMapper.findByCourseNameLike(courseName);
    }
    
    /**
     * 根据教师查询课程
     * 
     * @param teacher 教师
     * @return 课程列表
     */
    public List<Course> findByTeacher(String teacher) {
        return courseMapper.findByTeacher(teacher);
    }
    
    /**
     * 根据学期查询课程
     * 
     * @param semester 学期
     * @return 课程列表
     */
    public List<Course> findBySemester(String semester) {
        return courseMapper.findBySemester(semester);
    }
    
    /**
     * 多条件查询课程
     * 
     * @param courseCode 课程代码
     * @param courseName 课程名称
     * @param teacher 教师
     * @param semester 学期
     * @return 课程列表
     */
    public List<Course> findByConditions(String courseCode, String courseName, String teacher, String semester) {
        return courseMapper.findByConditions(courseCode, courseName, teacher, semester);
    }
    
    /**
     * 分页多条件查询课程
     * 
     * @param courseCode 课程代码
     * @param courseName 课程名称
     * @param teacher 教师
     * @param semester 学期
     * @param page 页码
     * @param size 每页大小
     * @return 课程列表
     */
    public List<Course> findByConditionsWithPage(String courseCode, String courseName, String teacher, String semester, int page, int size) {
        List<Course> allCourses = courseMapper.findByConditions(courseCode, courseName, teacher, semester);
        int offset = (page - 1) * size;
        int endIndex = Math.min(offset + size, allCourses.size());
        
        if (offset >= allCourses.size()) {
            return List.of();
        }
        
        return allCourses.subList(offset, endIndex);
    }
    
    /**
     * 统计课程总数
     * 
     * @return 课程总数
     */
    public int countAll() {
        return courseMapper.countAll();
    }
    
    /**
     * 根据条件统计课程数量
     * 
     * @param courseCode 课程代码
     * @param courseName 课程名称
     * @param teacher 教师
     * @param semester 学期
     * @return 课程数量
     */
    public int countByConditions(String courseCode, String courseName, String teacher, String semester) {
        return courseMapper.countByConditions(courseCode, courseName, teacher, semester);
    }
    
    /**
     * 创建课程
     * 
     * @param course 课程信息
     * @return 是否成功
     */
    public boolean createCourse(Course course) {
        // 检查课程代码是否已存在
        Course existingCourse = courseMapper.findByCourseCode(course.getCourseCode());
        if (existingCourse != null) {
            throw new RuntimeException("课程代码已存在");
        }
        
        return courseMapper.insert(course) > 0;
    }
    
    /**
     * 更新课程
     * 
     * @param course 课程信息
     * @return 是否成功
     */
    public boolean updateCourse(Course course) {
        // 检查课程是否存在
        Course existingCourse = courseMapper.findById(course.getId());
        if (existingCourse == null) {
            throw new RuntimeException("课程不存在");
        }
        
        // 如果课程代码发生变化，检查新课程代码是否已存在
        if (!existingCourse.getCourseCode().equals(course.getCourseCode())) {
            Course courseWithSameCode = courseMapper.findByCourseCode(course.getCourseCode());
            if (courseWithSameCode != null) {
                throw new RuntimeException("课程代码已存在");
            }
        }
        
        return courseMapper.update(course) > 0;
    }
    
    /**
     * 删除课程
     * 
     * @param id 课程ID
     * @return 是否成功
     */
    public boolean deleteCourse(Long id) {
        // 检查课程是否存在
        Course course = courseMapper.findById(id);
        if (course == null) {
            throw new RuntimeException("课程不存在");
        }
        
        return courseMapper.deleteById(id) > 0;
    }
    
    /**
     * 批量删除课程
     * 
     * @param ids 课程ID列表
     * @return 是否成功
     */
    public boolean deleteCourses(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        
        return courseMapper.deleteByIds(ids) > 0;
    }
    
    /**
     * 获取所有教师列表
     * 
     * @return 教师列表
     */
    public List<String> getAllTeachers() {
        List<Course> courses = courseMapper.findAll();
        return courses.stream()
                .map(Course::getTeacher)
                .filter(teacher -> teacher != null && !teacher.isEmpty())
                .distinct()
                .sorted()
                .toList();
    }
    
    /**
     * 获取所有学期列表
     * 
     * @return 学期列表
     */
    public List<String> getAllSemesters() {
        List<Course> courses = courseMapper.findAll();
        return courses.stream()
                .map(Course::getSemester)
                .filter(semester -> semester != null && !semester.isEmpty())
                .distinct()
                .sorted()
                .toList();
    }
}
