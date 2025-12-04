package com.secondhand.servlet;

import com.secondhand.service.ItemService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/publish")
public class PublishItemServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?msg=请先登录");
            return;
        }

        // 显示发布页面
        request.getRequestDispatcher("/publish.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 检查用户是否登录
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?msg=请先登录");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        // 获取表单参数
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String imageUrl = request.getParameter("imageUrl");

        // 参数验证
        if (title == null || title.trim().isEmpty() ||
                priceStr == null || priceStr.trim().isEmpty()) {
            request.setAttribute("error", "标题和价格不能为空");
            request.getRequestDispatcher("/publish.jsp").forward(request, response);
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);

            // 发布物品
            boolean success = itemService.publishItem(
                    title.trim(),
                    description != null ? description.trim() : "",
                    price,
                    imageUrl != null ? imageUrl.trim() : "",
                    userId
            );

            if (success) {
                response.sendRedirect(request.getContextPath() + "/myItems?msg=发布成功");
            } else {
                request.setAttribute("error", "发布失败，请重试");
                request.getRequestDispatcher("/publish.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "价格格式不正确");
            request.getRequestDispatcher("/publish.jsp").forward(request, response);
        }
    }
}