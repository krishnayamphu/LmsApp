package com.example.lmsapp.dao;

import com.example.lmsapp.config.ConnectDB;
import com.example.lmsapp.models.Book;
import com.example.lmsapp.models.Category;
import com.example.lmsapp.models.Member;
import com.example.lmsapp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    public static void save(Member member) {
        try (Connection con = ConnectDB.connect()) {
            PreparedStatement ps;
            String sql = "INSERT INTO members(name,email,password,mobile,address,status) VALUES(?,?,?,?,?,?)";
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, member.getName());
                ps.setString(2, member.getEmail());
                ps.setString(3, member.getPassword());
                ps.setString(4, member.getMobile());
                ps.setString(5, member.getAddress());
                ps.setBoolean(6, member.isStatus());
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static Member findById(int id) {
        String sql = "SELECT * FROM members WHERE id=?";
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

    public static Member findByEmail(String email) {
        String sql = "SELECT * FROM members WHERE email=?";
        try (Connection con = ConnectDB.connect(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
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

    public static boolean update(Member member) {
        String sql = "UPDATE members SET name = ?,email = ?,mobile = ?,address = ?,status = ?,updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = ConnectDB.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getName());
            ps.setString(2, member.getEmail());
            ps.setString(3, member.getMobile());
            ps.setString(4, member.getAddress());
            ps.setBoolean(5, member.isStatus());
            ps.setInt(6, member.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Connection conn = ConnectDB.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                members.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }

    public static Member authenticate(String email,String password){
        Member member=findByEmail(email);
        if(member==null){
            return null;
        }
        if(member.getPassword().equals(password)){
            return member;
        }
        return null;
    }

    private static Member mapRow(ResultSet rs) throws SQLException {
        Member member = new Member();
        member.setId(rs.getInt("id"));
        member.setName(rs.getString("name"));
        member.setEmail(rs.getString("email"));
        member.setPassword(rs.getString("password"));
        member.setMobile(rs.getString("mobile"));
        member.setAddress(rs.getString("address"));
        member.setStatus(rs.getBoolean("status"));
        member.setCreatedAt(rs.getString("created_at"));
        member.setUpdatedAt(rs.getString("updated_at"));
        return member;
    }
}
