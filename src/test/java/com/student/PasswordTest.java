package com.student;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码测试类 - 用于验证默认密码
 */
public class PasswordTest {
    
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        // 数据库中的密码哈希
        String storedPassword = "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi";
        
        // 测试不同的密码
        String[] testPasswords = {"admin123", "admin", "123456", "password"};
        
        System.out.println("=== 密码验证测试 ===");
        System.out.println("存储的密码哈希: " + storedPassword);
        System.out.println();
        
        for (String testPassword : testPasswords) {
            boolean matches = passwordEncoder.matches(testPassword, storedPassword);
            System.out.println("测试密码: " + testPassword + " -> " + (matches ? "✓ 匹配" : "✗ 不匹配"));
        }
        
        System.out.println();
        System.out.println("=== 生成新的密码哈希 ===");
        String newPassword = "admin123";
        String newHash = passwordEncoder.encode(newPassword);
        System.out.println("新密码: " + newPassword);
        System.out.println("新哈希: " + newHash);
        
        // 验证新生成的哈希
        boolean newMatches = passwordEncoder.matches(newPassword, newHash);
        System.out.println("新哈希验证: " + (newMatches ? "✓ 匹配" : "✗ 不匹配"));
    }
}
