package com.secondhand.service;

import com.secondhand.dao.ItemDao;
import com.secondhand.dao.UserDao;
import com.secondhand.entity.Item;
import com.secondhand.entity.User;
import java.util.List;

public class ItemService {
    private ItemDao itemDao = new ItemDao();
    private UserDao userDao = new UserDao();

    /**
     * 发布二手物品
     */
    public boolean publishItem(String title, String description,
                               double price, String imageUrl, int userId) {
        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        item.setPrice(java.math.BigDecimal.valueOf(price));
        item.setImageUrl(imageUrl);
        item.setUserId(userId);
        item.setStatus(1);  // 在售状态

        return itemDao.insert(item) > 0;
    }

    /**
     * 获取所有在售物品
     */
    public List<Item> getAllActiveItems() {
        List<Item> items = itemDao.findAllActive();

        // 为每个物品设置用户信息
        for (Item item : items) {
            User user = userDao.findById(item.getUserId());
            item.setUser(user);
        }

        return items;
    }

    /**
     * 搜索物品
     */
    public List<Item> searchItems(String keyword) {
        List<Item> items = itemDao.searchByTitle(keyword);

        // 为每个物品设置用户信息
        for (Item item : items) {
            User user = userDao.findById(item.getUserId());
            item.setUser(user);
        }

        return items;
    }

    /**
     * 获取用户发布的物品
     */
    public List<Item> getUserItems(int userId) {
        return itemDao.findByUserId(userId);
    }

    /**
     * 根据ID获取物品详情
     */
    public Item getItemById(int id) {
        Item item = itemDao.findById(id);
        if (item != null) {
            User user = userDao.findById(item.getUserId());
            item.setUser(user);
        }
        return item;
    }

    /**
     * 更新物品信息
     */
    public boolean updateItem(Item item) {
        return itemDao.update(item) > 0;
    }

    /**
     * 删除物品（标记为下架）
     */
    public boolean deleteItem(int itemId, int userId) {
        // 验证物品属于该用户
        Item item = itemDao.findById(itemId);
        if (item == null || item.getUserId() != userId) {
            return false;
        }

        return itemDao.delete(itemId, userId) > 0;
    }

    /**
     * 检查物品是否属于用户
     */
    public boolean isItemBelongsToUser(int itemId, int userId) {
        Item item = itemDao.findById(itemId);
        return item != null && item.getUserId() == userId;
    }
}