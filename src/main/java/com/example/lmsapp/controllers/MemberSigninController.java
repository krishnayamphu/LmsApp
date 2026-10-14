package com.example.lmsapp.controllers;

import com.example.lmsapp.dao.MemberDAO;
import com.example.lmsapp.dao.UserDAO;
import com.example.lmsapp.models.Member;
import com.example.lmsapp.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signin")
public class MemberSigninController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/signin.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        Member member = MemberDAO.authenticate(email, password);
        if(member==null){
            req.setAttribute("error","Invalid email or password");
            req.getRequestDispatcher("/WEB-INF/signin.jsp").forward(req, resp);
        }else{
            HttpSession session=req.getSession();
            session.setAttribute("authMember",member);
            resp.sendRedirect(req.getContextPath() +"/me/my-books");
        }
    }
}
