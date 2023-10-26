package com.example.servlet;

import com.example.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private Users users;

    @Override
    public void init() throws ServletException {
        super.init();
        users = Users.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userSession = (String) session.getAttribute("user");
        if (userSession != null) {
            response.sendRedirect("/user/hello.jsp");
        } else {
            response.sendRedirect("/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> userList = users.getUsers();

        String login = request.getParameter("login");
        String password = request.getParameter("password").trim();

        if (login != null && userList.contains(login)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", login);
            session.setAttribute("password", password);
            response.sendRedirect("/user/hello.jsp");
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }
}
