package com.secondhand.util;

import java.sql.*;

public class DBUtil {
    private static final String URL = "jdbc:mysql://10.100.164.23:3306/secondhand?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
    private static final String USER = "secondhand_user";
    private static final String PASSWORD = "StrongPassword123!";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("MySQL 驱动加载成功");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL 驱动加载失败");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("数据库连接成功");
            return conn;
        } catch (SQLException e) {
            System.err.println("数据库连接失败: " + e.getMessage());
            return null;
        }
    }

    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn, Statement stmt) {
        close(conn, stmt, null);
    }
}