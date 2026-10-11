package com.example.lmsapp.controllers.Media;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/uploads/*")
public class FileDownloadController extends HttpServlet {
    private static final String UPLOAD_DIR = "C:\\uploads";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String requestedFile = req.getPathInfo(); // e.g. /t.png
        if (requestedFile == null) {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "File name required");
            return;
        }
        File file = new File(UPLOAD_DIR, requestedFile);
        if (!file.exists() || file.isDirectory()) {
            res.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        res.setContentType(getServletContext().getMimeType(file.getName()));
        res.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() + "\"");
        res.setContentLengthLong(file.length());

        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = res.getOutputStream()) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}