package com.secondhand.dao;

import com.secondhand.entity.Item;
import com.secondhand.util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDao extends BaseDao {

    /**
     * 添加物品
     */
    public int insert(Item item) {
        String sql = "INSERT INTO items (title, description, price, image_url, user_id, status) VALUES (?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getImageUrl(),
                item.getUserId(),
                item.getStatus());
    }

    /**
     * 根据ID查询物品
     */
    public Item findById(int id) {
        String sql = "SELECT * FROM items WHERE id = ? AND status = 1";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return resultSetToItem(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return null;
    }

    /**
     * 根据用户ID查询物品
     */
    public List<Item> findByUserId(int userId) {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE user_id = ? AND status = 1 ORDER BY created_at DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                itemList.add(resultSetToItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return itemList;
    }

    /**
     * 获取所有在售物品
     */
    public List<Item> findAllActive() {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE status = 1 ORDER BY created_at DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                itemList.add(resultSetToItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return itemList;
    }

    /**
     * 搜索物品（模糊匹配标题）
     */
    public List<Item> searchByTitle(String keyword) {
        List<Item> itemList = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE status = 1 AND title LIKE ? ORDER BY created_at DESC";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                itemList.add(resultSetToItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(conn, pstmt, rs);
        }
        return itemList;
    }

    /**
     * 更新物品信息
     */
    public int update(Item item) {
        String sql = "UPDATE items SET title = ?, description = ?, price = ?, image_url = ?, status = ? WHERE id = ? AND user_id = ?";
        return executeUpdate(sql,
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getImageUrl(),
                item.getStatus(),
                item.getId(),
                item.getUserId());
    }

    /**
     * 删除物品（标记为下架）
     */
    public int delete(int id, int userId) {
        String sql = "UPDATE items SET status = 0 WHERE id = ? AND user_id = ?";
        return executeUpdate(sql, id, userId);
    }

    /**
     * 将ResultSet转换为Item对象
     */
    private Item resultSetToItem(ResultSet rs) throws SQLException {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setTitle(rs.getString("title"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getBigDecimal("price"));
        item.setImageUrl(rs.getString("image_url"));
        item.setUserId(rs.getInt("user_id"));
        item.setStatus(rs.getInt("status"));
        item.setCreatedAt(rs.getTimestamp("created_at"));
        return item;
    }
}