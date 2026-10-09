package com.example.lmsapp.controllers.Admin.Category;

import com.example.lmsapp.dao.CategoryDAO;
import com.example.lmsapp.models.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/category")
public class CategoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories= CategoryDAO.findAll();
        req.setAttribute("categories",categories);
        req.getRequestDispatcher("/WEB-INF/Admin/Category/index.jsp").forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id=Integer.parseInt(req.getParameter("id"));
        if(CategoryDAO.delete(id)){
            resp.sendRedirect("category");
        }
    }
}
