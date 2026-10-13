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
    private String title,publisher,image;
    private int publication_year,edition,quantity,category_id;
    private List<Integer> authorIds=new ArrayList<>();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id=Integer.parseInt(req.getParameter("id"));

        Book book=BookDAO.findById(id);
        req.setAttribute("book",BookDAO.findById(id));
        req.setAttribute("categories", CategoryDAO.findAll());
        req.setAttribute("authors", AuthorDAO.findAll());
        req.getRequestDispatcher("/WEB-INF/Admin/Books/show.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);

            ServletFileUpload upload = new ServletFileUpload(factory);
            try {
                List<FileItem> items = upload.parseRequest(request);
                Iterator<FileItem> iter = items.iterator();
                while (iter.hasNext()) {
                    FileItem item = iter.next();
                    if (item.isFormField()) {
                        processFormField(item);
                    } else {
                        processUploadedFile(item);
                        response.getWriter().println("file uploaded");
                    }
                }
                Book book=new Book();
                book.setTitle(title);
                book.setPublisher(publisher);
                book.setPublicationYear(publication_year);
                book.setEdition(edition);
                book.setQuantity(quantity);
                book.setAvailableQuantity(quantity);
                book.setCategoryId(category_id);
                book.setImage(image);
                BookDAO.save(book,authorIds);
                response.sendRedirect("books");
            } catch (FileUploadException e) {
                throw new RuntimeException(e);
            }

        } else {
            response.getWriter().println("No file uploaded");
        }
    }

    private void processFormField(FileItem item) {
        if(item.getFieldName().equals("title")){
            title=item.getString();
        } else if (item.getFieldName().equals("publisher")) {
            publisher=item.getString();
        } else if (item.getFieldName().equals("publication_year")) {
            publication_year=Integer.parseInt(item.getString());
        }else if (item.getFieldName().equals("edition")) {
            edition=Integer.parseInt(item.getString());
        }else if (item.getFieldName().equals("quantity")) {
            quantity=Integer.parseInt(item.getString());
        }else if (item.getFieldName().equals("category_id")) {
            category_id=Integer.parseInt(item.getString());
        }else if(item.getFieldName().equals("author_ids")){
            authorIds.add(Integer.parseInt(item.getString()));
        }
    }

    private void processUploadedFile(FileItem item) {
        String fileName = item.getName();
        image=fileName;
        File path = new File("C:\\uploads");
        if (!path.exists()) {
            path.mkdirs();
        }
        File uploadedFile = new File(path + "/" + fileName);
        try {
            item.write(uploadedFile);
            System.out.println("File Uploaded to :" + uploadedFile.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
