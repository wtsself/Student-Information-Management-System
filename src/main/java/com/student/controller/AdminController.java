package com.student.controller;

import com.student.entity.*;
import com.student.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.student.service.CustomUserDetailsService;

import java.util.List;

/**
 * 管理员控制器
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private CourseService courseService;
    
    @Autowired
    private ScoreService scoreService;
    
    @Autowired
    private HealthRecordService healthRecordService;
    
    /**
     * 管理员仪表板
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // 获取统计数据
        int totalStudents = studentService.countAll();
        int totalCourses = courseService.countAll();
        int totalScores = scoreService.countAll();
        int totalHealthRecords = healthRecordService.countAll();
        int teacherCount = courseService.getAllTeachers().size();
        int newStudentsThisMonth = studentService.countNewStudentsThisMonth();
        
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("totalScores", totalScores);
        model.addAttribute("totalHealthRecords", totalHealthRecords);
        model.addAttribute("teacherCount", teacherCount);
        model.addAttribute("newStudentsThisMonth", newStudentsThisMonth);
        
        return "admin/dashboard";
    }
    
    // ==================== 用户管理 ====================
    
    /**
     * 用户列表页面
     */
    @GetMapping("/users")
    public String userList(@RequestParam(defaultValue = "1") int page,
                          @RequestParam(defaultValue = "10") int size,
                          Model model) {
        List<User> users = userService.findByPage(page, size);
        int totalUsers = userService.countAll();
        int totalPages = (int) Math.ceil((double) totalUsers / size);
        
        model.addAttribute("users", users);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalUsers", totalUsers);
        
        return "admin/users/list";
    }
    
    /**
     * 添加用户页面
     */
    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/users/form";
    }
    
    /**
     * 编辑用户页面
     */
    @GetMapping("/users/edit/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        if (user == null) {
            return "redirect:/admin/users";
        }
        model.addAttribute("user", user);
        return "admin/users/form";
    }
    
    /**
     * 保存用户
     */
    @PostMapping("/users/save")
    public String saveUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        try {
            if (user.getId() == null) {
                userService.createUser(user);
                redirectAttributes.addFlashAttribute("success", "用户创建成功");
            } else {
                userService.updateUser(user);
                redirectAttributes.addFlashAttribute("success", "用户更新成功");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }
    
    /**
     * 删除用户
     */
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("success", "用户删除成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/users";
    }
    
    // ==================== 学生管理 ====================
    
    /**
     * 学生列表页面
     */
    @GetMapping("/students")
    public String studentList(@RequestParam(defaultValue = "1") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(required = false) String studentNo,
                             @RequestParam(required = false) String name,
                             @RequestParam(required = false) String major,
                             @RequestParam(required = false) String className,
                             @RequestParam(required = false) String status,
                             Model model) {
        List<Student> students;
        int totalStudents;
        
        if (studentNo != null || name != null || major != null || className != null || status != null) {
            students = studentService.findByConditionsWithPage(studentNo, name, major, className, status, page, size);
            totalStudents = studentService.countByConditions(studentNo, name, major, className, status);
        } else {
            students = studentService.findByPage(page, size);
            totalStudents = studentService.countAll();
        }
        
        int totalPages = (int) Math.ceil((double) totalStudents / size);
        
        model.addAttribute("students", students);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("studentNo", studentNo);
        model.addAttribute("name", name);
        model.addAttribute("major", major);
        model.addAttribute("className", className);
        model.addAttribute("status", status);
        model.addAttribute("majors", studentService.getAllMajors());
        model.addAttribute("classNames", studentService.getAllClassNames());
        
        return "admin/students/list";
    }
    
    /**
     * 添加学生页面
     */
    @GetMapping("/students/add")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "admin/students/form";
    }
    
    /**
     * 编辑学生页面
     */
    @GetMapping("/students/edit/{id}")
    public String editStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.findById(id);
        if (student == null) {
            return "redirect:/admin/students";
        }
        model.addAttribute("student", student);
        return "admin/students/form";
    }
    
    /**
     * 保存学生
     */
    @PostMapping("/students/save")
    public String saveStudent(@ModelAttribute Student student, RedirectAttributes redirectAttributes) {
        try {
            if (student.getId() == null) {
                studentService.createStudent(student);
                redirectAttributes.addFlashAttribute("success", "学生创建成功");
            } else {
                studentService.updateStudent(student);
                redirectAttributes.addFlashAttribute("success", "学生更新成功");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/students";
    }
    
    /**
     * 删除学生
     */
    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("success", "学生删除成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/students";
    }
    
    // ==================== 课程管理 ====================
    
    /**
     * 课程列表页面
     */
    @GetMapping("/courses")
    public String courseList(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(required = false) String courseCode,
                            @RequestParam(required = false) String courseName,
                            @RequestParam(required = false) String teacher,
                            @RequestParam(required = false) String semester,
                            Model model) {
        List<Course> courses;
        int totalCourses;
        
        if (courseCode != null || courseName != null || teacher != null || semester != null) {
            courses = courseService.findByConditionsWithPage(courseCode, courseName, teacher, semester, page, size);
            totalCourses = courseService.countByConditions(courseCode, courseName, teacher, semester);
        } else {
            courses = courseService.findByPage(page, size);
            totalCourses = courseService.countAll();
        }
        
        int totalPages = (int) Math.ceil((double) totalCourses / size);
        
        model.addAttribute("courses", courses);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalCourses", totalCourses);
        model.addAttribute("courseCode", courseCode);
        model.addAttribute("courseName", courseName);
        model.addAttribute("teacher", teacher);
        model.addAttribute("semester", semester);
        model.addAttribute("teachers", courseService.getAllTeachers());
        model.addAttribute("semesters", courseService.getAllSemesters());
        
        return "admin/courses/list";
    }
    
    /**
     * 添加课程页面
     */
    @GetMapping("/courses/add")
    public String addCourseForm(Model model) {
        model.addAttribute("course", new Course());
        return "admin/courses/form";
    }
    
    /**
     * 编辑课程页面
     */
    @GetMapping("/courses/edit/{id}")
    public String editCourseForm(@PathVariable Long id, Model model) {
        Course course = courseService.findById(id);
        if (course == null) {
            return "redirect:/admin/courses";
        }
        model.addAttribute("course", course);
        return "admin/courses/form";
    }
    
    /**
     * 保存课程
     */
    @PostMapping("/courses/save")
    public String saveCourse(@ModelAttribute Course course, RedirectAttributes redirectAttributes) {
        try {
            if (course.getId() == null) {
                courseService.createCourse(course);
                redirectAttributes.addFlashAttribute("success", "课程创建成功");
            } else {
                courseService.updateCourse(course);
                redirectAttributes.addFlashAttribute("success", "课程更新成功");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/courses";
    }
    
    /**
     * 删除课程
     */
    @PostMapping("/courses/delete/{id}")
    public String deleteCourse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            courseService.deleteCourse(id);
            redirectAttributes.addFlashAttribute("success", "课程删除成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/courses";
    }
    
    // ==================== 成绩管理 ====================
    
    /**
     * 成绩列表页面
     */
    @GetMapping("/scores")
    public String scoreList(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "10") int size,
                           @RequestParam(required = false) Long studentId,
                           @RequestParam(required = false) Long courseId,
                           @RequestParam(required = false) String semester,
                           @RequestParam(required = false) String examType,
                           Model model) {
        List<Score> scores;
        int totalScores;
        
        if (studentId != null || courseId != null || semester != null || examType != null) {
            scores = scoreService.findByConditionsWithPage(studentId, courseId, semester, examType, page, size);
            totalScores = scoreService.countByConditions(studentId, courseId, semester, examType);
        } else {
            scores = scoreService.findByPage(page, size);
            totalScores = scoreService.countAll();
        }
        
        int totalPages = (int) Math.ceil((double) totalScores / size);
        
        model.addAttribute("scores", scores);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalScores", totalScores);
        model.addAttribute("studentId", studentId);
        model.addAttribute("courseId", courseId);
        model.addAttribute("semester", semester);
        model.addAttribute("examType", examType);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        model.addAttribute("semesters", courseService.getAllSemesters());
        
        return "admin/scores/list";
    }
    
    /**
     * 添加成绩页面
     */
    @GetMapping("/scores/add")
    public String addScoreForm(Model model) {
        model.addAttribute("score", new Score());
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "admin/scores/form";
    }
    
    /**
     * 编辑成绩页面
     */
    @GetMapping("/scores/edit/{id}")
    public String editScoreForm(@PathVariable Long id, Model model) {
        Score score = scoreService.findById(id);
        if (score == null) {
            return "redirect:/admin/scores";
        }
        model.addAttribute("score", score);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "admin/scores/form";
    }
    
    /**
     * 保存成绩
     */
    @PostMapping("/scores/save")
    public String saveScore(@ModelAttribute Score score, RedirectAttributes redirectAttributes) {
        try {
            if (score.getId() == null) {
                scoreService.createScore(score);
                redirectAttributes.addFlashAttribute("success", "成绩创建成功");
            } else {
                scoreService.updateScore(score);
                redirectAttributes.addFlashAttribute("success", "成绩更新成功");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/scores";
    }
    
    /**
     * 删除成绩
     */
    @PostMapping("/scores/delete/{id}")
    public String deleteScore(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            scoreService.deleteScore(id);
            redirectAttributes.addFlashAttribute("success", "成绩删除成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/scores";
    }
    
    // ==================== 健康信息管理 ====================
    
    /**
     * 健康信息列表页面
     */
    @GetMapping("/health-records")
    public String healthRecordList(@RequestParam(defaultValue = "1") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(required = false) String bloodType,
                                  @RequestParam(required = false) String startDate,
                                  @RequestParam(required = false) String endDate,
                                  Model model) {
        List<HealthRecord> healthRecords;
        int totalHealthRecords;
        
        if (bloodType != null || startDate != null || endDate != null) {
            healthRecords = healthRecordService.findByConditionsWithPage(bloodType, startDate, endDate, page, size);
            totalHealthRecords = healthRecordService.countByConditions(bloodType, startDate, endDate);
        } else {
            healthRecords = healthRecordService.findByPage(page, size);
            totalHealthRecords = healthRecordService.countAll();
        }
        
        int totalPages = (int) Math.ceil((double) totalHealthRecords / size);
        
        model.addAttribute("healthRecords", healthRecords);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalHealthRecords", totalHealthRecords);
        model.addAttribute("bloodType", bloodType);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("bloodTypes", healthRecordService.getAllBloodTypes());
        
        return "admin/health-records/list";
    }
    
    /**
     * 添加健康信息页面
     */
    @GetMapping("/health-records/add")
    public String addHealthRecordForm(Model model) {
        model.addAttribute("healthRecord", new HealthRecord());
        model.addAttribute("students", studentService.findAll());
        return "admin/health-records/form";
    }
    
    /**
     * 编辑健康信息页面
     */
    @GetMapping("/health-records/edit/{id}")
    public String editHealthRecordForm(@PathVariable Long id, Model model) {
        HealthRecord healthRecord = healthRecordService.findById(id);
        if (healthRecord == null) {
            return "redirect:/admin/health-records";
        }
        model.addAttribute("healthRecord", healthRecord);
        model.addAttribute("students", studentService.findAll());
        return "admin/health-records/form";
    }
    
    /**
     * 保存健康信息
     */
    @PostMapping("/health-records/save")
    public String saveHealthRecord(@ModelAttribute HealthRecord healthRecord, RedirectAttributes redirectAttributes) {
        try {
            if (healthRecord.getId() == null) {
                healthRecordService.createHealthRecord(healthRecord);
                redirectAttributes.addFlashAttribute("success", "健康信息创建成功");
            } else {
                healthRecordService.updateHealthRecord(healthRecord);
                redirectAttributes.addFlashAttribute("success", "健康信息更新成功");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/health-records";
    }
    
    /**
     * 删除健康信息
     */
    @PostMapping("/health-records/delete/{id}")
    public String deleteHealthRecord(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            healthRecordService.deleteHealthRecord(id);
            redirectAttributes.addFlashAttribute("success", "健康信息删除成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/health-records";
    }
    
    /**
     * 健康统计页面
     */
    @GetMapping("/health-statistics")
    public String healthStatistics(Model model) {
        HealthRecordService.HealthStatistics stats = healthRecordService.getHealthStatistics();
        model.addAttribute("stats", stats);
        return "admin/health-records/statistics";
    }
}
