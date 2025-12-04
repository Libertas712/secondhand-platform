<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>二手物品发布平台</title>
    <meta charset="UTF-8">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background-color: #f5f5f5; }
        .container { max-width: 1200px; margin: 0 auto; padding: 20px; }
        header { background: #4CAF50; color: white; padding: 20px; border-radius: 5px; margin-bottom: 20px; }
        nav { display: flex; justify-content: space-between; align-items: center; }
        .user-info { text-align: right; }
        .main-content { display: flex; gap: 20px; }
        .sidebar { width: 250px; background: white; padding: 20px; border-radius: 5px; }
        .content { flex: 1; background: white; padding: 20px; border-radius: 5px; }
        .btn { display: inline-block; padding: 10px 20px; background: #4CAF50; color: white; text-decoration: none; border-radius: 4px; }
        .btn:hover { background: #45a049; }
        .search-form { margin: 20px 0; }
        .search-input { padding: 10px; width: 300px; border: 1px solid #ddd; border-radius: 4px; }
        .search-btn { padding: 10px 20px; background: #2196F3; color: white; border: none; border-radius: 4px; cursor: pointer; }
        .item-list { margin-top: 20px; }
        .item { border: 1px solid #ddd; padding: 15px; margin-bottom: 15px; border-radius: 5px; }
        .item-title { font-size: 18px; font-weight: bold; color: #333; }
        .item-price { color: #f60; font-size: 16px; font-weight: bold; margin: 10px 0; }
        .item-meta { color: #666; font-size: 14px; }
    </style>
</head>
<body>
<div class="container">
    <header>
        <nav>
            <h1>二手物品发布平台</h1>
            <div class="user-info">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        欢迎, ${sessionScope.username}！
                        <a href="${pageContext.request.contextPath}/logout" class="btn" style="margin-left: 10px;">退出</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/login.jsp" class="btn">登录</a>
                        <a href="${pageContext.request.contextPath}/register.jsp" class="btn">注册</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </nav>
    </header>

    <div class="main-content">
        <aside class="sidebar">
            <h3>功能菜单</h3>
            <ul style="list-style: none; margin-top: 15px;">
                <li style="margin-bottom: 10px;">
                    <a href="${pageContext.request.contextPath}/index.jsp" style="color: #333; text-decoration: none;">🏠 首页</a>
                </li>
                <c:if test="${not empty sessionScope.user}">
                    <li style="margin-bottom: 10px;">
                        <a href="${pageContext.request.contextPath}/publish.jsp" style="color: #333; text-decoration: none;">➕ 发布物品</a>
                    </li>
                    <li style="margin-bottom: 10px;">
                        <a href="${pageContext.request.contextPath}/myItems" style="color: #333; text-decoration: none;">📦 我的物品</a>
                    </li>
                </c:if>
                <li style="margin-bottom: 10px;">
                    <a href="${pageContext.request.contextPath}/listItems" style="color: #333; text-decoration: none;">🔍 浏览所有</a>
                </li>
            </ul>

            <div class="search-form" style="margin-top: 20px;">
                <form action="${pageContext.request.contextPath}/search" method="get">
                    <input type="text" name="keyword" class="search-input" placeholder="搜索物品...">
                    <button type="submit" class="search-btn">搜索</button>
                </form>
            </div>
        </aside>

        <main class="content">
            <h2>最新二手物品</h2>
            <p style="margin: 10px 0; color: #666;">在这里可以找到各种二手物品，价格实惠，物超所值！</p>

            <c:if test="${empty sessionScope.user}">
                <div style="background: #e3f2fd; padding: 15px; border-radius: 5px; margin: 20px 0;">
                    <p>💡 提示：登录后可以发布和管理自己的二手物品！</p>
                </div>
            </c:if>

            <div class="item-list">
                <%@ page import="com.secondhand.service.ItemService, com.secondhand.entity.Item, java.util.List" %>
                <%
                    ItemService itemService = new ItemService();
                    List<Item> latestItems = itemService.getAllActiveItems();
                    if (latestItems.size() > 5) {
                        latestItems = latestItems.subList(0, 5); // 只显示最新的5个
                    }
                    request.setAttribute("latestItems", latestItems);
                %>

                <c:forEach var="item" items="${latestItems}">
                    <div class="item">
                        <div class="item-title">${item.title}</div>
                        <div class="item-price">￥${item.price}</div>
                        <div class="item-description">${item.description}</div>
                        <div class="item-meta">
                            发布者：${item.user.username} |
                            发布时间：${item.createdAt}
                        </div>
                    </div>
                </c:forEach>

                <c:if test="${empty latestItems}">
                    <div class="git add .no-items">
                        <p>暂时没有二手物品，你可以成为第一个发布者！</p>
                        <c:if test="${not empty sessionScope.user}">
                            <a href="${pageContext.request.contextPath}/publish" class="btn">发布物品</a>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </main>
    </div>

    <footer style="text-align: center; margin-top: 40px; color: #666; padding: 20px; border-top: 1px solid #ddd;">
        <p>© 2025 二手物品发布平台 - 课程项目</p>
    </footer>
</div>
</body>
</html>