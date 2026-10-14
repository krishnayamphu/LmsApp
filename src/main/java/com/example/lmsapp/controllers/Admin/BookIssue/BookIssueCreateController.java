package com.example.lmsapp.controllers.Admin.BookIssue;

import com.example.lmsapp.dao.BookDAO;
import com.example.lmsapp.dao.MemberDAO;
import com.example.lmsapp.dao.BookIssueDAO;
import com.example.lmsapp.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

@WebServlet("/admin/book-issues-create")
public class BookIssueCreateController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("books", BookDAO.findAll());
        req.setAttribute("members", MemberDAO.findAll());
        req.getRequestDispatcher("/WEB-INF/Admin/BookIssues/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User authUser= (User) req.getSession(false).getAttribute("authUser");
        try {
            int bookId = Integer.parseInt(req.getParameter("book_id"));
            int memberId = Integer.parseInt(req.getParameter("member_id"));
            Date issueDate = Date.valueOf(LocalDate.parse(req.getParameter("issue_date")));
            Date dueDate = Date.valueOf(LocalDate.parse(req.getParameter("due_date")));
            String notes=req.getParameter("notes");

            BookIssueDAO.issueBook(bookId, memberId, issueDate, dueDate,notes,authUser);
            resp.sendRedirect(req.getContextPath() + "/admin/book-issues?success=created");
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book, member, or date.");
        } catch (SQLException e) {
            throw new ServletException("Unable to issue book.", e);
        }
    }
}
