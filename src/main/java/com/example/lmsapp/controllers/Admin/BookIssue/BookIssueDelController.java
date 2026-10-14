package com.example.lmsapp.controllers.Admin.BookIssue;

import com.example.lmsapp.dao.BookIssueDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/book-issues-delete")
public class BookIssueDelController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            BookIssueDAO.deleteReturned(id);
            resp.sendRedirect(req.getContextPath() + "/admin/book-issues?success=deleted");
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SQLException e) {
            throw new ServletException("Unable to delete issue.", e);
        }
    }
}
