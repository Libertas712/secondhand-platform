package com.secondhand.servlet;

import com.secondhand.entity.Item;
import com.secondhand.service.ItemService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/editItem")
public class EditItemServlet extends HttpServlet {
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

            // 验证物品是否属于用户
            if (!itemService.isItemBelongsToUser(itemId, userId)) {
                response.sendRedirect(request.getContextPath() + "/myItems?error=无权编辑此物品");
                return;
            }

            // 获取物品详情
            Item item = itemService.getItemById(itemId);
            if (item == null) {
                response.sendRedirect(request.getContextPath() + "/myItems?error=物品不存在");
                return;
            }

            request.setAttribute("item", item);
            request.getRequestDispatcher("/editItem.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/myItems?error=参数错误");
        }
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
        String itemIdStr = request.getParameter("id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");
        String imageUrl = request.getParameter("imageUrl");

        // 参数验证
        if (itemIdStr == null || title == null || title.trim().isEmpty() ||
                priceStr == null || priceStr.trim().isEmpty()) {
            request.setAttribute("error", "必要信息不能为空");
            request.getRequestDispatcher("/editItem.jsp").forward(request, response);
            return;
        }

        try {
            int itemId = Integer.parseInt(itemIdStr);
            double price = Double.parseDouble(priceStr);

            // 验证物品是否属于用户
            if (!itemService.isItemBelongsToUser(itemId, userId)) {
                response.sendRedirect(request.getContextPath() + "/myItems?error=无权编辑此物品");
                return;
            }

            // 获取原物品信息
            Item item = itemService.getItemById(itemId);
            if (item == null) {
                response.sendRedirect(request.getContextPath() + "/myItems?error=物品不存在");
                return;
            }

            // 更新物品信息
            item.setTitle(title.trim());
            item.setDescription(description != null ? description.trim() : "");
            item.setPrice(java.math.BigDecimal.valueOf(price));
            item.setImageUrl(imageUrl != null ? imageUrl.trim() : "");

            boolean success = itemService.updateItem(item);

            if (success) {
                response.sendRedirect(request.getContextPath() + "/myItems?msg=修改成功");
            } else {
                request.setAttribute("error", "修改失败，请重试");
                request.setAttribute("item", item);
                request.getRequestDispatcher("/editItem.jsp").forward(request, response);
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "价格格式不正确");
            request.getRequestDispatcher("/editItem.jsp").forward(request, response);
        }
    }
}