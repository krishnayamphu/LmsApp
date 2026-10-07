package com.example.lmsapp.controllers;

import com.example.lmsapp.dao.UserDAO;
import com.example.lmsapp.models.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User user = UserDAO.authenticate(email, password);
        if(user==null){
            HttpSession session=req.getSession();
            session.setAttribute("error","Invalid email or password");
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        }else{
            HttpSession session=req.getSession();
            session.setAttribute("authUser",user);
            session.setMaxInactiveInterval(60*15);
            resp.sendRedirect("dashboard");
        }
    }
}
