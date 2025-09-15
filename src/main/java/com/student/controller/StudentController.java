package com.student.controller;

import com.student.entity.*;
import com.student.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.student.service.CustomUserDetailsService;

import java.util.List;

/**
 * 学生控制器
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private ScoreService scoreService;
    
    @Autowired
    private HealthRecordService healthRecordService;
    
    /**
     * 学生仪表板
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 获取当前登录用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService.CustomUserPrincipal userPrincipal = 
            (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
        
        String username = userPrincipal.getUsername();
        
        // 根据用户名查找学生信息（这里假设用户名就是学号）
        Student student = studentService.findByStudentNo(username);
        if (student == null) {
            model.addAttribute("error", "未找到对应的学生信息");
            return "student/dashboard";
        }
        
        // 获取学生成绩信息
        List<Score> scores = scoreService.findByStudentId(student.getId());
        
        // 获取学生健康信息
        HealthRecord healthRecord = healthRecordService.findByStudentId(student.getId());
        
        // 计算平均绩点
        java.math.BigDecimal gpa = scoreService.calculateStudentGPA(student.getId());
        
        // 添加全局统计数据到模型中，确保base.html中的统计卡片能正确显示
        model.addAttribute("totalStudents", studentService.countAll());
        model.addAttribute("totalCourses", courseService.countAll());
        
        model.addAttribute("student", student);
        model.addAttribute("scores", scores);
        model.addAttribute("healthRecord", healthRecord);
        model.addAttribute("gpa", gpa);
        
        return "student/dashboard";
    }
    
    /**
     * 个人信息页面
     */
    @GetMapping("/profile")
    public String profile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService.CustomUserPrincipal userPrincipal = 
            (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
        
        String username = userPrincipal.getUsername();
        Student student = studentService.findByStudentNo(username);
        
        if (student == null) {
            model.addAttribute("error", "未找到对应的学生信息");
            return "student/profile";
        }
        
        model.addAttribute("student", student);
        return "student/profile";
    }
    
    /**
     * 成绩查询页面
     */
    @GetMapping("/scores")
    public String scores(@RequestParam(required = false) String semester,
                        @RequestParam(required = false) String examType,
                        Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService.CustomUserPrincipal userPrincipal = 
            (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
        
        String username = userPrincipal.getUsername();
        Student student = studentService.findByStudentNo(username);
        
        if (student == null) {
            model.addAttribute("error", "未找到对应的学生信息");
            return "student/scores";
        }
        
        List<Score> scores;
        if (semester != null || examType != null) {
            scores = scoreService.findByConditions(student.getId(), null, semester, examType);
        } else {
            scores = scoreService.findByStudentId(student.getId());
        }
        
        // 计算平均绩点
        java.math.BigDecimal gpa = scoreService.calculateStudentGPA(student.getId());
        
        model.addAttribute("student", student);
        model.addAttribute("scores", scores);
        model.addAttribute("gpa", gpa);
        model.addAttribute("semester", semester);
        model.addAttribute("examType", examType);
        model.addAttribute("semesters", courseService.getAllSemesters());
        
        return "student/scores";
    }
    
    /**
     * 健康信息页面
     */
    @GetMapping("/health")
    public String health(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService.CustomUserPrincipal userPrincipal = 
            (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
        
        String username = userPrincipal.getUsername();
        Student student = studentService.findByStudentNo(username);
        
        if (student == null) {
            model.addAttribute("error", "未找到对应的学生信息");
            return "student/health";
        }
        
        HealthRecord healthRecord = healthRecordService.findByStudentId(student.getId());
        
        model.addAttribute("student", student);
        model.addAttribute("healthRecord", healthRecord);
        
        return "student/health";
    }
    
    /**
     * 课程信息页面
     */
    @GetMapping("/courses")
    public String courses(@RequestParam(required = false) String semester,
                         Model model) {
        List<Course> courses;
        if (semester != null) {
            courses = courseService.findBySemester(semester);
        } else {
            courses = courseService.findAll();
        }
        
        model.addAttribute("courses", courses);
        model.addAttribute("semester", semester);
        model.addAttribute("semesters", courseService.getAllSemesters());
        
        return "student/courses";
    }
    
    /**
     * 成绩统计页面
     */
    @GetMapping("/score-statistics")
    public String scoreStatistics(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetailsService.CustomUserPrincipal userPrincipal = 
            (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
        
        String username = userPrincipal.getUsername();
        Student student = studentService.findByStudentNo(username);
        
        if (student == null) {
            model.addAttribute("error", "未找到对应的学生信息");
            return "student/score-statistics";
        }
        
        List<Score> scores = scoreService.findByStudentId(student.getId());
        
        // 计算各种统计信息
        java.math.BigDecimal gpa = scoreService.calculateStudentGPA(student.getId());
        
        // 按学期统计
        java.util.Map<String, java.util.List<Score>> scoresBySemester = scores.stream()
            .collect(java.util.stream.Collectors.groupingBy(Score::getSemester));
        
        // 按课程统计
        java.util.Map<String, java.util.List<Score>> scoresByCourse = scores.stream()
            .collect(java.util.stream.Collectors.groupingBy(score -> score.getCourse().getCourseName()));
        
        model.addAttribute("student", student);
        model.addAttribute("scores", scores);
        model.addAttribute("gpa", gpa);
        model.addAttribute("scoresBySemester", scoresBySemester);
        model.addAttribute("scoresByCourse", scoresByCourse);
        
        return "student/score-statistics";
    }
}
