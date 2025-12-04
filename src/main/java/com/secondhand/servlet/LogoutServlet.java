package com.secondhand.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 清除Session
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        // 跳转到首页
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}