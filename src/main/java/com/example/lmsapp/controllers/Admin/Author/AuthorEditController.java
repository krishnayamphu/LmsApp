package com.example.lmsapp.controllers.Admin.Author;

import com.example.lmsapp.dao.AuthorDAO;
import com.example.lmsapp.dao.CategoryDAO;
import com.example.lmsapp.models.Author;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/author-edit")
public class AuthorEditController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("author",AuthorDAO.findById(id));
        req.getRequestDispatcher("/WEB-INF/Admin/Authors/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name").trim();
        String biography = req.getParameter("biography");

        Author existing = AuthorDAO.findByName(name);

        if (existing != null && existing.getId() != id) {
            req.setAttribute("error", "Author name already exists.");
            req.getRequestDispatcher("/WEB-INF/Admin/authors/edit.jsp").forward(req, resp);
            return;
        }

        Author author = new Author();
        author.setId(id);
        author.setName(name);
        author.setBiography(biography);

        AuthorDAO.update(author);
        req.setAttribute("author", author);
        req.setAttribute("success", "Author updated successfully.");
        req.getRequestDispatcher("/WEB-INF/Admin/Authors/edit.jsp").forward(req, resp);
    }
}
