package com.secondhand;

import com.secondhand.entity.User;
import com.secondhand.entity.Item;
import java.math.BigDecimal;
import java.util.Date;

public class TestEntity {
    public static void main(String[] args) {
        System.out.println("=== 测试实体类 ===");

        // 测试 User 类
        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setPassword("123456");
        user.setEmail("test@example.com");
        user.setCreatedAt(new Date());

        System.out.println("用户信息: " + user);
        System.out.println("用户名: " + user.getUsername());
        System.out.println("邮箱: " + user.getEmail());

        // 测试 Item 类
        Item item = new Item();
        item.setId(1);
        item.setTitle("二手iPhone 13");
        item.setDescription("95新，功能完好，无划痕");
        item.setPrice(new BigDecimal("2999.00"));
        item.setUserId(1);
        item.setStatus(1);
        item.setCreatedAt(new Date());

        System.out.println("\n物品信息: " + item);
        System.out.println("标题: " + item.getTitle());
        System.out.println("价格: " + item.getPrice());
        System.out.println("状态: " + item.getStatusText());

        System.out.println("\n✅ 实体类测试完成！");
    }
}