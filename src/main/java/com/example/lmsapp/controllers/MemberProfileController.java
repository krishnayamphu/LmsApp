package com.example.lmsapp.controllers;

import com.example.lmsapp.dao.MemberDAO;
import com.example.lmsapp.models.Member;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/me/profile")
public class MemberProfileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Member member = (Member) req.getSession().getAttribute("authMember");
        req.setAttribute("member", member);
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Member member = (Member) req.getSession().getAttribute("authMember");
        member.setName(req.getParameter("name"));
        member.setMobile(req.getParameter("mobile"));
        member.setAddress(req.getParameter("address"));
        MemberDAO.update(member);
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
    }
}
