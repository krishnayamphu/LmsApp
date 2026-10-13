package com.example.lmsapp.controllers.Admin.Book;

import com.example.lmsapp.dao.AuthorDAO;
import com.example.lmsapp.dao.BookDAO;
import com.example.lmsapp.dao.CategoryDAO;
import com.example.lmsapp.models.Book;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet("/admin/book-view")
public class BookViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id=Integer.parseInt(req.getParameter("id"));
        Book book=BookDAO.findById(id);
        req.setAttribute("book",BookDAO.findById(id));
        req.setAttribute("categories", CategoryDAO.findAll());
        req.setAttribute("authors", AuthorDAO.findAll());
        req.getRequestDispatcher("/WEB-INF/Admin/Books/show.jsp").forward(req, resp);
    }
}
