package com.example.lmsapp.controllers.Admin.BookIssue;


import com.example.lmsapp.dao.BookIssueDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/admin/book-issues")
public class BookIssueController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("issues", BookIssueDAO.findAll());
            req.setAttribute("today", new Date(System.currentTimeMillis()));
            req.getRequestDispatcher("/WEB-INF/Admin/BookIssues/index.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Unable to load book issues.", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            BookIssueDAO.returnBook(id);
            resp.sendRedirect(req.getContextPath() + "/admin/book-issues?success=returned");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            throw new ServletException("Unable to return book.", e);
        }
    }
}
