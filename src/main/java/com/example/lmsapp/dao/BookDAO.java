package com.example.lmsapp.dao;

import com.example.lmsapp.config.ConnectDB;
import com.example.lmsapp.models.Book;
import com.example.lmsapp.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public static void save(Book book) {
        try (Connection con = ConnectDB.connect()) {
            PreparedStatement ps;
            String sql = "INSERT INTO books(title,publisher,publication_year,edition,quantity,available_quantity,image,category_id) VALUES(?,?,?,?,?,?,?,?)";
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, book.getTitle());
                ps.setString(2, book.getPublisher());
                ps.setInt(3,book.getPublicationYear());
                ps.setInt(4,book.getEdition());
                ps.setInt(5,book.getQuantity());
                ps.setInt(6,book.getAvailableQuantity());
                ps.setString(7,book.getImage());
                ps.setInt(8,book.getCategoryId());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Book findById(int id) {
        String sql = "SELECT * FROM books WHERE id=?";
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

    public static Category findByName(String name) {
        String sql = "SELECT * FROM categories WHERE name=?";
        try (Connection con = ConnectDB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
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

    public static List<Category> findAll() {

        List<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM categories ORDER BY created_at ASC";

        try (Connection conn = ConnectDB.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    public static boolean update(Category category) {

        String sql = "UPDATE categories SET name = ?, description = ? WHERE id = ?";
        try (Connection conn = ConnectDB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setLong(3, category.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(int id) {

        String sql = "DELETE FROM categories WHERE id = ?";

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
        return book;
    }
}
