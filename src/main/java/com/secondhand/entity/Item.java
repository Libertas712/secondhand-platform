package com.secondhand.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Item {
    private int id;
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private int userId;
    private int status;  // 1:在售, 0:已售出或删除
    private Date createdAt;

    // 关联的用户对象（可选）
    private User user;

    // 构造方法
    public Item() {}

    public Item(String title, String description, BigDecimal price, int userId) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.userId = userId;
        this.status = 1;  // 默认在售
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // 状态显示方法
    public String getStatusText() {
        return status == 1 ? "在售" : "已下架";
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", userId=" + userId +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}