package com.example.lmsapp.controllers;

import com.example.lmsapp.dao.MemberDAO;
import com.example.lmsapp.models.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signup")
public class MemberSignupController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/signup.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name=req.getParameter("name");
        String email=req.getParameter("email");
        String password=req.getParameter("password");
        String mobile=req.getParameter("mobile");
        String address=req.getParameter("address");

        Member member=new Member();
        member.setName(name);
        member.setEmail(email);
        member.setPassword(password);
        member.setMobile(mobile);
        member.setAddress(address);
        member.setStatus(true);
        MemberDAO.save(member);
        resp.sendRedirect("signin");
    }
}
