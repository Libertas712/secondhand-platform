package com.secondhand;

import com.secondhand.util.DBUtil;
import java.sql.*;

public class TestDB {
    public static void main(String[] args) {
        System.out.println("正在测试数据库连接...");

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            if (conn == null) {
                System.out.println("❌ 连接失败，请检查配置");
                return;
            }

            System.out.println("✅ 数据库连接成功！");
            System.out.println("连接信息：");
            System.out.println("- 数据库URL: " + conn.getMetaData().getURL());
            System.out.println("- 数据库用户: " + conn.getMetaData().getUserName());

            // 测试查询
            stmt = conn.createStatement();

            // 检查用户表
            rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next()) {
                System.out.println("- 用户表记录数: " + rs.getInt(1));
            }

            // 检查物品表
            rs = stmt.executeQuery("SELECT COUNT(*) FROM items");
            if (rs.next()) {
                System.out.println("- 物品表记录数: " + rs.getInt(1));
            }

            System.out.println("\n✅ 所有测试通过！");

        } catch (SQLException e) {
            System.out.println("❌ SQL错误: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, stmt, rs);
        }
    }
}