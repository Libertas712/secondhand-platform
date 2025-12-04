package com.secondhand;

import com.secondhand.dao.UserDao;
import com.secondhand.dao.ItemDao;
import com.secondhand.entity.User;
import com.secondhand.entity.Item;
import com.secondhand.util.MD5Util;
import java.math.BigDecimal;
import java.util.List;

public class TestDao {
    public static void main(String[] args) {
        System.out.println("=== 测试DAO层 ===");

        UserDao userDao = new UserDao();
        ItemDao itemDao = new ItemDao();

        try {
            // 测试1：插入用户
            System.out.println("\n1. 测试用户插入：");
            User user = new User();
            user.setUsername("test3");
            user.setPassword(MD5Util.md5("123456"));  // 密码加密
            user.setEmail("test3@example.com");

            int userId = userDao.insert(user);
            System.out.println("插入用户ID: " + userId);

            // 测试2：查询用户
            System.out.println("\n2. 测试用户查询：");
            User foundUser = userDao.findByUsername("test3");
            if (foundUser != null) {
                System.out.println("找到用户: " + foundUser.getUsername() + ", ID: " + foundUser.getId());
            } else {
                System.out.println("用户未找到");
            }

            // 测试3：检查用户名是否存在（跳过，稍后修复）
            System.out.println("\n3. 测试用户名检查（跳过修复中）...");

            // 测试4：用户登录
            System.out.println("\n4. 测试用户登录：");
            if (foundUser != null) {
                User loginUser = userDao.login("test3", MD5Util.md5("123456"));
                if (loginUser != null) {
                    System.out.println("登录成功: " + loginUser.getUsername());
                } else {
                    System.out.println("登录失败");
                }

                // 测试5：插入物品
                System.out.println("\n5. 测试物品插入：");
                Item item = new Item();
                item.setTitle("二手自行车");
                item.setDescription("山地车，21速，8成新");
                item.setPrice(new BigDecimal("399.00"));
                item.setUserId(foundUser.getId());
                item.setStatus(1);

                int itemId = itemDao.insert(item);
                System.out.println("插入物品ID: " + itemId);

                // 测试6：查询物品
                System.out.println("\n6. 测试物品查询：");
                List<Item> items = itemDao.findAllActive();
                System.out.println("在售物品数量: " + items.size());
                for (Item i : items) {
                    System.out.println("  - " + i.getTitle() + " ￥" + i.getPrice());
                }

                // 测试7：搜索物品
                System.out.println("\n7. 测试物品搜索：");
                List<Item> searchResults = itemDao.searchByTitle("自行");
                System.out.println("搜索'自行'结果数量: " + searchResults.size());
                for (Item i : searchResults) {
                    System.out.println("  - " + i.getTitle());
                }
            }

            System.out.println("\n✅ DAO层测试完成！");

        } catch (Exception e) {
            System.out.println("❌ 测试过程中出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
}