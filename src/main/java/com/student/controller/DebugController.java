package com.student.controller;

import com.student.entity.User;
import com.student.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 调试控制器 - 用于调试登录问题
 * 注意：生产环境中应该删除此控制器
 */
@Controller
public class DebugController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    /**
     * 调试用户信息
     */
    @GetMapping("/debug/user")
    public String debugUser(@RequestParam String username, Model model) {
        try {
            User user = userService.findByUsername(username);
            if (user != null) {
                model.addAttribute("user", user);
                model.addAttribute("userExists", true);
                
                // 测试密码验证
                String testPassword = "admin123";
                boolean passwordMatch = passwordEncoder.matches(testPassword, user.getPassword());
                model.addAttribute("testPassword", testPassword);
                model.addAttribute("passwordMatch", passwordMatch);
                
                // 显示加密后的密码（用于调试）
                model.addAttribute("storedPassword", user.getPassword());
                
            } else {
                model.addAttribute("userExists", false);
                model.addAttribute("username", username);
            }
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        
        return "debug/user";
    }
    
    /**
     * 测试密码加密
     */
    @GetMapping("/debug/password")
    public String debugPassword(@RequestParam String password, Model model) {
        try {
            String encodedPassword = passwordEncoder.encode(password);
            model.addAttribute("originalPassword", password);
            model.addAttribute("encodedPassword", encodedPassword);
            
            // 验证密码
            boolean matches = passwordEncoder.matches(password, encodedPassword);
            model.addAttribute("matches", matches);
            
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        
        return "debug/password";
    }
    
    /**
     * 重置admin密码
     */
    @GetMapping("/debug/reset-admin")
    public String resetAdminPassword(Model model) {
        try {
            // 查找admin用户
            User adminUser = userService.findByUsername("admin");
            if (adminUser != null) {
                // 重置密码为admin123
                boolean success = userService.resetPassword(adminUser.getId(), "admin123");
                if (success) {
                    model.addAttribute("message", "admin用户密码已重置为: admin123");
                } else {
                    model.addAttribute("error", "密码重置失败");
                }
            } else {
                // 创建admin用户
                User newAdmin = new User();
                newAdmin.setUsername("admin");
                newAdmin.setPassword("admin123");
                newAdmin.setRole("ADMIN");
                newAdmin.setEmail("admin@example.com");
                newAdmin.setStatus(1);
                
                boolean success = userService.createUser(newAdmin);
                if (success) {
                    model.addAttribute("message", "admin用户已创建，密码为: admin123");
                } else {
                    model.addAttribute("error", "admin用户创建失败");
                }
            }
        } catch (Exception e) {
            model.addAttribute("error", "操作失败: " + e.getMessage());
        }
        
        return "debug/reset-result";
    }
}
