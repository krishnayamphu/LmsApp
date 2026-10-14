package com.example.lmsapp.controllers;

import com.example.lmsapp.dao.BookIssueDAO;
import com.example.lmsapp.models.BookIssue;
import com.example.lmsapp.models.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/me/my-books")
public class MyBooksController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Member authMember = (Member) req.getSession().getAttribute("authMember");
        try {
            List<BookIssue> myBooks = BookIssueDAO.findByMemberId(authMember.getId());
            req.setAttribute("myBooks", myBooks);
            req.getRequestDispatcher("/WEB-INF/my-books.jsp").forward(req, resp);
        } catch (java.sql.SQLException e) {
            throw new ServletException("Unable to load member books", e);
        }
    }
}
