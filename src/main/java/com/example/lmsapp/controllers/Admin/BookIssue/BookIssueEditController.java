package com.example.lmsapp.controllers.Admin.BookIssue;

import com.example.lmsapp.dao.BookIssueDAO;
import com.example.lmsapp.models.BookIssue;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/admin/book-issues-edit")
public class BookIssueEditController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            BookIssue issue = BookIssueDAO.findById(id);

            if (issue == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            req.setAttribute("issue", issue);
            req.getRequestDispatcher("/WEB-INF/Admin/BookIssues/edit.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            throw new ServletException("Unable to load issue.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Date dueDate = Date.valueOf(req.getParameter("due_date"));
            BookIssueDAO.updateDueDate(id, dueDate);
            resp.sendRedirect(req.getContextPath() + "/admin/book-issues?success=updated");

        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid issue ID or due date.");
        } catch (SQLException e) {
            throw new ServletException("Unable to update due date.", e);
        }
    }
}
