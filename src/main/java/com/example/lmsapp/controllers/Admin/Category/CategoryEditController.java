package com.example.lmsapp.controllers.Admin.Category;

import com.example.lmsapp.dao.CategoryDAO;
import com.example.lmsapp.models.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/category-edit")
public class CategoryEditController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("category", CategoryDAO.findById(id));
        req.getRequestDispatcher("/WEB-INF/Admin/Category/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name").trim();
        String description = req.getParameter("description");

        Category existing = CategoryDAO.findByName(name);

        if (existing != null && existing.getId() != id) {
            req.setAttribute("error", "Category name already exists.");
            req.getRequestDispatcher("/WEB-INF/Admin/Category/edit.jsp").forward(req, resp);
            return;
        }

        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setDescription(description);

        CategoryDAO.update(category);
        req.setAttribute("category", category);
        req.setAttribute("success", "Category updated successfully.");
        req.getRequestDispatcher("/WEB-INF/Admin/Category/edit.jsp").forward(req, resp);
    }
}
