-- 修复管理员密码的SQL脚本
-- 这个脚本会重新设置admin用户的密码为admin123

USE student_management;

-- 更新admin用户的密码为admin123的BCrypt哈希
-- 这个哈希值是通过BCryptPasswordEncoder生成的
UPDATE users 
SET password = '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi',
    status = 1
WHERE username = 'admin';

-- 如果admin用户不存在，则创建
INSERT IGNORE INTO users (username, password, role, email, status) 
VALUES ('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ADMIN', 'admin@example.com', 1);

-- 查看更新后的用户信息
SELECT id, username, role, email, status, created_time 
FROM users 
WHERE username = 'admin';
