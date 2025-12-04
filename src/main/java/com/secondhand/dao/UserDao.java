package com.secondhand.dao;

import com.secondhand.entity.User;
import com.secondhand.util.DBUtil;
import com.secondhand.util.MD5Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends BaseDao {

    /**
     * 添加用户
     */
    public int insert(User user) {
        String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        return executeUpdate(sql,
                user.getUsername(),
                user.getPassword(),  // 密码应该在Service层加密
                user.getEmail());
    }

    /**
     * 根据ID查询用户
     */
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return resultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 根据用户名查询用户
     */
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return resultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 用户登录验证
     */
    public User login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);  // 密码应该是加密后的
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return resultSetToUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 检查用户名是否存在
     */
    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Object result = executeQuerySingle(sql, username);

        if (result instanceof Long) {
            return ((Long) result) > 0;
        } else if (result instanceof Integer) {
            return ((Integer) result) > 0;
        }
        return false;
    }

    /**
     * 获取所有用户
     */
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                userList.add(resultSetToUser(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return userList;
    }

    /**
     * 更新用户信息
     */
    public int update(User user) {
        String sql = "UPDATE users SET email = ? WHERE id = ?";
        return executeUpdate(sql, user.getEmail(), user.getId());
    }

    /**
     * 删除用户
     */
    public int delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        return executeUpdate(sql, id);
    }

    /**
     * 将ResultSet转换为User对象
     */
    private User resultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}