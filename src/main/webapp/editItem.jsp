<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>编辑物品 - 二手物品发布平台</title>
    <meta charset="UTF-8">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { font-family: Arial, sans-serif; background-color: #f5f5f5; }
        .container { max-width: 800px; margin: 20px auto; padding: 20px; background: white; border-radius: 10px; }
        h1 { color: #4CAF50; margin-bottom: 30px; border-bottom: 2px solid #4CAF50; padding-bottom: 10px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: bold; color: #333; }
        input[type="text"], input[type="number"], textarea, select {
            width: 100%; padding: 12px; border: 1px solid #ddd; border-radius: 4px;
            font-size: 16px; font-family: Arial, sans-serif;
        }
        textarea { min-height: 120px; resize: vertical; }
        .btn { padding: 12px 24px; background: #4CAF50; color: white; border: none;
            border-radius: 4px; font-size: 16px; cursor: pointer; }
        .btn:hover { background: #45a049; }
        .btn-secondary { background: #6c757d; }
        .btn-secondary:hover { background: #5a6268; }
        .btn-danger { background: #f44336; }
        .btn-danger:hover { background: #d32f2f; }
        .error { color: #f44336; margin-top: 10px; }
        .form-actions { display: flex; gap: 10px; margin-top: 30px; }
        .price-input { position: relative; }
        .price-input input { padding-left: 30px; }
        .price-symbol { position: absolute; left: 10px; top: 50%; transform: translateY(-50%);
            color: #666; font-weight: bold; }
        .form-hint { color: #666; font-size: 14px; margin-top: 5px; }
        .required::after { content: " *"; color: #f44336; }
        .item-status { display: inline-block; padding: 4px 8px; border-radius: 3px;
            font-size: 14px; margin-left: 10px; }
        .status-onsale { background: #d4edda; color: #155724; }
        .status-sold { background: #f8d7da; color: #721c24; }
    </style>
</head>
<body>
<div class="container">
    <h1>编辑物品</h1>

    <c:if test="${not empty error}">
        <div class="error" style="background: #f8d7da; color: #721c24; padding: 10px;
             border-radius: 4px; margin-bottom: 20px;">
                ${error}
        </div>
    </c:if>

    <c:if test="${not empty item}">
        <form action="${pageContext.request.contextPath}/editItem" method="post">
            <input type="hidden" name="id" value="${item.id}">

            <div class="form-group">
                <label>当前状态</label>
                <div>
                    <c:choose>
                        <c:when test="${item.status == 1}">
                            <span class="item-status status-onsale">在售</span>
                        </c:when>
                        <c:otherwise>
                            <span class="item-status status-sold">已下架</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <div class="form-group">
                <label for="title" class="required">物品标题</label>
                <input type="text" id="title" name="title" value="${item.title}" required>
            </div>

            <div class="form-group">
                <label for="description">物品描述</label>
                <textarea id="description" name="description">${item.description}</textarea>
            </div>

            <div class="form-group">
                <label for="price" class="required">价格</label>
                <div class="price-input">
                    <span class="price-symbol">￥</span>
                    <input type="number" id="price" name="price" step="0.01" min="0"
                           value="${item.price}" required>
                </div>
            </div>

            <div class="form-group">
                <label for="imageUrl">图片链接</label>
                <input type="text" id="imageUrl" name="imageUrl" value="${item.imageUrl}">
                <div class="form-hint">支持 jpg, png 等格式的图片链接</div>
            </div>

            <div class="form-group">
                <label for="status">物品状态</label>
                <select id="status" name="status">
                    <option value="1" ${item.status == 1 ? 'selected' : ''}>在售</option>
                    <option value="0" ${item.status == 0 ? 'selected' : ''}>已下架</option>
                </select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn">保存修改</button>
                <a href="${pageContext.request.contextPath}/myItems" class="btn btn-secondary">取消</a>
                <a href="${pageContext.request.contextPath}/deleteItem?id=${item.id}"
                   class="btn btn-danger"
                   onclick="return confirm('确定要删除这个物品吗？删除后无法恢复！')">删除物品</a>
            </div>
        </form>
    </c:if>
</div>

<script>
    document.querySelector('form').addEventListener('submit', function(e) {
        const title = document.getElementById('title').value.trim();
        const price = document.getElementById('price').value.trim();

        if (!title) {
            e.preventDefault();
            alert('请输入物品标题');
            return;
        }

        if (!price || isNaN(price) || parseFloat(price) <= 0) {
            e.preventDefault();
            alert('请输入有效的价格');
            return;
        }
    });
</script>
</body>
</html>