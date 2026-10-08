package com.example.lmsapp.controllers.Admin.Category;

import com.example.lmsapp.dao.CategoryDAO;
import com.example.lmsapp.models.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/admin/category-create")
public class CategoryCreateController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/Admin/Category/create.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name=req.getParameter("name");
        String description=req.getParameter("description");
        CategoryDAO.save(new Category(name,description));
        resp.sendRedirect("category");
    }
}
