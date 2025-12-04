<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的物品 - 二手物品发布平台</title>
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
        .item-list { margin-top: 20px; }
        .item { border: 1px solid #ddd; padding: 15px; margin-bottom: 15px; border-radius: 5px; }
        .item-title { font-size: 18px; font-weight: bold; color: #333; }
        .item-price { color: #f60; font-size: 16px; font-weight: bold; margin: 10px 0; }
        .item-meta { color: #666; font-size: 14px; margin-top: 10px; }
        .item-actions { margin-top: 10px; }
        .no-items { text-align: center; padding: 40px; color: #666; }
        .item-status { display: inline-block; padding: 3px 8px; border-radius: 3px;
            font-size: 12px; margin-left: 10px; }
        .status-onsale { background: #d4edda; color: #155724; }
        .status-sold { background: #f8d7da; color: #721c24; }
        .empty-state { text-align: center; padding: 50px 20px; color: #666; }
        .empty-state .icon { font-size: 48px; margin-bottom: 20px; display: block; }
        .stats { display: flex; gap: 20px; margin-bottom: 30px; padding: 20px; background: #f8f9fa;
            border-radius: 5px; }
        .stat-item { text-align: center; flex: 1; }
        .stat-number { font-size: 24px; font-weight: bold; color: #4CAF50; }
        .stat-label { font-size: 14px; color: #666; margin-top: 5px; }
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
            <h3>我的账户</h3>
            <ul style="list-style: none; margin-top: 15px;">
                <li style="margin-bottom: 10px;">
                    <a href="${pageContext.request.contextPath}/myItems" style="color: #333; text-decoration: none; font-weight: bold;">📦 我的物品</a>
                </li>
                <li style="margin-bottom: 10px;">
                    <a href="${pageContext.request.contextPath}/publish" style="color: #333; text-decoration: none;">➕ 发布物品</a>
                </li>
                <li style="margin-bottom: 10px;">
                    <a href="${pageContext.request.contextPath}/" style="color: #333; text-decoration: none;">🏠 返回首页</a>
                </li>
            </ul>

            <div style="margin-top: 30px;">
                <a href="${pageContext.request.contextPath}/publish" class="btn" style="width: 100%; text-align: center;">发布新物品</a>
            </div>
        </aside>

        <main class="content">
            <h2>我的物品</h2>

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

            <div class="stats">
                <div class="stat-item">
                    <div class="stat-number">
                        <c:set var="totalCount" value="0" />
                        <c:forEach var="item" items="${items}">
                            <c:set var="totalCount" value="${totalCount + 1}" />
                        </c:forEach>
                        ${totalCount}
                    </div>
                    <div class="stat-label">总物品数</div>
                </div>
                <div class="stat-item">
                    <div class="stat-number">
                        <c:set var="activeCount" value="0" />
                        <c:forEach var="item" items="${items}">
                            <c:if test="${item.status == 1}">
                                <c:set var="activeCount" value="${activeCount + 1}" />
                            </c:if>
                        </c:forEach>
                        ${activeCount}
                    </div>
                    <div class="stat-label">在售物品</div>
                </div>
                <div class="stat-item">
                    <div class="stat-number">
                        <c:set var="soldCount" value="0" />
                        <c:forEach var="item" items="${items}">
                            <c:if test="${item.status == 0}">
                                <c:set var="soldCount" value="${soldCount + 1}" />
                            </c:if>
                        </c:forEach>
                        ${soldCount}
                    </div>
                    <div class="stat-label">已下架</div>
                </div>
            </div>

            <div class="item-list">
                <c:choose>
                    <c:when test="${empty items}">
                        <div class="empty-state">
                            <span class="icon">📦</span>
                            <h3>你还没有发布过物品</h3>
                            <p>发布你的第一件二手物品，开始交易吧！</p>
                            <a href="${pageContext.request.contextPath}/publish" class="btn" style="margin-top: 20px;">发布物品</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="item" items="${items}">
                            <div class="item">
                                <div class="item-title">
                                    <span class="item-status ${item.status == 1 ? 'status-onsale' : 'status-sold'}">
                                            ${item.status == 1 ? '在售' : '已下架'}
                                    </span>
                                        ${item.title}
                                </div>
                                <div class="item-price">￥${item.price}</div>
                                <div class="item-description">
                                        ${item.description}
                                </div>
                                <div class="item-meta">
                                    发布时间：${item.createdAt}
                                </div>

                                <div class="item-actions">
                                    <a href="${pageContext.request.contextPath}/editItem?id=${item.id}" class="btn btn-blue">编辑</a>
                                    <c:choose>
                                        <c:when test="${item.status == 1}">
                                            <a href="${pageContext.request.contextPath}/deleteItem?id=${item.id}"
                                               class="btn btn-red"
                                               onclick="return confirm('确定要下架这个物品吗？')">下架</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${pageContext.request.contextPath}/editItem?id=${item.id}&status=1"
                                               class="btn"
                                               onclick="return confirm('确定要重新上架这个物品吗？')">重新上架</a>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
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