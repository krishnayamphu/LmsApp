package com.example.lmsapp.controllers.Admin.Author;

import com.example.lmsapp.dao.AuthorDAO;
import com.example.lmsapp.models.Author;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/authors")
public class AuthorController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Author> authors= AuthorDAO.findAll();
        req.setAttribute("authors",authors);
        req.getRequestDispatcher("/WEB-INF/Admin/Authors/index.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id=Integer.parseInt(req.getParameter("id"));
        if(AuthorDAO.delete(id)){
            resp.sendRedirect("authors");
        }
    }
}
