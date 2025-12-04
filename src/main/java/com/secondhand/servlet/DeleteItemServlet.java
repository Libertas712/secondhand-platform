package com.secondhand.servlet;

import com.secondhand.service.ItemService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/deleteItem")
public class DeleteItemServlet extends HttpServlet {
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
        String itemIdStr = request.getParameter("id");

        if (itemIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/myItems?error=物品不存在");
            return;
        }

        try {
            int itemId = Integer.parseInt(itemIdStr);

            // 删除物品
            boolean success = itemService.deleteItem(itemId, userId);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/myItems?msg=删除成功");
            } else {
                response.sendRedirect(request.getContextPath() + "/myItems?error=删除失败或无权操作");
            }

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/myItems?error=参数错误");
        }
    }
}