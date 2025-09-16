package com.student.mapper;

import com.student.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户数据访问层
 * 
 * @author Student Management System
 * @version 1.0.0
 */
@Mapper
public interface UserMapper {
    
    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(@Param("username") String username);
    
    /**
     * 根据ID查询用户
     * 
     * @param id 用户ID
     * @return 用户信息
     */
    User findById(@Param("id") Long id);
    
    /**
     * 查询所有用户
     * 
     * @return 用户列表
     */
    List<User> findAll();
    
    /**
     * 分页查询用户
     * 
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 用户列表
     */
    List<User> findByPage(@Param("offset") int offset, @Param("limit") int limit);
    
    /**
     * 根据角色查询用户
     * 
     * @param role 角色
     * @return 用户列表
     */
    List<User> findByRole(@Param("role") String role);
    
    /**
     * 统计用户总数
     * 
     * @return 用户总数
     */
    int countAll();
    
    /**
     * 插入用户
     * 
     * @param user 用户信息
     * @return 影响行数
     */
    int insert(User user);
    
    /**
     * 更新用户
     * 
     * @param user 用户信息
     * @return 影响行数
     */
    int update(User user);
    
    /**
     * 根据ID删除用户
     * 
     * @param id 用户ID
     * @return 影响行数
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * 更新用户状态
     * 
     * @param id 用户ID
     * @param status 状态
     * @return 影响行数
     */
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 更新用户密码
     * 
     * @param id 用户ID
     * @param password 新密码
     * @return 影响行数
     */
    int updatePassword(@Param("id") Long id, @Param("password") String password);
}
