package com.secondhand.servlet;

import com.secondhand.entity.Item;
import com.secondhand.service.ItemService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");

        if (keyword == null || keyword.trim().isEmpty()) {
            // 如果关键词为空，重定向到物品列表
            response.sendRedirect(request.getContextPath() + "/listItems");
            return;
        }

        // 搜索物品
        List<Item> items = itemService.searchItems(keyword.trim());

        // 设置到request中
        request.setAttribute("items", items);
        request.setAttribute("pageTitle", "搜索结果: " + keyword);
        request.setAttribute("keyword", keyword);

        // 转发到JSP页面
        request.getRequestDispatcher("/itemList.jsp").forward(request, response);
    }
}