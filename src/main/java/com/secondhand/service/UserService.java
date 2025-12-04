package com.secondhand.service;

import com.secondhand.dao.UserDao;
import com.secondhand.entity.User;
import com.secondhand.util.MD5Util;

public class UserService {
    private UserDao userDao = new UserDao();

    /**
     * 用户注册
     */
    public boolean register(String username, String password, String email) {
        // 1. 检查用户名是否已存在
        if (userDao.isUsernameExists(username)) {
            return false;
        }

        // 2. 创建用户对象
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Util.md5(password));  // 密码加密存储
        user.setEmail(email);

        // 3. 保存到数据库
        return userDao.insert(user) > 0;
    }

    /**
     * 用户登录
     */
    public User login(String username, String password) {
        // 密码需要加密后验证
        String encryptedPassword = MD5Util.md5(password);
        return userDao.login(username, encryptedPassword);
    }

    /**
     * 根据ID获取用户
     */
    public User getUserById(int id) {
        return userDao.findById(id);
    }

    /**
     * 根据用户名获取用户
     */
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 更新用户信息
     */
    public boolean updateUser(User user) {
        return userDao.update(user) > 0;
    }

    /**
     * 检查用户名是否存在
     */
    public boolean checkUsernameExists(String username) {
        return userDao.isUsernameExists(username);
    }
}