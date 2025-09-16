package com.student.service;

import com.student.entity.User;
import com.student.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户业务逻辑层
 *
 * @author Student Management System
 * @version 1.0.0
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    /**
     * 查询所有用户
     *
     * @return 用户列表
     */
    public List<User> findAll() {
        return userMapper.findAll();
    }

    /**
     * 分页查询用户
     *
     * @param page 页码（从1开始）
     * @param size 每页大小
     * @return 用户列表
     */
    public List<User> findByPage(int page, int size) {
        int offset = (page - 1) * size;
        return userMapper.findByPage(offset, size);
    }

    /**
     * 根据角色查询用户
     *
     * @param role 角色
     * @return 用户列表
     */
    public List<User> findByRole(String role) {
        return userMapper.findByRole(role);
    }

    /**
     * 统计用户总数
     *
     * @return 用户总数
     */
    public int countAll() {
        return userMapper.countAll();
    }

    /**
     * 创建用户
     *
     * @param user 用户信息
     * @return 是否成功
     */
    public boolean createUser(User user) {
        // 检查用户名是否已存在
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 设置默认状态
        if (user.getStatus() == null) {
            user.setStatus(1);
        }

        return userMapper.insert(user) > 0;
    }

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 是否成功
     */
    public boolean updateUser(User user) {
        // 检查用户是否存在
        User existingUser = userMapper.findById(user.getId());
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 如果用户名发生变化，检查新用户名是否已存在
        if (!existingUser.getUsername().equals(user.getUsername())) {
            User userWithSameName = userMapper.findByUsername(user.getUsername());
            if (userWithSameName != null) {
                throw new RuntimeException("用户名已存在");
            }
        }

        return userMapper.update(user) > 0;
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return 是否成功
     */
    public boolean deleteUser(Long id) {
        // 检查用户是否存在
        User user = userMapper.findById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 不能删除管理员用户
        if ("ADMIN".equals(user.getRole())) {
            throw new RuntimeException("不能删除管理员用户");
        }

        return userMapper.deleteById(id) > 0;
    }

    /**
     * 更新用户状态
     *
     * @param id 用户ID
     * @param status 状态
     * @return 是否成功
     */
    public boolean updateUserStatus(Long id, Integer status) {
        return userMapper.updateStatus(id, status) > 0;
    }

    /**
     * 更新用户密码
     *
     * @param id 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 是否成功
     */
    public boolean updatePassword(Long id, String oldPassword, String newPassword) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证旧密码
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("旧密码不正确");
        }

        // 加密新密码
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        return userMapper.updatePassword(id, encodedNewPassword) > 0;
    }

    /**
     * 重置用户密码
     *
     * @param id 用户ID
     * @param newPassword 新密码
     * @return 是否成功
     */
    public boolean resetPassword(Long id, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        return userMapper.updatePassword(id, encodedPassword) > 0;
    }
}
