# 二手物品发布平台

## 项目简介
一个基于Java Web的简易二手物品发布平台，用户可以在平台上发布、浏览、搜索、管理二手物品。项目采用MVC架构，实现了用户注册登录、物品发布、模糊搜索、物品管理等功能。

## 系统访问地址
**服务器访问链接**：http://10.100.164.23:8080/secondhand-platform/

## 系统结构设计

### 技术栈
- **后端**：Java Servlet + JSP + JSTL
- **数据库**：MySQL 8.0
- **前端**：HTML5 + CSS3 + JavaScript
- **构建工具**：Maven 3.8
- **服务器**：Apache Tomcat 9.0
- **版本控制**：Git

### 系统架构
用户请求 → Servlet控制器 → Service业务层 → DAO数据层 → MySQL数据库
↓
JSP视图层

### 项目目录结构
src/main/java/com/secondhand/
├── entity/
│   ├── User.java
│   └── Item.java
├── dao/
│   ├── BaseDao.java
│   ├── UserDao.java
│   └── ItemDao.java
├── service/
│   ├── UserService.java
│   └── ItemService.java
├── servlet/
│   ├── RegisterServlet.java
│   ├── LoginServlet.java
│   ├── LogoutServlet.java
│   ├── PublishItemServlet.java
│   ├── ItemListServlet.java
│   ├── SearchServlet.java
│   ├── MyItemsServlet.java
│   ├── EditItemServlet.java
│   └── DeleteItemServlet.java
├── filter/
│   └── CharacterEncodingFilter.java
└── util/
├── DBUtil.java
└── MD5Util.java

src/main/webapp/
├── index.jsp
├── login.jsp
├── register.jsp
├── itemList.jsp
├── publish.jsp
├── editItem.jsp
├── myItems.jsp
└── WEB-INF/
└── web.xml

## 数据库结构说明

### 数据库信息
- **数据库名**：secondhand
- **字符集**：UTF-8
- **排序规则**：utf8mb4_general_ci

### 表结构设计

#### 1. 用户表 (users)
CREATE TABLE users (
id INT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(50) UNIQUE NOT NULL COMMENT '用户名',
password VARCHAR(100) NOT NULL COMMENT '密码（MD5加密存储）',
email VARCHAR(100) COMMENT '邮箱',
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
);

