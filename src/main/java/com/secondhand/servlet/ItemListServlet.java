package com.secondhand.servlet;

import com.secondhand.entity.Item;
import com.secondhand.service.ItemService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/listItems")
public class ItemListServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取所有在售物品
        List<Item> items = itemService.getAllActiveItems();

        // 设置到request中
        request.setAttribute("items", items);
        request.setAttribute("pageTitle", "所有二手物品");

        // 转发到JSP页面
        request.getRequestDispatcher("/itemList.jsp").forward(request, response);
    }
}