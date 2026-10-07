package com.example.lmsapp.dao;

import com.example.lmsapp.config.ConnectDB;
import com.example.lmsapp.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    public static void save(User user) {
            try (Connection con = ConnectDB.connect()) {
                PreparedStatement ps;
                String sql = "INSERT INTO users(name,email,password,role) VALUES(?,?,?,?)";
                if (con != null) {
                    ps = con.prepareStatement(sql);
                    ps.setString(1, user.getName());
                    ps.setString(2, user.getEmail());
                    ps.setString(3, user.getPassword());
                    ps.setString(4, user.getRole());
                    ps.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
    }

    public static User findById(int id){
        String sql="SELECT * FROM users WHERE id=?";
        try(Connection con=ConnectDB.connect(); PreparedStatement ps=con.prepareStatement(sql)){
        ps.setInt(1,id);
        try (ResultSet rs=ps.executeQuery()){
            if(rs.next()){
                return mapRow(rs);
            }
        }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static User findByEmail(String email){
        String sql="SELECT * FROM users WHERE email=?";
        try(Connection con=ConnectDB.connect(); PreparedStatement ps=con.prepareStatement(sql)){
            ps.setString(1,email);
            try (ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    return mapRow(rs);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static User authenticate(String email,String password){
        User user=findByEmail(email);
        if(user==null){
            return null;
        }
        if(user.getPassword().equals(password)){
            return user;
        }
        return null;
    }

    private static User mapRow(ResultSet rs) throws SQLException {
        User user=new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getString("role"));
        return user;
    }
}
