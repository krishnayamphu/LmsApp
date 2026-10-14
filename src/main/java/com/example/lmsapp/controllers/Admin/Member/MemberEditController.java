package com.example.lmsapp.controllers.Admin.Member;

import com.example.lmsapp.dao.MemberDAO;
import com.example.lmsapp.models.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/member-edit")
public class MemberEditController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("member", MemberDAO.findById(Integer.parseInt(req.getParameter("id"))));
        req.getRequestDispatcher("/WEB-INF/Admin/Member/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String mobile = req.getParameter("mobile");
            String address = req.getParameter("address");
            String status = req.getParameter("status");

            if (name == null || name.trim().isEmpty()
                    || email == null || email.trim().isEmpty()
                    || status == null
                    || !status.equals("true")
                    && !status.equals("false")) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid member information.");
                return;
            }
            Member member = MemberDAO.findById(id);
            if (member == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Member not found.");
                return;
            }
            member.setName(name.trim());
            member.setEmail(email.trim());
            member.setMobile(mobile);
            member.setAddress(address);
            member.setStatus(Boolean.parseBoolean(status));
            MemberDAO.update(member);
            resp.sendRedirect(req.getContextPath() + "/admin/members?success=updated");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid member ID.");
        }
    }
}
