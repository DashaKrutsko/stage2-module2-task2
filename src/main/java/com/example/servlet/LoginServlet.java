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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String userSession = (String) session.getAttribute("user");
        if (userSession != null) {
            response.sendRedirect("/user/hello.jsp");
        } else {
            response.sendRedirect("/login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        String login = request.getParameter("login");
        if (users.getUsers().contains(login) && !request.getParameter("password").isEmpty()) {
            request.getSession().setAttribute("user", login);
            try {
                response.sendRedirect("/user/hello.jsp");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } catch (IOException | ServletException ex) {
                ex.printStackTrace();
            }
        }
    }
}
