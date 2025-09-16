package com.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.student.entity.User;
import com.student.service.CustomUserDetailsService;
import com.student.service.UserService;

/**
 * 主控制器
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Controller
public class MainController {
    
    @Autowired
    private UserService userService;
    
    /**
     * 首页
     */
    @GetMapping("/")
    public String index() {
        return "redirect:/dashboard";
    }
    
    /**
     * 仪表板
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() 
            && !"anonymousUser".equals(authentication.getPrincipal())) {
            
            CustomUserDetailsService.CustomUserPrincipal userPrincipal = 
                (CustomUserDetailsService.CustomUserPrincipal) authentication.getPrincipal();
            
            String role = userPrincipal.getUser().getRole();
            if ("ADMIN".equals(role)) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/student/dashboard";
            }
        }
        return "redirect:/login";
    }
    
    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                       @RequestParam(value = "logout", required = false) String logout,
                       Model model) {
        if (error != null) {
            model.addAttribute("error", "用户名或密码错误");
        }
        if (logout != null) {
            model.addAttribute("message", "您已成功退出登录");
        }
        return "login";
    }
    
    /**
     * 注册页面
     */
    @GetMapping("/register")
    public String register(@RequestParam(value = "error", required = false) String error,
                          @RequestParam(value = "message", required = false) String message,
                          Model model) {
        if (error != null) {
            model.addAttribute("error", error);
        }
        if (message != null) {
            model.addAttribute("message", message);
        }
        return "register";
    }
    
    /**
     * 处理注册请求
     */
    @PostMapping("/register")
    public String handleRegister(@RequestParam String username,
                                @RequestParam String password,
                                @RequestParam String confirmPassword,
                                @RequestParam String email,
                                @RequestParam(required = false) String phone,
                                @RequestParam String role,
                                RedirectAttributes redirectAttributes) {
        try {
            // 验证密码匹配
            if (!password.equals(confirmPassword)) {
                redirectAttributes.addAttribute("error", "密码和确认密码不匹配");
                return "redirect:/register";
            }
            
            // 验证用户名长度
            if (username.length() < 3 || username.length() > 50) {
                redirectAttributes.addAttribute("error", "用户名长度必须在3-50个字符之间");
                return "redirect:/register";
            }
            
            // 验证密码长度
            if (password.length() < 6 || password.length() > 255) {
                redirectAttributes.addAttribute("error", "密码长度必须在6-255个字符之间");
                return "redirect:/register";
            }
            
            // 验证邮箱格式
            if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                redirectAttributes.addAttribute("error", "邮箱格式不正确");
                return "redirect:/register";
            }
            
            // 验证角色
            if (!"USER".equals(role)) {
                redirectAttributes.addAttribute("error", "只能注册普通用户角色");
                return "redirect:/register";
            }
            
            // 创建用户对象
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setPhone(phone);
            user.setRole(role);
            user.setStatus(1); // 默认启用
            
            // 保存用户
            boolean success = userService.createUser(user);
            if (success) {
                redirectAttributes.addAttribute("message", "注册成功！请使用您的账号登录");
                return "redirect:/login";
            } else {
                redirectAttributes.addAttribute("error", "注册失败，请稍后重试");
                return "redirect:/register";
            }
            
        } catch (RuntimeException e) {
            redirectAttributes.addAttribute("error", e.getMessage());
            return "redirect:/register";
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "注册过程中发生错误，请稍后重试");
            return "redirect:/register";
        }
    }
    
    /**
     * 访问被拒绝页面
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error/403";
    }
}
