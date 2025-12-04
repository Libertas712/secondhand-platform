<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${pageTitle} - 二手物品发布平台</title>
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
        .btn { display: inline-block; padding: 10px 20px; background: #4CAF50; color: white;
            text-decoration: none; border-radius: 4px; margin: 5px; }
        .btn:hover { background: #45a049; }
        .btn-blue { background: #2196F3; }
        .btn-blue:hover { background: #0b7dda; }
        .btn-red { background: #f44336; }
        .btn-red:hover { background: #d32f2f; }
        .search-form { margin: 20px 0; }
        .search-input { padding: 10px; width: 300px; border: 1px solid #ddd; border-radius: 4px; }
        .search-btn { padding: 10px 20px; background: #2196F3; color: white; border: none;
            border-radius: 4px; cursor: pointer; }
        .item-list { margin-top: 20px; }
        .item { border: 1px solid #ddd; padding: 15px; margin-bottom: 15px; border-radius: 5px; }
        .item-title { font-size: 18px; font-weight: bold; color: #333; }
        .item-price { color: #f60; font-size: 16px; font-weight: bold; margin: 10px 0; }
        .item-meta { color: #666; font-size: 14px; margin-top: 10px; }
        .item-actions { margin-top: 10px; }
        .no-items { text-align: center; padding: 40px; color: #666; }
        .pagination { text-align: center; margin-top: 30px; }
        .page-link { display: inline-block; padding: 8px 12px; margin: 0 5px;
            border: 1px solid #ddd; text-decoration: none; color: #333; }
        .page-link.active { background: #4CAF50; color: white; border-color: #4CAF50; }
        .empty-state { text-align: center; padding: 50px 20px; color: #666; }
        .empty-state img { max-width: 200px; margin-bottom: 20px; opacity: 0.5; }
    </style>
</head>
<body>
<div class="container">
    <header>
        <nav>
            <h1><a href="${pageContext.request.contextPath}/" style="color: white; text-decoration: none;">二手物品发布平台</a></h1>
            <div class="user-info">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        欢迎, ${sessionScope.username}！
                        <a href="${pageContext.request.contextPath}/logout" class="btn">退出</a>
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
                    <a href="${pageContext.request.contextPath}/" style="color: #333; text-decoration: none;">🏠 首页</a>
                </li>
                <c:if test="${not empty sessionScope.user}">
                    <li style="margin-bottom: 10px;">
                        <a href="${pageContext.request.contextPath}/publish" style="color: #333; text-decoration: none;">➕ 发布物品</a>
                    </li>
                    <li style="margin-bottom: 10px;">
                        <a href="${pageContext.request.contextPath}/myItems" style="color: #333; text-decoration: none;">📦 我的物品</a>
                    </li>
                </c:if>
                <li style="margin-bottom: 10px;">
                    <a href="${pageContext.request.contextPath}/listItems" style="color: #333; text-decoration: none;">🔍 浏览所有</a>
                </li>
            </ul>

            <div class="search-form">
                <form action="${pageContext.request.contextPath}/search" method="get">
                    <input type="text" name="keyword" class="search-input"
                           placeholder="搜索物品..." value="${keyword}">
                    <button type="submit" class="search-btn">搜索</button>
                </form>
            </div>

            <c:if test="${not empty keyword}">
                <div style="margin-top: 15px;">
                    <p>搜索关键词: <strong>${keyword}</strong></p>
                    <p>找到 ${items.size()} 个结果</p>
                </div>
            </c:if>
        </aside>

        <main class="content">
            <h2>${pageTitle}</h2>

            <c:if test="${not empty param.msg}">
                <div style="background: #d4edda; color: #155724; padding: 10px; border-radius: 4px; margin: 10px 0;">
                        ${param.msg}
                </div>
            </c:if>

            <c:if test="${not empty param.error}">
                <div style="background: #f8d7da; color: #721c24; padding: 10px; border-radius: 4px; margin: 10px 0;">
                        ${param.error}
                </div>
            </c:if>

            <div class="item-list">
                <c:choose>
                    <c:when test="${empty items}">
                        <div class="empty-state">
                            <div style="font-size: 48px; margin-bottom: 20px;">📦</div>
                            <h3>没有找到物品</h3>
                            <p>暂时还没有人发布物品，你可以成为第一个发布者！</p>
                            <c:if test="${not empty sessionScope.user}">
                                <a href="${pageContext.request.contextPath}/publish" class="btn" style="margin-top: 20px;">发布物品</a>
                            </c:if>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="item" items="${items}">
                            <div class="item">
                                <div class="item-title">
                                    <c:if test="${item.status == 0}">
                                        <span style="background: #ccc; color: #666; padding: 2px 6px; border-radius: 3px;
                                              font-size: 12px; margin-right: 8px;">已下架</span>
                                    </c:if>
                                        ${item.title}
                                </div>
                                <div class="item-price">￥${item.price}</div>
                                <div class="item-description">
                                        ${item.description}
                                </div>
                                <div class="item-meta">
                                    发布者：${item.user.username} |
                                    发布时间：${item.createdAt} |
                                    <c:choose>
                                        <c:when test="${item.status == 1}">
                                            <span style="color: #4CAF50;">在售</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span style="color: #999;">已下架</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <c:if test="${not empty sessionScope.user && sessionScope.userId == item.userId}">
                                    <div class="item-actions">
                                        <a href="${pageContext.request.contextPath}/editItem?id=${item.id}" class="btn btn-blue">编辑</a>
                                        <a href="${pageContext.request.contextPath}/deleteItem?id=${item.id}"
                                           class="btn btn-red"
                                           onclick="return confirm('确定要删除这个物品吗？')">删除</a>
                                    </div>
                                </c:if>
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </div>
        </main>
    </div>

    <footer style="text-align: center; margin-top: 40px; color: #666; padding: 20px; border-top: 1px solid #ddd;">
        <p>© 2025 二手物品发布平台 - 课程项目</p>
    </footer>
</div>
</body>
</html>