#### 2. 物品表 (items)
CREATE TABLE items (
id INT PRIMARY KEY AUTO_INCREMENT,
title VARCHAR(200) NOT NULL COMMENT '物品标题',
description TEXT COMMENT '物品描述',
price DECIMAL(10, 2) COMMENT '价格',
image_url VARCHAR(500) COMMENT '图片URL',
user_id INT NOT NULL COMMENT '发布用户ID',
status TINYINT DEFAULT 1 COMMENT '状态：1-在售，0-已下架',
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

### 数据库关系图
users (1) ──── (n) items
↑                ↑
ID              user_id

## 使用方法

### 环境要求
- JDK 8+
- MySQL 8.0+
- Tomcat 9.0+
- Maven 3.6+

### 部署步骤

#### 1. 数据库初始化
-- 创建数据库
CREATE DATABASE secondhand DEFAULT CHARSET utf8mb4;

-- 创建用户并授权
CREATE USER 'secondhand_user'@'%' IDENTIFIED BY 'StrongPassword123!';
GRANT ALL PRIVILEGES ON secondhand.* TO 'secondhand_user'@'%';
FLUSH PRIVILEGES;

-- 使用数据库
USE secondhand;

-- 创建表（执行上面的建表SQL）

#### 2. 项目配置
修改 src/main/java/com/secondhand/util/DBUtil.java：
private static final String URL = "jdbc:mysql://10.100.164.23:3306/secondhand?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
private static final String USER = "secondhand_user";
private static final String PASSWORD = "StrongPassword123!";

#### 3. 项目打包
mvn clean package

#### 4. 部署到Tomcat
# 上传war包
scp target/secondhand-platform.war root@10.100.164.23:/usr/local/tomcat9/webapps/

# 重启Tomcat
ssh root@10.100.164.23 "cd /usr/local/tomcat9/bin && ./shutdown.sh && ./startup.sh"

#### 5. 访问系统
http://10.100.164.23:8080/secondhand-platform/

### 功能使用指南

#### 游客功能
- 浏览所有在售物品
- 搜索物品（支持模糊匹配）
- 注册新账号

#### 注册用户功能
- 登录/退出系统
- 发布二手物品
- 查看和管理自己发布的物品
- 编辑或下架自己的物品
- 搜索其他用户的物品

## 测试账号

系统已预置以下测试账号：

| 用户名       | 密码   | 备注                   |
|--------------|--------|------------------------|
| 2335060304   | 123456 | 普通用户，已有测试物品 |
| 张三         | 456789 | 普通用户，已有测试物品 |
| testuser     | 123456 | 测试用户               |
| serviceuser  | 123456 | 测试用户               |

### 测试物品数据
- 二手笔记本电脑（￥1999.00）
- 二手自行车（￥399.00）
- 二手数码相机（￥1299.00）
- 二手iPhone手机（￥2999.00）

## 功能测试清单

### 用户相关测试
- 用户注册（密码MD5加密存储）
- 用户登录/退出
- 用户名唯一性验证
- 表单数据验证

### 物品相关测试
- 发布二手物品
- 浏览所有物品
- 模糊搜索物品（标题匹配）
- 查看物品详情
- 编辑自己的物品
- 下架/删除自己的物品
- 查看我的物品列表

### 安全相关
- 密码加密存储（MD5）
- 会话管理
- 权限控制（只能操作自己的物品）
- SQL注入防护（使用PreparedStatement）
- XSS防护（JSTL自动转义）

## 故障排除

### 常见问题

#### 1. 数据库连接失败
# 检查MySQL服务状态
systemctl status mysql

# 检查防火墙
sudo ufw status
sudo ufw allow 3306

# 检查用户权限
mysql -u root -p
SELECT host, user FROM mysql.user;

#### 2. Tomcat启动失败
# 查看Tomcat日志
tail -f /usr/local/tomcat9/logs/catalina.out

# 检查端口占用
netstat -tlnp | grep 8080

# 检查文件权限
ls -la /usr/local/tomcat9/webapps/

#### 3. 页面显示乱码
- 确保数据库连接URL包含 useUnicode=true&characterEncoding=UTF-8
- 检查JSP页面设置了 <%@ page contentType="text/html;charset=UTF-8" %>
- 检查Tomcat的 server.xml 配置了 URIEncoding="UTF-8"

#### 4. 404页面未找到
# 检查war包是否解压
ls -la /usr/local/tomcat9/webapps/secondhand-platform/

# 重启Tomcat
cd /usr/local/tomcat9/bin && ./shutdown.sh && ./startup.sh

## 项目特点

### 已完成功能
1. 用户系统
    - 注册/登录/退出
    - 密码加密存储
    - 会话管理

2. 物品系统
    - 发布二手物品
    - 浏览所有物品
    - 模糊搜索功能
    - 物品管理（编辑/删除）

3. 权限系统
    - 游客只能浏览
    - 用户可发布和管理自己的物品
    - 防止越权操作

4. 安全性
    - SQL注入防护
    - 密码加密
    - 输入验证

### 技术亮点
- 采用MVC分层架构
- 使用JSTL标签库减少Java代码嵌入
- 数据库连接池管理
- 统一的异常处理
- 响应式界面设计

## 文件说明

### 核心配置文件
- pom.xml - Maven项目配置
- web.xml - Web应用配置
- DBUtil.java - 数据库连接配置

### 重要业务文件
- UserService.java - 用户业务逻辑
- ItemService.java - 物品业务逻辑
- LoginServlet.java - 登录处理
- PublishItemServlet.java - 物品发布处理

## 开发者信息
- 项目类型：Java Web课程项目
- 开发环境：IntelliJ IDEA + MySQL + Tomcat
- 开发周期：2025年12月
- 目标：实现一个功能完整的二手物品发布平台

## 许可证
本项目为课程设计作品，仅供学习参考。

最后更新：2025年12月  
系统状态：运行正常  
访问地址：http://10.100.164.23:8080/secondhand-platform/