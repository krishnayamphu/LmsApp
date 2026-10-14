package com.example.lmsapp.dao;

import com.example.lmsapp.config.ConnectDB;
import com.example.lmsapp.models.Book;
import com.example.lmsapp.models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public static void save(Book book, List<Integer> authorIds) {
        try (Connection con = ConnectDB.connect()) {
            PreparedStatement ps;
            String sql = "INSERT INTO books(title,publisher,publication_year,edition,quantity,available_quantity,image,category_id) VALUES(?,?,?,?,?,?,?,?)";
            String authorSql = "INSERT INTO book_authors(book_id,author_id) VALUES(?,?)";
            int bookId;
            if (con != null) {
                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, book.getTitle());
                ps.setString(2, book.getPublisher());
                ps.setInt(3, book.getPublicationYear());
                ps.setInt(4, book.getEdition());
                ps.setInt(5, book.getQuantity());
                ps.setInt(6, book.getAvailableQuantity());
                ps.setString(7, book.getImage());
                ps.setInt(8, book.getCategoryId());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) {
                        throw new SQLException("Book ID was not generated");
                    }
                    bookId = keys.getInt(1);
                }
                if (authorIds != null && !authorIds.isEmpty()) {
                    try (PreparedStatement psmt = con.prepareStatement(authorSql)) {
                        for (Integer authorId : authorIds) {
                            psmt.setInt(1, bookId);
                            psmt.setInt(2, authorId);
                            psmt.addBatch();
                        }
                        psmt.executeBatch();
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Book findById(int id) {
        String sql = "SELECT b.*, c.id AS c_id, c.name AS name, c.description AS description FROM books b LEFT JOIN categories c ON c.id = b.category_id WHERE b.id=? ORDER BY b.id DESC";
        try (Connection con = ConnectDB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Book findByTitle(String title) {
        String sql = "SELECT * FROM categories WHERE title=?";
        try (Connection con = ConnectDB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, title);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.*, c.id AS c_id, c.name AS name, c.description AS description FROM books b LEFT JOIN categories c ON c.id = b.category_id ORDER BY b.id DESC";
        try (Connection conn = ConnectDB.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                books.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static boolean update(Book book) {
        return false;
    }


    public static void update(Book book, List<Integer> authorIds) throws SQLException {
        String updateSql = "UPDATE books SET title=?,publisher=?,publication_year=?,edition=?,quantity=?,available_quantity=?,image=?,category_id=? WHERE id=?";
        String deleteAuthorsSql = "DELETE FROM book_authors WHERE book_id = ?";
        String insertAuthorsSql = "INSERT INTO book_authors (book_id, author_id) VALUES (?, ?)";

        try (Connection con = ConnectDB.connect()) {
            con.setAutoCommit(false);
            try {
                // Lock the book row and read its current stock.
                int oldQuantity;
                int oldAvailable;
                try (PreparedStatement ps = con.prepareStatement("SELECT quantity, available_quantity FROM books WHERE id = ? FOR UPDATE")) {
                    ps.setInt(1, book.getId());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Book not found.");
                        }
                        oldQuantity = rs.getInt("quantity");
                        oldAvailable = rs.getInt("available_quantity");
                    }
                }

                // Preserve outstanding loans when changing total stock.
                int newAvailable = oldAvailable + (book.getQuantity() - oldQuantity);
                if (book.getQuantity() < 0 || newAvailable < 0) {
                    throw new SQLException("Total quantity cannot be less than books currently issued.");
                }

                try (PreparedStatement ps = con.prepareStatement(updateSql)) {
                    ps.setString(1, book.getTitle());
                    ps.setString(2, book.getPublisher());
                    ps.setInt(3, book.getPublicationYear());
                    ps.setInt(4, book.getEdition());
                    ps.setInt(5, book.getQuantity());
                    ps.setInt(6, newAvailable);
                    ps.setString(7, book.getImage());
                    ps.setInt(8, book.getCategoryId());
                    ps.setInt(9, book.getId());

                    if (ps.executeUpdate() == 0) {
                        throw new SQLException("Book update failed.");
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(deleteAuthorsSql)) {
                    ps.setInt(1, book.getId());
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = con.prepareStatement(insertAuthorsSql)) {
                    for (Integer authorId : authorIds) {
                        ps.setInt(1, book.getId());
                        ps.setInt(2, authorId);
                        ps.addBatch();
                    }
                    if (!authorIds.isEmpty()) {
                        ps.executeBatch();
                    }
                }

                con.commit();

            } catch (Exception e) {
                con.rollback();

                if (e instanceof SQLException) {
                    throw (SQLException) e;
                }
                throw new SQLException("Unable to update book.", e);

            } finally {
                con.setAutoCommit(true);
            }
        }
    }


    public static boolean delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";
        try (Connection conn = ConnectDB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Book mapRow(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setTitle(rs.getString("title"));
        book.setPublisher(rs.getString("publisher"));
        book.setPublicationYear(rs.getInt("publication_year"));
        book.setEdition(rs.getInt("edition"));
        book.setQuantity(rs.getInt("quantity"));
        book.setAvailableQuantity(rs.getInt("available_quantity"));
        book.setImage(rs.getString("image"));
        book.setCategoryId(rs.getInt("category_id"));
        book.setCreatedAt(rs.getString("created_at"));
        book.setUpdatedAt(rs.getString("updated_at"));

        if (rs.getObject("c_id") != null) {
            Category category = new Category();
            category.setId(rs.getInt("c_id"));
            category.setName(rs.getString("name"));
            category.setDescription(rs.getString("description"));
            book.setCategory(category);
        }
        if (book != null) {
            book.setAuthors(AuthorDAO.findByBookId(book.getId()));
        }
        return book;
    }
}
