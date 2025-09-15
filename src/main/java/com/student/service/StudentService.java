package com.student.service;

import com.student.entity.Student;
import com.student.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 学生业务逻辑层
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Service
@Transactional
public class StudentService {
    
    @Autowired
    private StudentMapper studentMapper;
    
    /**
     * 根据学号查询学生
     * 
     * @param studentNo 学号
     * @return 学生信息
     */
    public Student findByStudentNo(String studentNo) {
        return studentMapper.findByStudentNo(studentNo);
    }
    
    /**
     * 根据ID查询学生
     * 
     * @param id 学生ID
     * @return 学生信息
     */
    public Student findById(Long id) {
        return studentMapper.findById(id);
    }
    
    /**
     * 查询所有学生
     * 
     * @return 学生列表
     */
    public List<Student> findAll() {
        return studentMapper.findAll();
    }
    
    /**
     * 分页查询学生
     * 
     * @param page 页码（从1开始）
     * @param size 每页大小
     * @return 学生列表
     */
    public List<Student> findByPage(int page, int size) {
        int offset = (page - 1) * size;
        return studentMapper.findByPage(offset, size);
    }
    
    /**
     * 根据姓名模糊查询学生
     * 
     * @param name 姓名
     * @return 学生列表
     */
    public List<Student> findByNameLike(String name) {
        return studentMapper.findByNameLike(name);
    }
    
    /**
     * 根据专业查询学生
     * 
     * @param major 专业
     * @return 学生列表
     */
    public List<Student> findByMajor(String major) {
        return studentMapper.findByMajor(major);
    }
    
    /**
     * 根据班级查询学生
     * 
     * @param className 班级
     * @return 学生列表
     */
    public List<Student> findByClassName(String className) {
        return studentMapper.findByClassName(className);
    }
    
    /**
     * 根据学籍状态查询学生
     * 
     * @param status 学籍状态
     * @return 学生列表
     */
    public List<Student> findByStatus(String status) {
        return studentMapper.findByStatus(status);
    }
    
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
    public List<Student> findByConditions(String studentNo, String name, String major, String className, String status) {
        return studentMapper.findByConditions(studentNo, name, major, className, status);
    }
    
    /**
     * 分页多条件查询学生
     * 
     * @param studentNo 学号
     * @param name 姓名
     * @param major 专业
     * @param className 班级
     * @param status 学籍状态
     * @param page 页码
     * @param size 每页大小
     * @return 学生列表
     */
    public List<Student> findByConditionsWithPage(String studentNo, String name, String major, String className, String status, int page, int size) {
        List<Student> allStudents = studentMapper.findByConditions(studentNo, name, major, className, status);
        int offset = (page - 1) * size;
        int endIndex = Math.min(offset + size, allStudents.size());
        
        if (offset >= allStudents.size()) {
            return List.of();
        }
        
        return allStudents.subList(offset, endIndex);
    }
    
    /**
     * 统计学生总数
     * 
     * @return 学生总数
     */
    public int countAll() {
        return studentMapper.countAll();
    }
    
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
    public int countByConditions(String studentNo, String name, String major, String className, String status) {
        return studentMapper.countByConditions(studentNo, name, major, className, status);
    }
    
    /**
     * 创建学生
     * 
     * @param student 学生信息
     * @return 是否成功
     */
    public boolean createStudent(Student student) {
        // 检查学号是否已存在
        Student existingStudent = studentMapper.findByStudentNo(student.getStudentNo());
        if (existingStudent != null) {
            throw new RuntimeException("学号已存在");
        }
        
        // 设置默认学籍状态
        if (student.getStatus() == null || student.getStatus().isEmpty()) {
            student.setStatus("在读");
        }
        
        return studentMapper.insert(student) > 0;
    }
    
    /**
     * 更新学生
     * 
     * @param student 学生信息
     * @return 是否成功
     */
    public boolean updateStudent(Student student) {
        // 检查学生是否存在
        Student existingStudent = studentMapper.findById(student.getId());
        if (existingStudent == null) {
            throw new RuntimeException("学生不存在");
        }
        
        // 如果学号发生变化，检查新学号是否已存在
        if (!existingStudent.getStudentNo().equals(student.getStudentNo())) {
            Student studentWithSameNo = studentMapper.findByStudentNo(student.getStudentNo());
            if (studentWithSameNo != null) {
                throw new RuntimeException("学号已存在");
            }
        }
        
        return studentMapper.update(student) > 0;
    }
    
    /**
     * 删除学生
     * 
     * @param id 学生ID
     * @return 是否成功
     */
    public boolean deleteStudent(Long id) {
        // 检查学生是否存在
        Student student = studentMapper.findById(id);
        if (student == null) {
            throw new RuntimeException("学生不存在");
        }
        
        return studentMapper.deleteById(id) > 0;
    }
    
    /**
     * 批量删除学生
     * 
     * @param ids 学生ID列表
     * @return 是否成功
     */
    public boolean deleteStudents(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        
        return studentMapper.deleteByIds(ids) > 0;
    }
    
    /**
     * 获取所有专业列表
     * 
     * @return 专业列表
     */
    public List<String> getAllMajors() {
        List<Student> students = studentMapper.findAll();
        return students.stream()
                .map(Student::getMajor)
                .filter(major -> major != null && !major.isEmpty())
                .distinct()
                .sorted()
                .toList();
    }
    
    /**
     * 获取所有班级列表
     * 
     * @return 班级列表
     */
    public List<String> getAllClassNames() {
        List<Student> students = studentMapper.findAll();
        return students.stream()
                .map(Student::getClassName)
                .filter(className -> className != null && !className.isEmpty())
                .distinct()
                .sorted()
                .toList();
    }
    
    /**
     * 获取本月新增学生数量
     * 
     * @return 本月新增学生数量
     */
    public int countNewStudentsThisMonth() {
        return studentMapper.countNewStudentsThisMonth();
    }
}
