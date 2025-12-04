package com.secondhand.servlet;

import com.secondhand.entity.Item;
import com.secondhand.service.ItemService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/myItems")
public class MyItemsServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?msg=请先登录");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        // 获取用户的物品
        List<Item> items = itemService.getUserItems(userId);

        // 设置到request中
        request.setAttribute("items", items);
        request.setAttribute("pageTitle", "我的物品");

        // 转发到JSP页面
        request.getRequestDispatcher("/myItems.jsp").forward(request, response);
    }
}