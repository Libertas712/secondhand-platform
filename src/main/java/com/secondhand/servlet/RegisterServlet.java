package com.secondhand.servlet;

import com.secondhand.service.UserService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService = new UserService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 显示注册页面
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        // 参数验证
        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            request.setAttribute("error", "用户名和密码不能为空");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        // 调用Service注册用户
        boolean success = userService.register(username, password, email);

        if (success) {
            // 注册成功，跳转到登录页面
            response.sendRedirect(request.getContextPath() + "/login.jsp?msg=注册成功，请登录");
        } else {
            // 注册失败，用户名可能已存在
            request.setAttribute("error", "用户名已存在，请选择其他用户名");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}