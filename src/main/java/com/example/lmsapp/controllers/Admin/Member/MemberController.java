package com.example.lmsapp.controllers.Admin.Member;

import com.example.lmsapp.dao.MemberDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/members")
public class MemberController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("members", MemberDAO.findAll());
        req.getRequestDispatcher("/WEB-INF/Admin/Member/index.jsp").forward(req,resp);
    }
}
