package com.secondhand;

import com.secondhand.service.UserService;
import com.secondhand.service.ItemService;
import com.secondhand.entity.User;
import com.secondhand.entity.Item;
import java.util.List;

public class TestService {
    public static void main(String[] args) {
        System.out.println("=== 测试Service层 ===");

        UserService userService = new UserService();
        ItemService itemService = new ItemService();

        try {
            // 测试1：用户注册
            System.out.println("\n1. 测试用户注册：");
            boolean registerSuccess = userService.register("serviceuser", "123456", "service@example.com");
            System.out.println("注册结果: " + (registerSuccess ? "成功" : "失败（可能用户名已存在）"));

            // 测试2：用户登录
            System.out.println("\n2. 测试用户登录：");
            User user = userService.login("serviceuser", "123456");
            if (user != null) {
                System.out.println("登录成功: " + user.getUsername() + ", ID: " + user.getId());

                // 测试3：发布物品
                System.out.println("\n3. 测试发布物品：");
                boolean publishSuccess = itemService.publishItem(
                        "二手数码相机",
                        "佳能单反，95新，配件齐全",
                        1299.00,
                        "",
                        user.getId()
                );
                System.out.println("发布结果: " + (publishSuccess ? "成功" : "失败"));

                // 测试4：获取所有物品
                System.out.println("\n4. 测试获取所有物品：");
                List<Item> allItems = itemService.getAllActiveItems();
                System.out.println("在售物品总数: " + allItems.size());
                for (Item item : allItems) {
                    System.out.println("  - " + item.getTitle() +
                            " ￥" + item.getPrice() +
                            " (发布者: " + (item.getUser() != null ? item.getUser().getUsername() : "未知") + ")");
                }

                // 测试5：搜索物品
                System.out.println("\n5. 测试搜索物品：");
                List<Item> searchResults = itemService.searchItems("相机");
                System.out.println("搜索'相机'结果: " + searchResults.size());
                for (Item item : searchResults) {
                    System.out.println("  - " + item.getTitle());
                }

                // 测试6：获取用户物品
                System.out.println("\n6. 测试获取用户物品：");
                List<Item> userItems = itemService.getUserItems(user.getId());
                System.out.println("用户发布的物品数量: " + userItems.size());

                // 测试7：检查用户名是否存在
                System.out.println("\n7. 测试检查用户名：");
                boolean exists = userService.checkUsernameExists("serviceuser");
                System.out.println("用户名'serviceuser'是否存在: " + exists);

            } else {
                System.out.println("登录失败，无法继续测试");
            }

            System.out.println("\n✅ Service层测试完成！");

        } catch (Exception e) {
            System.out.println("❌ 测试过程中出错: " + e.getMessage());
            e.printStackTrace();
        }
    }
}