package com.example.lmsapp.dao;

import com.example.lmsapp.config.ConnectDB;
import com.example.lmsapp.models.Category;
import com.example.lmsapp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public static void save(Category category) {
        try (Connection con = ConnectDB.connect()) {
            PreparedStatement ps;
            String sql = "INSERT INTO categories(name,description) VALUES(?,?)";
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, category.getName());
                ps.setString(2, category.getDescription());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Category findById(int id) {
        String sql = "SELECT * FROM categories WHERE id=?";
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

    public boolean update(Category category) {

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

    public boolean delete(int id) {

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

    private static Category mapRow(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setName(rs.getString("name"));
        category.setDescription(rs.getString("description"));
        category.setCreatedAt(rs.getString("created_at"));
        return category;
    }
}
