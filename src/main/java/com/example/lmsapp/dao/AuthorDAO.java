package com.example.lmsapp.dao;

import com.example.lmsapp.config.ConnectDB;
import com.example.lmsapp.models.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {
    public static void save(Author author) {
        try (Connection con = ConnectDB.connect()) {
            PreparedStatement ps;
            String sql = "INSERT INTO authors(name,biography) VALUES(?,?)";
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, author.getName());
                ps.setString(2, author.getBiography());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Author findById(int id) {
        String sql = "SELECT * FROM authors WHERE id=?";
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

    public static Author findByName(String name) {
        String sql = "SELECT * FROM authors WHERE name=?";
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

    public static List<Author> findAll() {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM authors ORDER BY created_at ASC";
        try (Connection conn = ConnectDB.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                authors.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public static boolean update(Author author) {
        String sql = "UPDATE authors SET name = ?, biography = ? WHERE id = ?";
        try (Connection conn = ConnectDB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, author.getName());
            ps.setString(2, author.getBiography());
            ps.setLong(3, author.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(int id) {
        String sql = "DELETE FROM authors WHERE id = ?";
        try (Connection conn = ConnectDB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Author mapRow(ResultSet rs) throws SQLException {
        Author author = new Author();
        author.setId(rs.getInt("id"));
        author.setName(rs.getString("name"));
        author.setBiography(rs.getString("biography"));
        author.setCreatedAt(rs.getString("created_at"));
        return author;
    }
}
