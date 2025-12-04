<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>用户注册 - 二手物品发布平台</title>
    <meta charset="UTF-8">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background-color: #f5f5f5;
            display: flex; justify-content: center; align-items: center; min-height: 100vh; }
        .container { width: 400px; background: white; padding: 40px; border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1); }
        h1 { text-align: center; color: #4CAF50; margin-bottom: 30px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; color: #333; font-weight: bold; }
        input[type="text"], input[type="password"], input[type="email"] { width: 100%; padding: 12px;
            border: 1px solid #ddd; border-radius: 4px; font-size: 16px; }
        .btn { width: 100%; padding: 12px; background: #4CAF50; color: white;
            border: none; border-radius: 4px; font-size: 16px; cursor: pointer; margin-top: 10px; }
        .btn:hover { background: #45a049; }
        .error { color: #f44336; margin-top: 10px; text-align: center; }
        .success { color: #4CAF50; margin-top: 10px; text-align: center; }
        .links { text-align: center; margin-top: 20px; }
        .links a { color: #2196F3; text-decoration: none; }
    </style>
</head>
<body>
<div class="container">
    <h1>用户注册</h1>

    <c:if test="${not empty param.msg}">
        <div class="success">${param.msg}</div>
    </c:if>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">
        <div class="form-group">
            <label for="username">用户名：</label>
            <input type="text" id="username" name="username" required>
        </div>

        <div class="form-group">
            <label for="password">密码：</label>
            <input type="password" id="password" name="password" required>
        </div>

        <div class="form-group">
            <label for="email">邮箱（可选）：</label>
            <input type="email" id="email" name="email">
        </div>

        <button type="submit" class="btn">注册</button>
    </form>

    <div class="links">
        <p>已有账号？<a href="${pageContext.request.contextPath}/login.jsp">立即登录</a></p>
        <p><a href="${pageContext.request.contextPath}/index.jsp">返回首页</a></p>
    </div>
</div>
</body>
</html>